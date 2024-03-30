package com.weatherbeaconboard.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weatherbeaconboard.repository.ConfirmationTokenRepository;
import com.weatherbeaconboard.repository.UserRepository;
import com.weatherbeaconboard.service.JwtUtil;
import com.weatherbeaconboard.service.converter.NullSafeConverter;
import com.weatherbeaconboard.service.security.CustomUserDetailsFetcher;
import com.weatherbeaconboard.service.user.UserService;
import com.weatherbeaconboard.web.model.User;
import com.weatherbeaconboard.web.model.UserDetailsResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static com.weatherbeaconboard.model.enums.RoleType.USER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private UserService userService;
    @MockBean
    private ConfirmationTokenRepository confirmationTokenRepository;
    @MockBean
    private BCryptPasswordEncoder passwordEncoder;
    @MockBean
    private NullSafeConverter converter;
    @MockBean
    private JwtUtil jwtUtil;
    @MockBean
    private CustomUserDetailsFetcher userDetailsFetcher;

    @Mock
    private UserDetails userDetails;
    @Mock
    private UserDetailsResponse userDetailsResponse;

    private User user;

    @BeforeEach
    void setup() {
        final String username = "username1";
        final String password = "123456789";
        final String firstName = "Armii";
        final String ciuci = "Ciucii";
        final String phoneNumber = "0777123456";
        final String email = "alex1@gmail.com";
        final String cityAddress = "Caracal1";

        user = User.builder()
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
    void findUserById_Success() throws Exception {
        when(userService.getById(1)).thenReturn(user);

        mockMvc.perform(get("/v1/users/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void deleteUserById_Success() throws Exception {
        mockMvc.perform(delete("/v1/users/{id}", 1))
                .andExpect(status().isNoContent());

        verify(userService).deleteById(1);
    }

    @Test
    void updateUser_Success() throws Exception {
        User updatedUser = User.builder()
                .address("newAddress")
                .build();
        when(userService.update(anyInt(), any(User.class))).thenReturn(updatedUser);

        mockMvc.perform(patch("/v1/users/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(updatedUser)))
                .andExpect(status().isOk());
    }

    @Test
    void register_Success() throws Exception {
        final String token = "registrationToken";
        when(userService.register(any(User.class))).thenReturn(token);

        mockMvc.perform(post("/v1/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(user)))
                .andExpect(status().isCreated())
                .andExpect(content().string(token));
    }

    @Test
    void confirmEmailValidation_Success() throws Exception {
        String token = "validToken";

        mockMvc.perform(post("/v1/confirmEmail")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(token))
                .andExpect(status().isOk());

        verify(userService).confirmToken(token);
    }

    @Test
    void getUserDetails_Success() throws Exception {

        when(userService.loadUserByUsername("username")).thenReturn(userDetails);
        when(converter.convert(userDetails, UserDetailsResponse.class)).thenReturn(userDetailsResponse);

        mockMvc.perform(get("/v1/userDetails")
                        .param("username", "username"))
                .andExpect(status().isOk());
    }
}
