package com.weatherbeaconboard.service.confirmationtoken;

import com.weatherbeaconboard.model.ConfirmationTokenEntity;
import com.weatherbeaconboard.model.UserEntity;
import com.weatherbeaconboard.repository.ConfirmationTokenRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConfirmationTokenServiceImplTest {

    @Mock
    private ConfirmationTokenRepository confirmationTokenRepository;

    @InjectMocks
    private ConfirmationTokenServiceImpl uut;

    private final String validToken = "validToken";

    private ConfirmationTokenEntity validConfirmationToken;

    @BeforeEach
    void setup() {
        final OffsetDateTime now = OffsetDateTime.now();
        final OffsetDateTime oneDayAhead = now.plusDays(1);

        validConfirmationToken = new ConfirmationTokenEntity();
        validConfirmationToken.setId(1);
        validConfirmationToken.setToken(validToken);
        validConfirmationToken.setTokenCreatedAt(now);
        validConfirmationToken.setTokenExpiresAt(oneDayAhead);
    }

    @Test
    void getToken_validToken_returnsConfirmationTokenEntity() {
        when(confirmationTokenRepository.getByToken(anyString())).thenReturn(validConfirmationToken);

        final ConfirmationTokenEntity actualConfirmationTokenEntity = uut.getToken(validToken);

        assertEquals(validConfirmationToken, actualConfirmationTokenEntity);
        verify(confirmationTokenRepository, times(1)).getByToken(anyString());
    }

    @Test
    void getToken_invalidToken_throwsException() {
        when(confirmationTokenRepository.getByToken(anyString())).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> uut.getToken(validToken));
        verify(confirmationTokenRepository, times(1)).getByToken(anyString());
    }

    @Test
    void setConfirmedAt_validToken_confirmsTheEmailConfirmationDate() {
        doNothing().when(confirmationTokenRepository).updateTokenConfirmationDate(anyString(), any(OffsetDateTime.class));

        uut.setConfirmedAt(validToken);

        verify(confirmationTokenRepository, times(1)).updateTokenConfirmationDate(anyString(), any(OffsetDateTime.class));
    }

    @Test
    void deleteById_validId_deletesTheConfirmationTokenEntity() {
        doNothing().when(confirmationTokenRepository).deleteById(anyInt());

        uut.deleteById(validConfirmationToken.getId());

        verify(confirmationTokenRepository, times(1)).deleteById(anyInt());
    }

    @Test
    void saveAndReturnAConfirmationToken_validUser_returnsGeneratedToken() {
        final UserEntity userEntity = UserEntity.builder().build();
        when(confirmationTokenRepository.save(any(ConfirmationTokenEntity.class))).thenReturn(validConfirmationToken);

        final String actualToken = uut.saveAndReturnAConfirmationToken(userEntity);

        assertNotNull(actualToken);
    }
}