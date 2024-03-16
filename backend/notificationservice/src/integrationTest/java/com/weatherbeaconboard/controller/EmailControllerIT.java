package com.weatherbeaconboard.controller;

import com.weatherbeaconboard.web.model.EmailRequest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.http.ContentType.JSON;

class EmailControllerIT {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8082";
        RestAssured.port = 443;
    }

    @Test
    void sendEmailIntegrationTest() {
        final EmailRequest emailRequest = new EmailRequest("example@example.com", "Subject", "Body text");

        RestAssured.given()
                .contentType(JSON)
                .body(emailRequest)
                .when()
                .post("/v1/sendEmail")
                .then()
                .statusCode(200);
    }
}