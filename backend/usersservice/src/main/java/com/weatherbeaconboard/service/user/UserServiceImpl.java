package com.weatherbeaconboard.service.user;


import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.weatherbeaconboard.exceptions.*;
import com.weatherbeaconboard.model.ConfirmationTokenEntity;
import com.weatherbeaconboard.model.UserEntity;
import com.weatherbeaconboard.model.enums.RoleType;
import com.weatherbeaconboard.repository.UserRepository;
import com.weatherbeaconboard.service.confirmationtoken.ConfirmationTokenService;
import com.weatherbeaconboard.service.converter.NullSafeConverter;
import com.weatherbeaconboard.service.email.EmailService;
import com.weatherbeaconboard.web.model.EmailRequest;
import com.weatherbeaconboard.web.model.User;
import jakarta.persistence.OptimisticLockException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.beans.PropertyDescriptor;
import java.time.OffsetDateTime;
import java.util.Locale;

import static java.lang.String.format;
import static java.util.Collections.singleton;

/**
 * Service class used for managing user business logic and implementing the {@link com.weatherbeaconboard.service.user.UserService} interface.
 */
@Slf4j
@Service
@Validated
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailService emailService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final NullSafeConverter converter;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity foundUser = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User with %s username was not found" + username)
        );

        return new org.springframework.security.core.userdetails.User(
                foundUser.getUsername(),
                foundUser.getPassword(),
                singleton(new SimpleGrantedAuthority(foundUser.getRole().name()))
        );
    }

    /**
     * Retrieve a  {@link User} object based on id.
     *
     * @param id the id of the wanted  {@link User} object
     * @return an object of type {@link User}
     * @throws EntityIsNotExistingException if the searched user does not exist in the database
     */
    public User getById(@NotNull Integer id) {
        assertEntityExists(id);
        final UserEntity foundUserEntity = userRepository.getReferenceById(id);

        return converter.convert(foundUserEntity, User.class);
    }

    /**
     * Delete a {@link UserEntity} by id alongside with the associated {@link ConfirmationTokenEntity}.
     *
     * @param id the id of the {@link UserEntity} to be deleted
     * @throws EntityIsNotExistingException if the searched user does not exist in the database
     */
    public void deleteById(@NotNull Integer id) {
        confirmationTokenService.deleteById(id);
        userRepository.deleteById(id);
    }

    /**
     * Update a {@link UserEntity} fields.
     *
     * @param id   the id of the user that will be updated
     * @param user the web model mapped as {@link User} that contains the data that will be updated
     * @return the updated {@link UserEntity} mapped as a {@link User}
     * @throws EntityIsNotExistingException if the searched user id does not exist in the database
     */
    public User update(@NotNull Integer id, @NotNull @Valid User user) {
        assertEntityExists(id);
        final UserEntity foundUserEntity = userRepository.getReferenceById(id);

        final BeanWrapper srcBeanWrapper = new BeanWrapperImpl(user);
        final BeanWrapper tgtBeanWrapper = new BeanWrapperImpl(foundUserEntity);

        for (PropertyDescriptor srcPropertyDescriptor : srcBeanWrapper.getPropertyDescriptors()) {
            String propertyName = srcPropertyDescriptor.getName();
            Object providedValue = srcBeanWrapper.getPropertyValue(propertyName);

            if (providedValue != null && tgtBeanWrapper.isWritableProperty(propertyName)) {
                tgtBeanWrapper.setPropertyValue(propertyName, providedValue);
            }
        }

        try {
            final UserEntity savedUser = userRepository.save(foundUserEntity);
            return converter.convert(savedUser, User.class);
        } catch (ObjectOptimisticLockingFailureException | OptimisticLockException e) {
            throw new OptimisticLockingFailureException("Failed to update user due to concurrent update.", e);
        }
    }

    /**
     * Check if the {@link UserEntity} email already exists.
     *
     * @param email the email of {@link UserEntity}
     * @return true if the email already exist, otherwise false
     */
    @Override
    public boolean isUserEmailAlreadyRegistered(@NotBlank String email) {
        final boolean isUserFound = userRepository.findByEmail(email).isPresent();

        if (!isUserFound) {
            log.debug("User {} was not found by email", email);
        }

        log.debug("User {} was found by email", email);
        return isUserFound;
    }

    /**
     * After the email is confirmed, the token will be set as confirmed and the {@link UserEntity} account will be enabled.
     *
     * @param token the unique generated token for a new user
     * @throws ConfirmationTokenExpiredException          if the confirmation token expired
     * @throws ConfirmationTokenAlreadyConfirmedException if the confirmation token already exists in the database
     */
    @Override
    public void confirmToken(@NotBlank String token) {
        final ConfirmationTokenEntity confirmationToken = confirmationTokenService.getToken(token);

        if (confirmationToken.getTokenExpiresAt().isBefore(OffsetDateTime.now())) {
            log.debug("The token has expired and cannot be used for confirmation.");
            throw new ConfirmationTokenExpiredException("Token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        log.debug("The token was confirmed by user.");

        // Enable and unlock the user account
        userRepository.enableUser(confirmationToken.getUser().getEmail());
        userRepository.unlockUser(confirmationToken.getUser().getEmail());
        log.debug("User enabled their account.");
    }

    /**
     * Based on entered data, a new user will be saved in the DB and a token
     * is generated for validating email within the next 30 minutes from registering.
     *
     * @param newUser the user who register in the app
     * @return token the generated confirmation token for user email validation
     * @throws EntityAlreadyExistsException if a user with the same email already exists
     * @throws InvalidPhoneNumberException  if the introduced phone number is not valid
     */
    @Override
    public String register(@NotNull User newUser) throws EntityAlreadyExistsException {
        final UserEntity userEntity = converter.convert(newUser, UserEntity.class);
        checkIfEmailAlreadyExists(userEntity.getEmail());
        validatePhoneNumber(userEntity.getPhoneNumber());
        final UserEntity savedUser = save(userEntity);

        String token = confirmationTokenService.saveAndReturnAConfirmationToken(savedUser);

        String link = "http://localhost:8080/v1/auth/confirm?token=" + token; //url for validating user acc

        sendRegistrationEmail(savedUser, link);

        return token;
    }

    private void assertEntityExists(@NotNull Integer id) {
        log.debug("Attempting to retrieve user for id {} ", id);

        if (!userRepository.existsById(id)) {
            final String errorMessage = format("An object of type %s is not existing for the id %s",
                    UserEntity.class.getSimpleName(), id);

            throw new EntityIsNotExistingException(errorMessage);
        }
    }

    /**
     * Check if a user email already exists.
     *
     * @param email email of user
     * @throws EntityAlreadyExistsException - if a user with the same username already exists
     */
    private void checkIfEmailAlreadyExists(@NotBlank String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            log.debug("User email {} already exists", email);
            throw new EntityAlreadyExistsException(email);
        }
    }

    /**
     * Creates a user entity and adds it in the database based on the inputs.
     *
     * @param user the user that will be stored in the database
     */
    private UserEntity save(@NotNull @Valid UserEntity user) {
        final String encodedPassword = passwordEncoder.encode(user.getPassword());

        UserEntity newUser = UserEntity.builder()
                .username(user.getUsername())
                .password(encodedPassword)
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(RoleType.USER)
                .email(user.getEmail())
                .address(user.getAddress())
                .phoneNumber(user.getPhoneNumber())
                //account is not set as enabled in the moment of registering
                .enabled(false)
                .locked(true)
                .build();

        return userRepository.save(newUser);
    }

    /**
     * Validates the phone number using {@link Phonenumber}.
     * The country is built based on {@link PhoneNumberUtil#getCountryCodeForRegion(String)}.
     *
     * @param number the phone number that will be validated
     * @throws InvalidPhoneNumberException if the introduced phone number is not valid
     */
    private void validatePhoneNumber(@NotBlank String number) {
        final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        final Locale defaultLocale = Locale.getDefault();
        final String countryCode = phoneNumberUtil.getRegionCodeForCountryCode(
                phoneNumberUtil.getCountryCodeForRegion(defaultLocale.getCountry())
        );

        try {
            final Phonenumber.PhoneNumber phoneNumber = phoneNumberUtil.parse(number, countryCode);
            phoneNumberUtil.isValidNumber(phoneNumber);
        } catch (NumberParseException e) {
            throw new InvalidPhoneNumberException("Invalid phone number");
        }
    }

    /**
     * Send an email for the given {@link UserEntity} to inform about his registration in the application.
     *
     * @param user the {@link UserEntity} that was registered in the application
     * @param link the link where the {@link UserEntity} can validate his registration
     */
    private void sendRegistrationEmail(@NotNull UserEntity user, @NotBlank String link) {
        final String emailSubject = "Welcome to WeatherBeacon Board: Verify Your Email Address to Get Started";

        final EmailRequest emailRequest = EmailRequest.builder()
                .to(user.getEmail())
                .subject(emailSubject)
                .body(buildEmail(user.getFirstName(), link))
                .build();

        emailService.sendRegistrationEmail(emailRequest);
    }

    /**
     * Email template that the {@link UserEntity} will receive when he registers in the application.
     *
     * @param name the {@link UserEntity} that was registered in the application
     * @param link the link where the {@link UserEntity} can validate his registration
     * @return a String containing the email template
     */
    private @NotNull
    String buildEmail(@NotBlank String name, @NotBlank String link) {
        return "<div style=\"width: 500px; margin: 0 auto; text-align: center; font-family: Arial, sans-serif; background-color: lightgray; padding: 40px; border-radius: 10px; box-shadow: 0 0 10px 0 rgba(0, 0, 0, 0.1);\">\n" +
                " <h1 style=\"margin-top: 50px; font-size: 36px; color: #01304A;\">Email Verification</h1>\n" +
                " <p>Dear " + name + ",</p>\n" +
                " <p>Thank you for signing up for our application! To complete your registration, we need to verify your email address.</p>\n" +
                " <br>\n" +
                " <p>Click the button below to validate your email.</p>\n" +
                " <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a target=\"_blank\" href=\"" + link + "\">Activate now</a> </p>\n" +
                " </p>\n" +
                " <p style=\"font-size: 18px; margin-bottom: 20px; color: gray;\">Best wishes,</p>\n" +
                " <p style=\"font-size: 18px; margin-bottom: 20px; color: gray;\">The WeatherBeacon Board Team.</p>\n" +
                "</p>" +
                "</div>";
    }
}