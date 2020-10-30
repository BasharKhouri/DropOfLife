package com.example.dropoflife.Classes;

import java.util.Date;

public class User {
    private  String fireBaseAuthID;
    private String fullName;
    private Date dateOfLastDonation;
    private int numberOfDonations;
    private BloodType bloodType;
    private Date dateOfBirth;
    private String sex;

    public User(String fireBaseAuthID, String fullName, Date dateOfBirth, String sex){
        this.fireBaseAuthID= fireBaseAuthID;
        this.numberOfDonations=0;
        dateOfLastDonation=null;
        this.fullName=fullName;
        this.dateOfBirth=dateOfBirth;
        this.sex=sex;
        try{
            bloodType=new BloodType(0);
        }catch (Exception e ){
            System.out.println(e.getMessage());
        }
    }

    public String getFireBaseAuthID() {
        return fireBaseAuthID;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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

}
