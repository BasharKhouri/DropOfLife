package com.example.dropoflife.Classes;

import java.util.Date;

/**
 * author Bashar Khouri
 */
public class User {
    final private  String fireBaseAuthID;
    private BloodType bloodType;
    private Date dateOfBirth;
    private String sex;
    private Date dateOfLastDonation;
    private int numberOfDonations;


    /**
     *
     * @param userPhotoURL : is the URL of the profile picture, the URL is stored in firebase storage.
     */


    /**
     *
     * @return UserPhotoURL : it is the URL of the profile picture that is stored in the firebase database
     */

    /**
     *
     * @param fireBaseAuthID it is assigned to the user when he do the sign up it con not be changed after that.
     * @param dateOfBirth type Date
     * @param sex type String
     * @param bloodType  type @<code>{BloodType}</code>  note that the BloodID must be between 0-8 OR @<code>Exception IncorrectBloodIDException will be throne.</code>
     */
    public User(String fireBaseAuthID, Date dateOfBirth, String sex , BloodType bloodType){
        this.fireBaseAuthID= fireBaseAuthID;
        this.numberOfDonations=0;
        dateOfLastDonation=null;


        this.dateOfBirth=dateOfBirth;
        this.sex=sex;
       this.bloodType=bloodType;
    }


    // The Getters
    public String getFireBaseAuthID() {
        return fireBaseAuthID;
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

    // The Setters



    /**
     *
     * @param bloodID must be between 0-8  OR @<code>Exception IncorrectBloodIDException will be throne.</code>
     */
    public void setBloodType(int bloodID) {
        try {
            this.bloodType = new BloodType(bloodID);
        } catch (Exception e ){
            System.out.println(e.getMessage());
        }

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
