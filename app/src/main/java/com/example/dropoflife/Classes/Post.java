package com.example.dropoflife.Classes;

import java.util.Date;

/**
 * author Bashar Khouri
 */
public class Post {
    private String userID ;
    private BloodType bloodType;
    private String description;
    private Date dateOfPublish;
    private String location ;
    private String picPath;

    public String getPicPath() {
        return picPath;
    }

    Post(String userID, BloodType bloodType, String description, Date dateOfPublish, String location, String picPath){
        this.userID = userID ;
        this.bloodType= bloodType;
        this.description=description;
        this.dateOfPublish=dateOfPublish;
        this.location=location;
        this.picPath = picPath;
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

    public String getUserID() {return userID;}

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
