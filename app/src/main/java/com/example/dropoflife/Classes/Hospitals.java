package com.example.dropoflife.Classes;

/**
 * @author Bashar
 */
public class Hospitals {
    String name ;
    String longitude ;
    String latitude;
    String PhoneNumber;
    String profilePic;
    String address;
    Hospitals(){/*Ignore  Constructor For Firebase*/}
    /**
     *
     * @param name name of the Hospital
     * @param longitude for the Geographical location
     * @param latitude for the Geographical location
     * @param phoneNumber for the receptionist
     * @param profilePic the Path for the LOGO
     * @param address the physical location for the Hospital
     */
    public Hospitals(String name, String longitude, String latitude, String phoneNumber, String profilePic, String address) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        PhoneNumber = phoneNumber;
        this.profilePic = profilePic;
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
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

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public String getAddress() {
        return address;
    }
}
