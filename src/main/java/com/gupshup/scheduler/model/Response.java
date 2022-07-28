package com.gupshup.scheduler.model;

/**
 *
 * @author Emmanuel Paul
 */
public class Response {
    
    private String message;

    public Response() {}
    public Response(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
