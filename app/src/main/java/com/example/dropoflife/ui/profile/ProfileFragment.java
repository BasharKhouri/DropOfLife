package com.example.dropoflife.ui.profile;

import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.dropoflife.Classes.User;
import com.example.dropoflife.MainActivity;
import com.example.dropoflife.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {
    User user;
    ProgressBar progressBar ;
    ImageView userImage;
    TextView bloodTypeDisplay, numberOfDonations,userName,remainingDaysUntilNextDonation;
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    Button donationButton;
    Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference mStorageRef;
    File localFile ;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceSyotate) {
       View view = inflater.inflate(R.layout.fragment_profile,container,false);

       //init values
        userName = (TextView) view.findViewById(R.id.profile_page_userName);
        userImage =(ImageView)view.findViewById(R.id.profile_image);
        remainingDaysUntilNextDonation=(TextView)view.findViewById(R.id.Profile_NoOfDaysRemaining);
        progressBar =  (ProgressBar)view.findViewById(R.id.progress_circular);
        bloodTypeDisplay =(TextView)view.findViewById(R.id.profile_page_usesBlood);
        donationButton=(Button)view.findViewById(R.id.donationButton);
        numberOfDonations =(TextView)view.findViewById(R.id.profile_page_NoOfDonation);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        user = MainActivity.user;
        storage = FirebaseStorage.getInstance();



        userName.setText(user.getUserName());
        //set values
        //if the user has a profile pic
        if(user.getProfilePic()!=null) {
            try {
                Picasso.get().load(MainActivity.localFile).placeholder(R.drawable.profile).into(userImage);
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
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

        try {
            bloodTypeDisplay.setText(user.getBloodType().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
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

        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePic();
            }
        });




        return view ;
    }

    private void choosePic() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
      try {


            imageUri =data.getData();
            userImage.setImageURI(imageUri);
            uploadImage();
        }
      catch (Exception e ){
          Toast.makeText(getContext(), R.string.Fail_in_select_img, Toast.LENGTH_SHORT).show();

      }
    }

    private void uploadImage() {
        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setTitle(R.string.uploading);

        final String imagePath = "images/"+MainActivity.firebaseUser.getUid()+".jpeg";
                StorageReference riversRef = mStorageRef.child(imagePath);

        riversRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                        user.setProfilePic( taskSnapshot.getStorage()+"");
                        fStore.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).set(user);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        pd.dismiss();
                        Toast.makeText(getContext(), R.string.upload_not_successful, Toast.LENGTH_SHORT).show();
                        // Handle unsuccessful uploads
                        // ...

                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progressPercent =(100*snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                ;
                pd.setMessage((int)progressPercent+" ");
            }
        });
    }
}
