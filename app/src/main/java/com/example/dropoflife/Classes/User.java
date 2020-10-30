package com.example.dropoflife.Classes;

import java.util.Date;

public class User {
    private  String fireBaseAuthID;
    private  String userNationalID;
    private String fullName;
    private Date dateOfLastDonation;
    private int numberOfDonations;
    private BloodType bloodType;
    private Date dateOfBirth;
    private String residentialAddress;
    private String sex;

    User(String fireBaseAuthID , String userNationalID, String fullName , Date dateOfBirth ,String sex){
        this.fireBaseAuthID= fireBaseAuthID;
        this.userNationalID=userNationalID;
        this.fullName=fullName;
        this.dateOfBirth=dateOfBirth;
        this.sex=sex;
    }




    public String getFireBaseAuthID() {
        return fireBaseAuthID;
    }

    public String getUserNationalID() {
        return userNationalID;
    }

    public String getFullName() {
        return fullName;
    }

    public Date getDateOfLastDonation() {
        return dateOfLastDonation;
    }

    public int getNumberOfDonations() {
        return numberOfDonations;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getResidentialAddress() {
        return residentialAddress;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setResidentialAddress(String residentialAddress) {
        this.residentialAddress = residentialAddress;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }

    public void setNumberOfDonations(int numberOfDonations) {
        this.numberOfDonations = numberOfDonations;
    }

    public void setDateOfLastDonation(Date dateOfLastDonation) {
        this.dateOfLastDonation = dateOfLastDonation;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setUserNationalID(String userNationalID) {
        this.userNationalID = userNationalID;
    }
}
