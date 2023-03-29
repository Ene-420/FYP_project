package com.example.fypproject;

import java.io.Serializable;
import java.util.ArrayList;

public class UserModel implements Serializable {

    private String dob;
    private String fullName, userName, userID;
    private String course, courseYr, university;
    private String email, password;
    private ArrayList<String> hobbies;
    private String profileImage;

    public UserModel() {
    }

    public UserModel(String fullName, String course, String courseYr, String university, String email, ArrayList<String> hobbies, String profileImage, String dob) {
        this.fullName = fullName;
        this.course = course;
        this.courseYr = courseYr;
        this.university = university;
        this.email = email;
        this.hobbies = hobbies;
        this.profileImage = profileImage;
        this.dob = dob;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String firstName, String lastName) {
        this.fullName = firstName +"_"+ lastName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getCourseYr() {
        return courseYr;
    }

    public void setCourseYr(String courseYr) {
        this.courseYr = courseYr;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<String> getHobbies() {
        return hobbies;
    }

    public void setHobbies(ArrayList<String> hobbies) {
        this.hobbies = hobbies;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
