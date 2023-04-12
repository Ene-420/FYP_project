package com.example.fypproject.Model;

public class LocationModel {

    private String userId;
    private double userLongitude;
    private double userLatittude;

    public LocationModel() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getUserLongitude() {
        return userLongitude;
    }

    public void setUserLongitude(double userLongitude) {
        this.userLongitude = userLongitude;
    }

    public double getUserLatittude() {
        return userLatittude;
    }

    public void setUserLatittude(double userLatittude) {
        this.userLatittude = userLatittude;
    }
}
