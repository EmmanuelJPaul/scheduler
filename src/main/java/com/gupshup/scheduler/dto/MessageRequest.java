package com.gupshup.scheduler.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class MessageRequest {
    @NotNull(message = "Recipient is required!")
    @Pattern(regexp = "^[0-9]{12}$", message = "Recipient must be 12 digits!")
    private String recipient;

    @NotEmpty(message = "Message is required!")
    @Pattern(regexp = "^[\\w\\W]{3,255}+$", message = "Message format is invalid!")
    private String message;

    @NotNull(message = "Sent At is required!")
    @Pattern(regexp = "\\d{4}-[01]\\d-[0-3]\\d\\s[0-2]\\d:[0-5]\\d:[0-5]\\d(?:\\.\\d+)?Z?", message = "Sent At format is invalid!")
    private String sendAt;

    public MessageRequest() {}

    public MessageRequest(Long recipient, String message, String sendAt) {
        this.recipient = recipient.toString();
        this.message = message;
        this.sendAt = sendAt;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessage() {
        return message;
    }

    public String getSendAt() {
        return sendAt;
    }
}
