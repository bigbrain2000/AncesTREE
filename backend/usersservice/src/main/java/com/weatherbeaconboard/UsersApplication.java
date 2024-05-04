package com.weatherbeaconboard;

import com.weatherbeaconboard.model.ConfirmationTokenEntity;
import com.weatherbeaconboard.model.UserEntity;
import com.weatherbeaconboard.model.enums.RoleType;
import com.weatherbeaconboard.repository.ConfirmationTokenRepository;
import com.weatherbeaconboard.repository.UserRepository;
import com.weatherbeaconboard.service.user.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.OffsetDateTime;

import static com.weatherbeaconboard.utils.DateUtils.getDateTime;

@Slf4j
@SpringBootApplication
@AllArgsConstructor
@Transactional
public class UsersApplication implements CommandLineRunner {

    private final UserService userService;
    private final UserRepository userRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(UsersApplication.class, args);
    }

    @Override
    public void run(String... args) {
        insertPredefinedAdmin();
    }

    /**
     * Method used for inserting a predefined user in the database for debugging purposes.
     */
    @Transactional
    protected void insertPredefinedAdmin() {
        String adminData = "a";
        String adminPhoneNumber = "1234567890";
        String adminEmail = "auctionshunters@gmail.com";
        String adminCityAddress = "Caracal";
        OffsetDateTime now = getDateTime();

        if (!userService.isUserEmailAlreadyRegistered(adminEmail)) {
            UserEntity admin = UserEntity.builder()
                    .id(1)
                    .username(adminData)
                    .password(passwordEncoder.encode("12345678"))
                    .firstName("Armi")
                    .lastName("Ciuci")
                    .role(RoleType.ADMIN)
                    .email(adminEmail)
                    .address(adminCityAddress)
                    .phoneNumber(adminPhoneNumber)
                    .enabled(true)
                    .locked(false)
                    .version(0)
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
