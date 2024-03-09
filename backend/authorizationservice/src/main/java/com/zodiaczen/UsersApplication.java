package com.zodiaczen;

import com.zodiaczen.model.ConfirmationTokenEntity;
import com.zodiaczen.model.UserEntity;
import com.zodiaczen.model.enums.RoleType;
import com.zodiaczen.repository.ConfirmationTokenRepository;
import com.zodiaczen.repository.UserRepository;
import com.zodiaczen.security.PasswordEncoder;
import com.zodiaczen.service.user.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.OffsetDateTime;

import static com.zodiaczen.utils.DateUtils.getDateTime;

@Slf4j
@SpringBootApplication
@AllArgsConstructor
@Transactional
public class UsersApplication implements CommandLineRunner {

    private final UserService userService;
    private final UserRepository userRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(UsersApplication.class, args);
    }

    @Override
    public void run(String... args) {
        insertPredefinedAdmin();
    }

    /**
     * Method used for inserting a predefined user in the database for debugging purposes.
     * TODO: remove before deploying.
     */
    @Transactional
    protected void insertPredefinedAdmin() {
        String adminData = "a";
        String adminPhoneNumber = "1234567890";
        String adminEmail = "auctionshunters@gmail.com";
        String adminCityAddress = "Caracal";
        OffsetDateTime now = OffsetDateTime.now();

        if (!userService.isUserEmailAlreadyRegistered(adminEmail)) {
            UserEntity admin = UserEntity.builder()
                    .id(1)
                    .username(adminData)
                    .password(passwordEncoder.bCryptPasswordEncoder().encode("12345678"))
                    .firstName("Armi")
                    .lastName("Ciuci")
                    .role(RoleType.ADMIN)
                    .email(adminEmail)
                    .birthday(now)
                    .address(adminCityAddress)
                    .phoneNumber(adminPhoneNumber)
                    .enabled(true)
                    .locked(false)
                    .build();

            userRepository.save(admin);

            ConfirmationTokenEntity adminConfirmationToken = ConfirmationTokenEntity.builder()
                    .id(1)
                    .token(adminData)
                    .tokenCreatedAt(now)
                    .tokenExpiresAt(now)
                    .tokenConfirmedAt(now)
                    .user(admin)
                    .build();

            confirmationTokenRepository.save(adminConfirmationToken);
        }
    }
}
