package com.gupshup.scheduler.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gupshup.scheduler.dto.AuthRequest;
import com.gupshup.scheduler.model.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith( SpringJUnit4ClassRunner.class )
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    ObjectMapper object = new ObjectMapper();

    @Test
    public void validCredentialsTest () throws Exception {
        AuthRequest authRequest = new AuthRequest("emmanuel.paul@gupshup.io", "password");
        ResponseEntity<Response> response = restTemplate.postForEntity("/authenticate", authRequest, Response.class);

        assertThat(response.getBody().getMessage()).startsWith("eyJhbGciOiJIUzI1NiJ9.").withFailMessage("Needs to generate a valid token");
    }

    @Test
    public void invalidEmailTest () throws Exception {
        AuthRequest authRequest = new AuthRequest("invalid.user@gupshup.io", "password");
        ResponseEntity<Response> response = restTemplate.postForEntity("/authenticate", authRequest, Response.class);

        assertThat(response.getBody().getMessage()).isEqualTo("Invalid user credentials!").withFailMessage("Needs to throw error");
    }

    @Test
    public void invalidPasswordTest () throws Exception {
        AuthRequest authRequest = new AuthRequest("emmanuel.paul@gupshup.io", "pass");
        ResponseEntity<Response> response = restTemplate.postForEntity("/authenticate", authRequest, Response.class);

        assertThat(response.getBody().getMessage()).isEqualTo("Invalid user credentials!").withFailMessage("Needs to throw error");
    }
}
