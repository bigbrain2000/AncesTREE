package com.weatherbeaconboard.web.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.io.Serializable;

/**
 * Web model for an email request.
 *
 * @param to      the recipient's email address
 * @param subject the subject of the email
 * @param body    the content of the email
 */
@Builder
public record EmailRequest(

        @NotBlank
        String to,

        @NotBlank
        String subject,

        @NotBlank
        String body

) implements Serializable {

}
