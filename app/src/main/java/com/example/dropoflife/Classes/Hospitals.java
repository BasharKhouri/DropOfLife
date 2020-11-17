package com.example.dropoflife.Classes;

import com.google.firebase.firestore.GeoPoint;

/**
 * @author Bashar
 */
public class Hospitals {
    String name;
    String ID;
    GeoPoint location;
    String phoneNumber;
    String logo;
    String address;

    Hospitals() {/*Ignore  Constructor For Firebase*/}

    /**
     * @param name        name of the Hospital
     * @param location    for the geographical location of the hospital
     * @param phoneNumber for the receptionist
     * @param logo        the Path for the LOGO
     * @param address     the physical location for the Hospital
     */
    public Hospitals(String ID, String name, GeoPoint location, String phoneNumber, String logo, String address) {
        this.name = name;
        this.location = location;
        this.phoneNumber = phoneNumber;
        this.logo = logo;
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    public void setLogo(String logo) {
        this.logo = logo;
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


    public String getAddress() {
        return address;
    }

    public String getID() {
        return ID;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public String getLogo() {
        return logo;
    }

}
