package com.example.dropoflife.Classes;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;

/**
 * author Bashar Khouri
 */
public class User implements Parcelable {

    private String userName;
    private BloodType bloodType;
    private Date dateOfBirth;
    private String sex;
    private Date dateOfLastDonation;
    private int numberOfDonations;
    private String email;
    private String profilePic;
    private String phone;
    private Roles role;
    private String hospitalID;
    private String userID;

    /**
     * @param userName
     * @param bloodType   type @<code>{BloodType}</code>  note that the BloodID must be between 0-8 OR @<code>Exception IncorrectBloodIDException will be throne.</code>
     * @param dateOfBirth type Date
     * @param sex         type String
     * @param email
     * @param profilePic
     */

    public User(String userID, String userName, BloodType bloodType, Date dateOfBirth, String sex, String email, String profilePic, Roles role) {
        this.userID = userID;
        this.role = role;
        this.userName = userName;
        this.bloodType = bloodType;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
        this.email = email;
        this.profilePic = profilePic;
        numberOfDonations = 0;
    }

    public User(String userName, String email, String profilePic) {

        this.userName = userName;

        this.profilePic = profilePic;

        this.email = email;

        numberOfDonations = 0;
    }

    public User() {
        // public no-arg constructor needed
    }


    protected User(Parcel in) {
        userName = in.readString();
        sex = in.readString();
        numberOfDonations = in.readInt();
        email = in.readString();
        profilePic = in.readString();
        phone = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
    // The Getters


    public String getHospitalID() {
        return hospitalID;
    }

    public String getUserID() {
        return userID;
    }

    public Roles getRole() {
        return role;
    }

    public String getUserName() {
        return userName;
    }

    /**
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


    public void setHospitalID(String hospitalID) {
        this.hospitalID = hospitalID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
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

    public void setRole(Roles role) {
        this.role = role;
    }

    public void setHospital(String hospitalID) {
        this.hospitalID = hospitalID;
    }

    public String getHospital() {
        return hospitalID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userName);
        dest.writeString(sex);
        dest.writeInt(numberOfDonations);
        dest.writeString(email);
        dest.writeString(profilePic);
        dest.writeString(phone);
        dest.writeString(userID);

    }
}
