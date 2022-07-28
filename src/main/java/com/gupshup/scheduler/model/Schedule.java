package com.gupshup.scheduler.model;

import java.util.Date;
import java.util.Optional;

public class Schedule {

    private int id;
    private int messageId;
    private int userId;
    private Date scheduleTime;
    private String status;
    private long recipient;
    private Optional<String> apiMessageId;
    private Optional<String> apiStatusReport;

    public Schedule() {}

    public Schedule(int id, int messageId, int userId, Date scheduleTime, String status, long recipient, Optional<String> apiMessageId, Optional<String> apiStatusReport) {
        this.id = id;
        this.messageId = messageId;
        this.userId = userId;
        this.scheduleTime = scheduleTime;
        this.status = status;
        this.recipient = recipient;
        this.apiMessageId = apiMessageId;
        this.apiStatusReport = apiStatusReport;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(Date scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getRecipient() {
        return recipient;
    }

    public void setRecipient(long recipient) {
        this.recipient = recipient;
    }

    public Optional<String> getApiMessageId() {
        return apiMessageId;
    }

    public void setApiMessageId(Optional<String> apiMessageId) {
        this.apiMessageId = apiMessageId;
    }

    public Optional<String> getApiStatusReport() {
        return apiStatusReport;
    }

    public void setApiStatusReport(Optional<String> apiStatusReport) {
        this.apiStatusReport = apiStatusReport;
    }
}
