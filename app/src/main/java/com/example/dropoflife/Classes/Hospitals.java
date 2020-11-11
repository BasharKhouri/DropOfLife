package com.example.dropoflife.Classes;

import com.google.firebase.firestore.GeoPoint;

/**
 * @author Bashar
 */
public class Hospitals {
    String name ;
    GeoPoint location;
    String phoneNumber;
    String profilePic;
    String address;
    Hospitals(){/*Ignore  Constructor For Firebase*/}
    /**
     *
     * @param name name of the Hospital
     * @param location for the geographical location of the hospital
     * @param phoneNumber for the receptionist
     * @param profilePic the Path for the LOGO
     * @param address the physical location for the Hospital
     */
    public Hospitals(String name, GeoPoint location, String phoneNumber, String profilePic, String address) {
        this.name = name;
        this.location=location;
        this.phoneNumber = phoneNumber;
        this.profilePic = profilePic;
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public String getAddress() {
        return address;
    }
}
