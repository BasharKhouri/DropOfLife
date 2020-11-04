package com.example.dropoflife.Classes;

import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;

/**
 * author Bashar Khouri
 */
public class User {


    private  String fireBaseAuthID;
    private  String userName;
    private BloodType bloodType;
    private Date dateOfBirth;
    private String sex;
    private Date dateOfLastDonation;
    private int numberOfDonations;
    private String email;


    private Uri profilePic;
    private String phone;



    /**
     *
     * @param fireBaseAuthID it is assigned to the user when he do the sign up it con not be changed after that.
     * @param userName
     * @param bloodType  type @<code>{BloodType}</code>  note that the BloodID must be between 0-8 OR @<code>Exception IncorrectBloodIDException will be throne.</code>
     * @param dateOfBirth type Date
     * @param sex type String
     * @param email
     * @param profilePic
     */

    public User(String fireBaseAuthID, String userName, BloodType bloodType, Date dateOfBirth, String sex, String email, Uri profilePic) {
        this.fireBaseAuthID = fireBaseAuthID;
        this.userName = userName;
        this.bloodType = bloodType;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
        this.email = email;
        this.profilePic = profilePic;
        numberOfDonations=0;
    }
    public User(){
        // public no-arg constructor needed
    }

    // The Getters
    public String getFireBaseAuthID() {
        return fireBaseAuthID;
    }

    public String getUserName() {
        return userName;
    }

    /**
     *
     * @return profilePic : it is the URL of the profile picture that is stored in the firebase database
     */
    public Uri getProfilePic() {
        return profilePic;
    }

    public String getPhone() {
        return phone;
    }
    public String getBloodType() {
        return bloodType.getBloodType();
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getSex() {
        return sex;
    }

    public Date getDateOfLastDonation() {
        return dateOfLastDonation;
    }

    public int getNumberOfDonations() {
        return numberOfDonations;
    }

    public String getEmail() {
        return email;
    }

    // The Setters

    /**
     *
     * @param bloodID must be from 0 to 8  OR @<code>Exception IncorrectBloodIDException will be throne.</code>
     */

    public void setBloodType(int bloodID) {
        try {
            this.bloodType = new BloodType(bloodID);
        } catch (Exception e ){
            System.out.println(e.getMessage());
        }

    }


    public void setFireBaseAuthID(String fireBaseAuthID) {
        this.fireBaseAuthID = fireBaseAuthID;
    }

    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     *
     * @param profilePic : is the URL of the profile picture, the URL is stored in firebase storage.
     */

    public void setProfilePic(Uri profilePic) {
        this.profilePic = profilePic;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setDateOfLastDonation(Date dateOfLastDonation) {
        this.dateOfLastDonation = dateOfLastDonation;
    }

    public void setNumberOfDonations(int numberOfDonations) {
        this.numberOfDonations = numberOfDonations;
    }


}
