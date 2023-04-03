package com.example.fypproject.Model;

public class MessageModel {

    private String message, userId;
    private long timeStamp;

    public MessageModel() {
    }

    public MessageModel(String message, String userId, long timeStamp) {
        this.message = message;
        this.userId = userId;
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
