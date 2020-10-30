package com.example.dropoflife.Classes;

import java.util.Date;

public class Post {
    private User user ;
    private BloodType bloodType;
    private String description;
    private Date dateOfPublish;
    Post( User user , BloodType bloodType , String description , Date dateOfPublish){
        this.user = user ;
        this.bloodType= bloodType;
        this.description=description;
        this.dateOfPublish=dateOfPublish;
    }
}
