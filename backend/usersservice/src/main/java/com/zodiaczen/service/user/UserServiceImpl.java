package com.zodiaczen.service.user;


import com.zodiaczen.exceptions.EntityAlreadyExistsException;
import com.zodiaczen.exceptions.InvalidEmailException;
import com.zodiaczen.model.ConfirmationTokenEntity;
import com.zodiaczen.model.UserEntity;
import com.zodiaczen.model.enums.RolType;
import com.zodiaczen.repository.UserRepository;
import com.zodiaczen.security.PasswordEncoder;
import com.zodiaczen.service.confirmationtoken.ConfirmationTokenService;
import com.zodiaczen.service.email.EmailService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * Service class used for managing user business logic and implementing the {@link UserService} interface.
 */
@Slf4j
@Service
@Validated
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailService emailService;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           ConfirmationTokenService confirmationTokenService,
                           EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.confirmationTokenService = confirmationTokenService;
        this.emailService = emailService;
    }

    /**
     * After the email is confirmed, the token will be set as confirmed and the {@link UserEntity} account will be enabled.
     *
     * @param token the unique generated token for a new user
     */

    @Override
    public void confirmToken(@NotBlank String token) {
        final ConfirmationTokenEntity confirmationToken = confirmationTokenService.getToken(token);

        if (confirmationToken.getTokenConfirmedAt().isBefore(confirmationToken.getTokenExpiresAt())) {
            confirmationTokenService.setConfirmedAt(token);
            log.debug("The token was confirmed by user.");

            //set users scopes as validated
            userRepository.enableUser(confirmationToken.getUser().getEmail());
            userRepository.unlockUser(confirmationToken.getUser().getEmail());
            log.debug("User enabled his account.");
        }
    }

    /**
     * Based on entered data, a new user will be saved in the DB and a token
     * is generated for validating email within the next 30 minutes from registering.
     *
     * @param newUser the user who register in the app
     * @return a String which contains the unique token generated for the registered user
     * @throws InvalidEmailException       if a user email is invalid
     * @throws EntityAlreadyExistsException if a user with the same email already exists
     */
    @Override
    public String saveUserAndSendConfirmationToken(@NotNull UserEntity newUser) throws InvalidEmailException, EntityAlreadyExistsException {
        String activationTokenForANewUser = signUpUser(newUser);

        String link = "http://localhost:5000/confirm?token=" + activationTokenForANewUser; //url for validating user account

        sendRegistrationEmail(newUser, link);

        return activationTokenForANewUser;
    }

    private String signUpUser(@NotNull UserEntity user) throws EntityAlreadyExistsException {
        emailService.checkIfEmailIsValid(user.getEmail());
        checkIfEmailAlreadyExists(user.getEmail());

        save(user);

        return confirmationTokenService.saveAndReturnAConfirmationToken(user);
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
    private void save(@NotNull @Valid UserEntity user) {
        String encodedPassword = passwordEncoder.bCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(encodedPassword);

        UserEntity newUser = UserEntity.builder()
                .username(user.getUsername())
                .password(encodedPassword)
                .role(RolType.USER)
                .email(user.getEmail())
                .address(user.getAddress())
                .phoneNumber(user.getPhoneNumber())
                .enabled(false)
                .locked(true)
                .build();

        userRepository.save(newUser);
        log.debug("User {} inserted into the database", user);
    }

    /**
     * Send an email for the given {@link UserEntity} to inform about his registration in the application.
     *
     * @param newUser the {@link UserEntity} that was registered in the application
     * @param link    the link where the {@link UserEntity} can validate his registration
     */
    private void sendRegistrationEmail(@NotNull UserEntity newUser, @NotBlank String link) {
        String emailSubject = "Bine ați venit la Vânătorii de licitații: Verificați adresa dvs. de e-mail pentru a începe";
        emailService.sendEmail(newUser.getEmail(), emailSubject, buildEmail(newUser.getUsername(), link));
    }

    /**
     * Email template that the {@link UserEntity} will receive when he will register in the application.
     *
     * @param name the {@link UserEntity} that was registered in the application
     * @param link the link where the {@link UserEntity} can validate his registration
     * @return a String containing the email template
     */
    //TODO: consider this method
    private @NotNull String buildEmail(@NotBlank String name, @NotBlank String link) {

        return "<div style=\"width: 500px; margin: 0 auto; text-align: center; font-family: Arial, sans-serif; background-color: lightgray; padding: 40px; border-radius: 10px; box-shadow: 0 0 10px 0 rgba(0, 0, 0, 0.1);\">\n" +
                " <h1 style=\"margin-top: 50px; font-size: 36px; color: #01304A;\">Validare email</h1>\n" +
                " <p>Draga " + name + ",</p>\n" +
                " <p>Vă mulțumim că v-ați înscris la în aplicația noastră! Pentru a finaliza înregistrarea, trebuie să verificăm adresa dumneavoastră de e-mail.</p>\n" +
                " <br>\n" +
                " <p>Apăsați pe butonul de mai jos pentru a valida e-malailul.</p>\n" +
                " <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a  target=\"_blank\" href=\"" + link + "\">Actiează acum</a> </p>" +
                " </p>\n" +
                " <p style=\"font-size: 18px; margin-bottom: 20px; color: gray;\">Cele mai bune urări,</p>\n" +
                " <p style=\"font-size: 18px; margin-bottom: 20px; color: gray;\">echipa Vânătorii de Licitații.</p>\n" +
                "</p>" +
                "</div>";
    }
}