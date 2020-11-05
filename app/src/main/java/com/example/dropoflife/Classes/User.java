package com.example.dropoflife.Classes;

import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;

/**
 * author Bashar Khouri
 */
public class User {

    private  String userName;
    private BloodType bloodType;
    private Date dateOfBirth;
    private String sex;
    private Date dateOfLastDonation;
    private int numberOfDonations;
    private String email;


    private String profilePic;
    private String phone;



    /**
     *
     * @param userName
     * @param bloodType  type @<code>{BloodType}</code>  note that the BloodID must be between 0-8 OR @<code>Exception IncorrectBloodIDException will be throne.</code>
     * @param dateOfBirth type Date
     * @param sex type String
     * @param email
     * @param profilePic
     */

    public User( String userName, BloodType bloodType, Date dateOfBirth, String sex, String email, String profilePic) {

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


    public String getUserName() {
        return userName;
    }

    /**
     *
     * @return profilePic : it is the URL of the profile picture that is stored in the firebase database
     */
    public String getProfilePic() {
        return profilePic;
    }

    public String getPhone() {
        return phone;
    }

    public BloodType getBloodType() {
        return bloodType;
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

    public void setProfilePic(String profilePic) {
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

    @Override
    public String toString() {
        return getUserName();
    }
}
