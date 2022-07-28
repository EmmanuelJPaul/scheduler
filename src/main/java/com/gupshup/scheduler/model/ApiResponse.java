/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gupshup.scheduler.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author Emmanuel Paul
 */
public class ApiResponse {
    
    @JsonProperty
    private String status;
    
    @JsonProperty
    private String messageId;

    public String getStatus() {
        return status;
    }

    public String getMessageId() {
        return messageId;
    }
            
    @Override
    public String toString() {
        return "ApiResponse{" + "status=" + status + ", messageId=" + messageId + '}';
    }
    
}