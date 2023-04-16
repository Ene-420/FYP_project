package com.example.fypproject.Model;

import java.text.SimpleDateFormat;
import java.util.Date;

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

    public String setTimeStamp(long timeStamp) {

        Date date = new Date(timeStamp);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        String dateSet = format.format(date);
        return dateSet;
    }
}
