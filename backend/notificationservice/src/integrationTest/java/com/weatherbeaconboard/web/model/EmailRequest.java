package com.weatherbeaconboard.web.model;

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

        String to,

        String subject,

        String body

        )implements Serializable{

        }
