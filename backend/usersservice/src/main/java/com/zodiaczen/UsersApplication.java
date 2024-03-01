package com.zodiaczen;

import com.zodiaczen.model.ConfirmationTokenEntity;
import com.zodiaczen.model.UserEntity;
import com.zodiaczen.model.enums.RoleType;
import com.zodiaczen.repository.ConfirmationTokenRepository;
import com.zodiaczen.repository.UserRepository;
import com.zodiaczen.service.user.UserService;
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
public class UsersApplication implements CommandLineRunner {

    private final UserService userService;
    private final UserRepository userRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;

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
    private void insertPredefinedAdmin() {
        String adminData = "a";
        String adminPhoneNumber = "1234567890";
        String adminEmail = "auctionshunters@gmail.com";
        String adminCityAddress = "Caracal";
        OffsetDateTime now = getDateTime();

        if (!userService.isUserEmailAlreadyRegistered(adminEmail)) {
            UserEntity admin = UserEntity.builder()
                    .username(adminData)
                    .password(adminData)
                    .role(RoleType.ADMIN)
                    .email(adminEmail)
                    .address(adminCityAddress)
                    .phoneNumber(adminPhoneNumber)
                    .enabled(true)
                    .locked(false)
                    .build();

            userRepository.save(admin);

            ConfirmationTokenEntity adminConfirmationToken = ConfirmationTokenEntity.builder()
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
