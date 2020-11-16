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

    private Hospitals hospital;
    private int bloodTypeID;
    private String description;
    private Date dateOfPublish;
    private String ID ;

    public Post(String ID,Hospitals hospital, int bloodTypeID, String description, Date dateOfPublish) {
        this.ID = ID;
        this.hospital = hospital;
        this.bloodTypeID = bloodTypeID;
        this.description = description;
        this.dateOfPublish = dateOfPublish;

    }

    public Post() {
        //auto gen
    }

        //setters
    public void setID(String ID) {
        this.ID = ID;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBloodTypeID(int bloodTypeID) {
        this.bloodTypeID = bloodTypeID;
    }


    public void setHospital(Hospitals hospital) {
        this.hospital = hospital;
    }

    public void setDateOfPublish(Date dateOfPublish) {
        this.dateOfPublish = dateOfPublish;
    }

    //getters
    public String getID() {
        return ID;
    }

    public Hospitals getHospital() {
        return hospital;
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
