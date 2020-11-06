package com.example.dropoflife.ui.profile;

import android.net.Uri;

import android.os.Build;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.dropoflife.Classes.User;
import com.example.dropoflife.MainActivity;
import com.example.dropoflife.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class ProfileFragment extends Fragment {
    User user;
    ProgressBar progressBar ;
    ImageView userImage;
    TextView bloodTypeDisplay, numberOfDonations,userName,remainingDaysUntilNextDonation;
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    Button donationButton;
    private StorageReference mStorageRef;


    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceSyotate) {
       View view = inflater.inflate(R.layout.fragment_profile,container,false);
       mStorageRef = FirebaseStorage.getInstance().getReference();
       user = MainActivity.user;
       //init values
        userName = (TextView) view.findViewById(R.id.profile_page_userName);
        userImage =(ImageView)view.findViewById(R.id.profile_image);
        remainingDaysUntilNextDonation=(TextView)view.findViewById(R.id.Profile_NoOfDaysRemaining);
        progressBar =  (ProgressBar)view.findViewById(R.id.progress_circular);
        bloodTypeDisplay =(TextView)view.findViewById(R.id.profile_page_usesBlood);
        donationButton=(Button)view.findViewById(R.id.donationButton);
        numberOfDonations =(TextView)view.findViewById(R.id.profile_page_NoOfDonation);

        mStorageRef = FirebaseStorage.getInstance().getReference();
       //set values
       userName.setText(user.getUserName());

       try {//if the image is null it wont be changed form the default image
           userImage.setImageURI(Uri.parse(user.getProfilePic()));
       }catch (Exception e){

           System.out.println(e.getMessage());
       }
       if(user.getDateOfLastDonation()==null){
           progressBar.setProgress(100);
           remainingDaysUntilNextDonation.setText(R.string.you_can_donate);

       }else{
           Long noOfDays = TimeUnit.DAYS.convert( new Date().getTime()-user.getDateOfLastDonation().getTime(), TimeUnit.MILLISECONDS);
           if(noOfDays<75){
                remainingDaysUntilNextDonation.setText(getString(R.string.days_remaining)+ " "+(75-noOfDays));
               progressBar.setProgress((int) (100*noOfDays/75));
               donationButton.setEnabled(false);

           }else{
               remainingDaysUntilNextDonation.setText(R.string.you_can_donate);
               progressBar.setProgress (100);
           }
       }

       bloodTypeDisplay.setText(user.getBloodType().toString());
        try {
            numberOfDonations.setText(user.getNumberOfDonations()+"");
        }catch (Exception e){
            numberOfDonations.setText("0");
        }
        donationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setNumberOfDonations(user.getNumberOfDonations()+1);
                user.setDateOfLastDonation(new Date());
                progressBar.setProgress(0);
                remainingDaysUntilNextDonation.setText(getString(R.string.days_remaining)+" "+ 75);
                progressBar.setProgress((int) (0));
                numberOfDonations.setText(user.getNumberOfDonations()+" ");
                donationButton.setEnabled(false);
                fStore.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).set(user);
            }
        });




        return view ;
    }


}