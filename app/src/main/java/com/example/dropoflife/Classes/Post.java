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

    private String userID;
    private int bloodTypeID;
    private String description;
    private Date dateOfPublish;
    private String location;
    private String phoneNumber;
    private User owner;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public Post(String userID, int bloodTypeID, String description, Date dateOfPublish, String location, String phoneNumber) {
        this.userID = userID;
        this.bloodTypeID = bloodTypeID;
        this.description = description;
        this.dateOfPublish = dateOfPublish;
        this.location = location;
        this.phoneNumber = phoneNumber;
        owner = getOwner();
    }

    public Post() {
        //auto gen
    }

    public User getOwner() {

        final User[] owner = new User[1];
        db.collection("users").whereEqualTo("fireBaseAuthID", userID).limit(1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                owner[0] = document.toObject(User.class);
                            }
                        } else {
                            owner[0] = null;
                            Log.w("", "Error getting documents.", task.getException());
                        }
                    }
                });
        return owner[0];
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBloodTypeID(int bloodTypeID) {
        this.bloodTypeID = bloodTypeID;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setDateOfPublish(Date dateOfPublish) {
        this.dateOfPublish = dateOfPublish;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public User owner() {
        return owner;
    }

    public String getUserID() {
        return userID;
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
