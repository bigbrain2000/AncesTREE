package com.weatherbeaconboard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weatherbeaconboard.controller.email.EmailService;
import com.weatherbeaconboard.web.controller.EmailController;
import com.weatherbeaconboard.web.model.EmailRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmailController.class)
@AutoConfigureMockMvc(addFilters = false)
class EmailControllerServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmailService emailService;

    @Test
    void sendEmail_Success() throws Exception {
        final EmailRequest emailRequest = new EmailRequest("example@example.com", "Subject", "Body text");
        doNothing().when(emailService).sendEmail(emailRequest);

        mockMvc.perform(post("/v1/sendEmail")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(emailRequest)))
                .andExpect(status().isOk());

        verify(emailService).sendEmail(any(EmailRequest.class));
    }
}
