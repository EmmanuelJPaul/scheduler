package com.gupshup.scheduler.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gupshup.scheduler.dto.AuthRequest;
import com.gupshup.scheduler.dto.MessageRequest;
import com.gupshup.scheduler.model.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith( SpringJUnit4ClassRunner.class )
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ScheduleControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    ObjectMapper object = new ObjectMapper();
    String jwt;

    public ResponseEntity<Response> call (String jwt, String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("authorization", "Bearer " + jwt);
        HttpEntity<String> entity = new HttpEntity<>(message, headers);
        return restTemplate.postForEntity("/schedule", entity, Response.class);
    }

    @Before
    public void getToken () {
        AuthRequest authRequest = new AuthRequest("emmanuel.paul@gupshup.io", "password");
        ResponseEntity<Response> authResponse = restTemplate.postForEntity("/authenticate", authRequest, Response.class);
        jwt = authResponse.getBody().getMessage();
    }

    @Test
    @Transactional
    @Rollback(true)
    public void validMessageTest () throws Exception {
        MessageRequest messageRequest = new MessageRequest(919790876687l, "A test message!", "2022-07-26 22:49:00");
        ResponseEntity<Response> response = call(jwt, object.writeValueAsString(messageRequest));

        assertThat(response.getBody().getMessage()).isEqualTo("Message scheduled successfully!").withFailMessage("Must execute successfully");
    }

    @Test
    public void invalidRecipientTest () throws Exception {
        MessageRequest messageRequest = new MessageRequest(9790876687l, "A test message!", "2022-07-26 22:49:00");
        ResponseEntity<Response> response = call(jwt, object.writeValueAsString(messageRequest));
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST).withFailMessage("Invalid Recipient. Must fail with Bad Request");

        MessageRequest messageRequest2 = new MessageRequest(2l, "A test message!", "2022-07-26 22:49:00");
        ResponseEntity<Response> response2 = call(jwt, object.writeValueAsString(messageRequest2));
        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST).withFailMessage("Null Recipient. Must fail with Bad Request");
    }

    @Test
    public void invalidMessageTest () throws Exception {
        MessageRequest messageRequest = new MessageRequest(919790876687l, "A!", "2022-07-26 22:49:00");
        ResponseEntity<Response> response = call(jwt, object.writeValueAsString(messageRequest));
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST).withFailMessage("Invalid Message. Must fail with Bad Request");

        MessageRequest messageRequest2 = new MessageRequest(919790876687l, null, "2022-07-26 22:49:00");
        ResponseEntity<Response> response2 = call(jwt, object.writeValueAsString(messageRequest2));
        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST).withFailMessage("Null Message. Must fail with Bad Request");
    }

    @Test
    public void invalidSendAtTest () throws Exception {
        MessageRequest messageRequest = new MessageRequest(919790876687l, "A test message!", "2022-07-26 22:4:00");
        ResponseEntity<Response> response = call(jwt, object.writeValueAsString(messageRequest));
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST).withFailMessage("Invalid Sent At. Must fail with Bad Request");

        MessageRequest messageRequest2 = new MessageRequest(919790876687l, "A test message!", null);
        ResponseEntity<Response> response2 = call(jwt, object.writeValueAsString(messageRequest2));
        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST).withFailMessage("Null Sent At. Must fail with Bad Request");
    }

}
