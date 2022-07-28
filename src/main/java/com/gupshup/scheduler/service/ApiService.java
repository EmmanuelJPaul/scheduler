/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gupshup.scheduler.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gupshup.scheduler.model.Message;
import com.gupshup.scheduler.model.ApiResponse;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Emmanuel Paul
 */
@Service
public class ApiService {

    private final String API_ENDPOINT;

    private final String API_KEY;
    private final RestTemplate restTemplate;
    
    public ApiService(RestTemplateBuilder restTemplateBuilder, @Value("${gupshup.api.endpoint}") String endpoint, @Value("${gupshup.api.key}") String apiKey) {
        this.restTemplate = restTemplateBuilder.build();
        this.API_ENDPOINT = endpoint;
        this.API_KEY = apiKey;
    }
    
    private String generateBody (Message message) {
        return "channel=whatsapp&source="+message.getSender()+"&destination="+message.getRecipient()+"&message=%7B%22type%22:%22text%22,%22text%22:%22"+message.getMessage()+"%22%7D&src.name=SchedulerApp";
    }
    
    public ApiResponse sendMessage (Message message) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("apikey", API_KEY);
        
        HttpEntity<String> entity = new HttpEntity<>(generateBody(message), headers);
        String response = this.restTemplate.postForObject(API_ENDPOINT, entity, String.class);

        // Convert JSON to Object
        ObjectMapper mapper = new ObjectMapper();
        ApiResponse responseObject = new ApiResponse();
        
        try {
            responseObject = mapper.readValue(response, ApiResponse.class);
        } catch (JsonProcessingException ex) {
             Logger.getLogger(ApiService.class.getName()).log(Level.SEVERE, null, ex);            
        }
        
        return responseObject;
    }
}
