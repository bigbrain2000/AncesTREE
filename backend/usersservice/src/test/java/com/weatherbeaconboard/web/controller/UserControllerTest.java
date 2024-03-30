package com.weatherbeaconboard.web.controller;

import com.weatherbeaconboard.service.converter.NullSafeConverter;
import com.weatherbeaconboard.service.user.UserService;
import com.weatherbeaconboard.web.model.User;
import com.weatherbeaconboard.web.model.UserDetailsResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.OffsetDateTime;

import static com.weatherbeaconboard.model.enums.RoleType.USER;
import static com.weatherbeaconboard.utils.DateUtils.getDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private NullSafeConverter converter;

    @InjectMocks
    private UserController userController;

    private User expectedUser;

    @BeforeEach
    void setup() {
        final String username = "username";
        final String password = "12345678";
        final String firstName = "Armi";
        final String ciuci = "Ciuci";
        final String phoneNumber = "1234567890";
        final String email = "alex@gmail.com";
        final String cityAddress = "Caracal";
        final OffsetDateTime NOW = getDateTime();

        expectedUser = User.builder()
                .id(1)
                .username(username)
                .password(password)
                .firstName(firstName)
                .lastName(ciuci)
                .role(USER)
                .email(email)
                .address(cityAddress)
                .phoneNumber(phoneNumber)
                .enabled(false)
                .locked(false)
                .version(0)
                .build();
    }

    @Test
    void findUserById_Success() {
        when(userService.getById(anyInt())).thenReturn(expectedUser);

        final ResponseEntity<User> response = userController.findUserById(1);

        assertEquals(OK, response.getStatusCode());
        assertEquals(expectedUser, response.getBody());
    }

    @Test
    void deleteUserById_Success() {
        doNothing().when(userService).deleteById(anyInt());

        final ResponseEntity<Void> response = userController.deleteUserById(expectedUser.id());

        assertEquals(NO_CONTENT, response.getStatusCode());
        verify(userService).deleteById(1);
    }

    @Test
    void updateUser_Success() {
        final User user = User.builder().build();
        when(userService.update(anyInt(), any(User.class))).thenReturn(user);

        final ResponseEntity<User> response = userController.updateUser(1, user);

        assertEquals(OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void register_Success() {
        when(userService.register(any(User.class))).thenReturn("token");

        final ResponseEntity<String> response = userController.register(User.builder().build());

        assertEquals(CREATED, response.getStatusCode());
        assertEquals("token", response.getBody());
    }

    @Test
    void confirmEmailValidation_Success() {
        final ResponseEntity<Void> response = userController.confirmEmailValidation("token");

        assertEquals(OK, response.getStatusCode());
        verify(userService).confirmToken("token");
    }

    @Test
    void getUserDetails_Success() {
        final UserDetails userDetails = mock(UserDetails.class);
        final UserDetailsResponse userDetailsResponse = UserDetailsResponse.builder().build();
        when(userService.loadUserByUsername(anyString())).thenReturn(userDetails);
        when(converter.convert(any(UserDetails.class), eq(UserDetailsResponse.class))).thenReturn(userDetailsResponse);

        final ResponseEntity<UserDetailsResponse> response = userController.getUserDetails("username");

        assertEquals(OK, response.getStatusCode());
        assertEquals(userDetailsResponse, response.getBody());
    }
}