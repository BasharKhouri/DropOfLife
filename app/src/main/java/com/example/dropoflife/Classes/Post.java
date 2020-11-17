package com.example.dropoflife.Classes;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;

/**
 * author Bashar Khouri
 */
public class Post {

    public String hospitalID;
    private int bloodTypeID;
    private String description;
    private Date dateOfPublish;
    private String postID ;

    public Post(String postID,String hospitalID, int bloodTypeID, String description, Date dateOfPublish) {
        this.postID = postID;
        this.hospitalID = hospitalID;
        this.bloodTypeID = bloodTypeID;
        this.description = description;
        this.dateOfPublish = dateOfPublish;

    }

    public Post() {
        //auto gen
    }

        //setters
    public void setPostID(String postID) {
        this.postID = postID;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBloodTypeID(int bloodTypeID) {
        this.bloodTypeID = bloodTypeID;
    }

    public void setHospitalID(String hospitalID) {
        this.hospitalID = hospitalID;
    }

    public void setDateOfPublish(Date dateOfPublish) {
        this.dateOfPublish = dateOfPublish;
    }

    //getters
    public String getPostID() {
        return postID;
    }

    public String getHospitalID() {
        return hospitalID;
    }

    public int bloodTypeID() {
        return bloodTypeID;
    }

    public String getDescription() {
        return description;
    }

    public Date getDateOfPublish() {
        return dateOfPublish;
    }

    public int getBloodTypeID() {
        return bloodTypeID;
    }
}
