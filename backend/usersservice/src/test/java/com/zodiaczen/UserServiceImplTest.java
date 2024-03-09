//package com.zodiaczen;
//
//import com.zodiaczen.exceptions.EntityAlreadyExistsException;
//import com.zodiaczen.exceptions.InvalidEmailException;
//import com.zodiaczen.model.UserEntity;
//import com.zodiaczen.model.enums.RoleType;
//import com.zodiaczen.repository.UserRepository;
//import com.zodiaczen.security.PasswordEncoder;
//import com.zodiaczen.service.confirmationtoken.ConfirmationTokenService;
//import com.zodiaczen.service.converter.NullSafeConverter;
//import com.zodiaczen.service.email.EmailService;
//import com.zodiaczen.service.user.UserServiceImpl;
//import com.zodiaczen.web.model.User;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.time.OffsetDateTime;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class UserServiceImplTest {
//
//    @Mock
//    private UserRepository userRepository;
//    //    @Mock
////    private ConfirmationTokenRepository confirmationTokenRepository;
//    @Mock
//    private PasswordEncoder passwordEncoder;
//    @Mock
//    private ConfirmationTokenService confirmationTokenService;
//    @Mock
//    private EmailService emailService;
//    @Mock
//    private NullSafeConverter converter;
//
//    @InjectMocks
//    private UserServiceImpl userService;
//
//
//    //    private final UserRepository userRepository;
//    //    private final PasswordEncoder passwordEncoder;
//    //    private final ConfirmationTokenService confirmationTokenService;
//    //    private final EmailService emailService;
//    //
//    //    private final NullSafeConverter converter;
//    private static UserEntity expectedUserEntity;
//    String adminData = "a";
//    String adminPhoneNumber = "1234567890";
//    String adminEmail = "auctionshunters@gmail.com";
//    String adminCityAddress = "Caracal";
//
//    @BeforeEach
//    void setUp() {
//
//     //   userService = new UserServiceImpl(userRepository, passwordEncoder, confirmationTokenService, emailService, converter);
//        OffsetDateTime now = OffsetDateTime.now();
//
//        expectedUserEntity = UserEntity.builder()
//                .username(adminData)
//                .password(adminData)
//                .role(RoleType.ADMIN)
//                .email(adminEmail)
//                .address(adminCityAddress)
//                .phoneNumber(adminPhoneNumber)
//                .enabled(true)
//                .locked(false)
//                .build();
//
////        ConfirmationTokenEntity adminConfirmationToken = ConfirmationTokenEntity.builder()
////                .token(adminData)
////                .tokenCreatedAt(now)
////                .tokenExpiresAt(now)
////                .tokenConfirmedAt(now)
////                .user(expectedUserEntity)
////                .build();
//    }
//
//    @Test
//    void whenCheckingUserEmailNotRegistered_thenReturnsFalse() {
//        String email = "user@example.com";
//        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
//
//        boolean result = userService.isUserEmailAlreadyRegistered(email);
//
//        assertFalse(result);
//        verify(userRepository).findByEmail(email);
//    }
//
//    @Test
//    void whenCheckingUserEmailNotRegistered_thenReturnsTrue() {
//        String email = "user@example.com";
//        when(userRepository.findByEmail(email)).thenReturn(Optional.of(expectedUserEntity));
//
//        boolean result = userService.isUserEmailAlreadyRegistered(email);
//
//        assertTrue(result);
//        verify(userRepository).findByEmail(email);
//    }
//
//    @Test
//    void whenSavingUserAndSendingToken_thenSucceeds() throws InvalidEmailException, EntityAlreadyExistsException {
//        User user = User.builder()
//                .username(adminData)
//                .password(adminData)
//                .role(RoleType.ADMIN)
//                .email(adminEmail)
//                .address(adminCityAddress)
//                .phoneNumber(adminPhoneNumber)
//                .enabled(true)
//                .locked(false)
//                .build();
//        when(converter.convert(any(User.class), eq(UserEntity.class))).thenReturn(UserEntity.builder().build());
//        when(userRepository.findByEmail(user.email())).thenThrow(EntityAlreadyExistsException.class);
//
//        assertThrows(EntityAlreadyExistsException.class, () -> userService.saveUserAndSendConfirmationToken(user));
//    }
//}