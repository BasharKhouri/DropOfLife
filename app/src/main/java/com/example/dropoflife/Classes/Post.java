package com.example.dropoflife.Classes;

import java.util.Date;

public class Post {
    private User user ;
    private BloodType bloodType;
    private String description;
    private Date dateOfPublish;
    private String location ;
    Post( User user , BloodType bloodType , String description , Date dateOfPublish,String location){
        this.user = user ;
        this.bloodType= bloodType;
        this.description=description;
        this.dateOfPublish=dateOfPublish;
        this.location=location;
    }

    public String getLocation() {
        return location;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }

    public User getUser() {
        return user;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public String getDescription() {
        return description;
    }

    public Date getDateOfPublish() {
        return dateOfPublish;
    }
}
