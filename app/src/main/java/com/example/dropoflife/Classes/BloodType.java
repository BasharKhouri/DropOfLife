package com.example.dropoflife.Classes;

/**
 * author Bashar Khouri
 */
public class BloodType {

    private String bloodType;
    private int bloodID;
    final public static String bloodTypes[] = {"A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-", "Unknown"};

    /**
     * @param bloodID number must be between 0 and 8 else it will throw Blood type not found @<code> String bloodTypes [] ={"A+","A-","B+","B-","O+","O-","AB+","AB-","Unknown"} </code>
     */
    public BloodType(int bloodID) throws IncorrectBloodIDException {
        if (bloodID >= 0 && bloodID < 8) {
            bloodType = bloodTypes[bloodID];
            this.bloodID = bloodID;
        } else {
            throw new IncorrectBloodIDException("Make sure that the Blood ID is between 0 and 8");
        }
    }

    public BloodType() {
        //auto filed from DB
    }

    @Override
    public String toString() {
        return bloodType;
    }

    public static String[] getBloodTypes() {
        return bloodTypes;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public void setBloodID(int bloodID) {
        this.bloodID = bloodID;
    }

    public int getBloodID() {
        return bloodID;
    }

    public String getBloodType() {
        return bloodType;
    }

    public class IncorrectBloodIDException extends Exception {
        public IncorrectBloodIDException(String e) {
            super(e);
        }
    }
}