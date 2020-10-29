package com.example.dropoflife.Classes;

import java.util.Arrays;

public class BloodType {

    private String bloodType;
    private int bloodID ;
    private String bloodTypes [] ={"A+","A-","B+","B-","C+","C-","AB+","AB-"};
    /**
     *
     * @param bloodID number must be between 0 and 8 else it will through Blood type not found
     */
    BloodType(int bloodID){
        if(bloodID>0&&bloodID<8){
            bloodType = bloodTypes[bloodID];
           this.bloodID=bloodID;
        }else {

        }

    }

    public int getBloodID() {
        return bloodID;
    }

    @Override
    public String toString() {
        return bloodType;
    }

    public class IncorrectBloodIDException extends Exception {
        public IncorrectBloodIDException() {
            super("Make sure that the Blood ID is between 0 and 8 ");
        }
    }

}
