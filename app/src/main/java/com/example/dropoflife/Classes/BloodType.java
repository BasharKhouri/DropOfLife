package com.example.dropoflife.Classes;

import java.util.Arrays;

/**
 * author Bashar Khouri
 */
public class BloodType {

    private String bloodType;
    private int bloodID ;
   final public static String bloodTypes [] ={"A+","A-","B+","B-","C+","C-","AB+","AB-","Unknown"};
    /**
     *
     * @param bloodID number must be between 0 and 8 else it will through Blood type not found @<code> String bloodTypes [] ={"A+","A-","B+","B-","C+","C-","AB+","AB-","Unknown"} </code>
     */
    public BloodType(int bloodID) throws IncorrectBloodIDException {
        if(bloodID>=0&&bloodID<=8){
            bloodType = bloodTypes[bloodID];
           this.bloodID=bloodID;
        }else {
            throw  new IncorrectBloodIDException("Make sure that the Blood ID is between 0 and 9");
        }
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
