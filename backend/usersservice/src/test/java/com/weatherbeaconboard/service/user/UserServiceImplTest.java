package com.weatherbeaconboard.service.user;

import com.weatherbeaconboard.exceptions.ConfirmationTokenAlreadyConfirmedException;
import com.weatherbeaconboard.exceptions.ConfirmationTokenExpiredException;
import com.weatherbeaconboard.exceptions.EntityAlreadyExistsException;
import com.weatherbeaconboard.exceptions.EntityIsNotExistingException;
import com.weatherbeaconboard.model.ConfirmationTokenEntity;
import com.weatherbeaconboard.model.UserEntity;
import com.weatherbeaconboard.model.enums.RoleType;
import com.weatherbeaconboard.repository.UserRepository;
import com.weatherbeaconboard.service.confirmationtoken.ConfirmationTokenService;
import com.weatherbeaconboard.service.converter.NullSafeConverter;
import com.weatherbeaconboard.service.email.EmailService;
import com.weatherbeaconboard.web.model.EmailRequest;
import com.weatherbeaconboard.web.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.OffsetDateTime;
import java.util.Optional;

import static com.weatherbeaconboard.utils.DateUtils.getDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ConfirmationTokenService confirmationTokenService;

    @Mock
    private EmailService emailService;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private NullSafeConverter converter;

    @InjectMocks
    private UserServiceImpl userService;

    private UserEntity expectedUserEntity;
    private User expectedUser;
    private final static String USERNAME = "a";
    private static final OffsetDateTime NOW = getDateTime();
    private final String validToken = "validToken";
    private final String expiredToken = "expiredToken";
    private final String confirmedToken = "confirmedToken";
    private ConfirmationTokenEntity validConfirmationToken;
    private ConfirmationTokenEntity expiredConfirmationToken;
    private ConfirmationTokenEntity alreadyConfirmedToken;

    @BeforeEach
    void setup() {
        String phoneNumber = "1234567890";
        String email = "alex@gmail.com";
        String cityAddress = "Caracal";

        expectedUserEntity = UserEntity.builder()
                .id(1)
                .username(USERNAME)
                .password("12345678")
                .firstName("Armi")
                .lastName("Ciuci")
                .role(RoleType.USER)
                .email(email)
                .birthday(NOW)
                .address(cityAddress)
                .phoneNumber(phoneNumber)
                .enabled(true)
                .locked(false)
                .version(0)
                .build();

        expectedUser = User.builder()
                .id(1)
                .username(USERNAME)
                .password("12345678")
                .firstName("Armi")
                .lastName("Ciuci")
                .role(RoleType.USER)
                .email(email)
                .birthday(NOW)
                .address(cityAddress)
                .phoneNumber(phoneNumber)
                .build();

        OffsetDateTime now = OffsetDateTime.now();
        OffsetDateTime oneDayAgo = now.minusDays(1);
        OffsetDateTime oneDayAhead = now.plusDays(1);

        validConfirmationToken = new ConfirmationTokenEntity();
        validConfirmationToken.setId(1);
        validConfirmationToken.setToken(validToken);
        validConfirmationToken.setTokenCreatedAt(now);
        validConfirmationToken.setTokenExpiresAt(oneDayAhead);

        expiredConfirmationToken = new ConfirmationTokenEntity();
        expiredConfirmationToken.setId(2);
        expiredConfirmationToken.setToken(expiredToken);
        expiredConfirmationToken.setTokenCreatedAt(oneDayAgo);
        expiredConfirmationToken.setTokenExpiresAt(oneDayAgo); // Already expired

        alreadyConfirmedToken = new ConfirmationTokenEntity();
        alreadyConfirmedToken.setId(3);
        alreadyConfirmedToken.setToken(confirmedToken);
        alreadyConfirmedToken.setTokenCreatedAt(now);
        alreadyConfirmedToken.setTokenExpiresAt(oneDayAhead);
        alreadyConfirmedToken.setTokenConfirmedAt(now); // Already confirmed
        validConfirmationToken.setUser(expectedUserEntity);
        expiredConfirmationToken.setUser(expectedUserEntity);
        alreadyConfirmedToken.setUser(expectedUserEntity);
    }

    @Test
    void whenLoadUserByUsername_thenSuccess() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(expectedUserEntity));

        final UserDetails userDetails = userService.loadUserByUsername(USERNAME);

        assertEquals(USERNAME, userDetails.getUsername());
        String password = "12345678";
        assertEquals(password, userDetails.getPassword());
    }

    @Test
    void whenLoadUserByUsername_thenThrowException() {
        when(userRepository.findByUsername(anyString())).thenThrow(UsernameNotFoundException.class);

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(USERNAME));
    }

    @Test
    void whenGetById_thenSuccess() {
        when(userRepository.existsById(1)).thenReturn(true);
        when(userRepository.getReferenceById(1)).thenReturn(expectedUserEntity);
        when(converter.convert(any(UserEntity.class), eq(User.class))).thenReturn(expectedUser);

        final User result = userService.getById(1);

        assertEquals(expectedUser, result);
    }

    @Test
    void whenGetById_thenThrowException() {
        when(userRepository.existsById(1)).thenReturn(false);

        assertThrows(EntityIsNotExistingException.class, () -> userService.getById(1));
    }

    @Test
    void whenDeleteById_thenSuccess() {
        userService.deleteById(1);

        verify(confirmationTokenService).deleteById(1);
        verify(userRepository).deleteById(1);
    }

    @Test
    void whenUpdate_thenSuccess() {
        when(userRepository.existsById(1)).thenReturn(true);
        when(userRepository.getReferenceById(1)).thenReturn(expectedUserEntity);
        when(userRepository.save(any(UserEntity.class))).thenReturn(expectedUserEntity);
        when(converter.convert(expectedUserEntity, User.class)).thenReturn(expectedUser);

        final User result = userService.update(1, expectedUser);

        assertEquals(expectedUser, result);
    }

    @Test
    void whenUpdate_thenThrowException() {
        when(userRepository.existsById(1)).thenReturn(false);

        assertThrows(EntityIsNotExistingException.class, () -> userService.update(1, expectedUser));
    }

    @Test
    void whenRegister_thenSuccess() {
        when(converter.convert(expectedUser, UserEntity.class)).thenReturn(expectedUserEntity);
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(UserEntity.class))).thenReturn(expectedUserEntity);
        when(confirmationTokenService.saveAndReturnAConfirmationToken(expectedUserEntity)).thenReturn("token");

        final String token = userService.register(expectedUser);

        assertEquals("token", token);
        verify(emailService).sendRegistrationEmail(any(EmailRequest.class));
    }

    @Test
    void whenRegister_thenThrowException() {
        when(converter.convert(expectedUser, UserEntity.class)).thenReturn(expectedUserEntity);
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(expectedUserEntity));

        assertThrows(EntityAlreadyExistsException.class, () -> userService.register(expectedUser));
    }

    @Test
    void confirmToken_WithValidToken() {
        when(confirmationTokenService.getToken(validToken)).thenReturn(validConfirmationToken);

        userService.confirmToken(validToken);

        verify(confirmationTokenService).setConfirmedAt(validToken);
        verify(userRepository).enableUser(anyString());
        verify(userRepository).unlockUser(anyString());
    }

    @Test
    void confirmToken_WithExpiredToken() {
        when(confirmationTokenService.getToken(expiredToken)).thenReturn(expiredConfirmationToken);

        assertThrows(ConfirmationTokenExpiredException.class, () -> userService.confirmToken(expiredToken));

        verify(confirmationTokenService, never()).setConfirmedAt(expiredToken);
    }

    @Test
    void confirmToken_WithAlreadyConfirmedToken() {
        when(confirmationTokenService.getToken(confirmedToken)).thenReturn(alreadyConfirmedToken);

        assertThrows(ConfirmationTokenAlreadyConfirmedException.class, () -> userService.confirmToken(confirmedToken));

        verify(confirmationTokenService, never()).setConfirmedAt(confirmedToken);
    }
}
