package com.example.dropoflife.Interface;

import com.example.dropoflife.Classes.Hospitals;

import java.util.List;

public interface IFirebaseHospitalLoad {
    void onLoadSuccess(List<Hospitals> hospitalsList);
    void onLoadFail(String message);



}
