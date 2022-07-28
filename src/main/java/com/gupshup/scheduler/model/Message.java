/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gupshup.scheduler.model;

import java.util.Optional;
import java.util.regex.Pattern;

/**
 *
 * @author Emmanuel Paul
 */
public class Message {
    private Long recipient;

    private Long sender;

    private String message;
    private String sendAt;
    private int id;

    public Message(int id, Long recipient, Long sender, String message, String sendAt) {
        this.id = id;
        this.recipient = recipient;
        this.message = message;
        this.sendAt = sendAt;
        this.sender = sender;
    }

    public Long getRecipient() {
        return recipient;
    }

    public void setRecipient(Long recipient) {
        this.recipient = recipient;
    }

    public Long getSender() { return sender; }

    public void setSender(Long sender) { this.sender = sender; }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSendAt() {
        return sendAt;
    }

    public void setSendAt(String sendAt) {
        this.sendAt = sendAt;
    }
    
    public Optional<Integer> getId() {
        return Optional.ofNullable(id);
    }

    public void setId(int id) {
        this.id = id;
    }



    @Override
    public String toString() {
        return "Message{" +
                "recipient=" + recipient +
                ", sender=" + sender +
                ", message='" + message + '\'' +
                ", sendAt='" + sendAt + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
