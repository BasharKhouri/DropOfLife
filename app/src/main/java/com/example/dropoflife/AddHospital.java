package com.example.dropoflife;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dropoflife.Classes.Hospitals;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.core.utilities.Utilities;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

/**
 * @author Bashar
 */
public class AddHospital extends AppCompatActivity {

    private FirebaseStorage storage;
    private StorageReference mStorageRef;
    FirebaseFirestore fStore ;
    Hospitals hospital ;
    EditText hospitalNameET , hospitalPhoneET , hospitalAddressET ,latitude,longitude;
    ImageView logoIV;
    Uri imageUri;
    Button saveHospital;
    String logoStr ;
    GeoPoint location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hospital);
      // init values
        //Edit text
        hospitalNameET = (EditText)findViewById(R.id.HospitalNameET);
        hospitalPhoneET = (EditText)findViewById(R.id.HospitalPhoneNumberET);
        hospitalAddressET =(EditText)findViewById(R.id.building_address);
        latitude = (EditText)findViewById(R.id.latitudeET);
        longitude = (EditText)findViewById(R.id.longitudeET);
        // ImageView
        logoIV=(ImageView)findViewById(R.id.addHospitalLogoImage);
        //Button
        saveHospital = (Button)findViewById(R.id.saveHospital);
        //firebase stuff
        storage = FirebaseStorage.getInstance();
        fStore  = FirebaseFirestore.getInstance();


        saveHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               save(hospitalNameET.getText().toString(),location , hospitalPhoneET.getText().toString(),logoStr,hospitalAddressET.getText().toString());
            }
        });

    }
//todo make a document of hospital in firebase
    private void save(String  name , GeoPoint location,String phoneNumber,String logo ,String address) {
       if(!TextUtils.isEmpty(name)||!TextUtils.isEmpty(phoneNumber)||!TextUtils.isEmpty(logo)||!TextUtils.isEmpty(address)||location!=null)
        {
            hospital = new Hospitals(name, location, phoneNumber, logo, address);
            fStore.collection("Hospitals").document();
        }
    }

//todo add a map view 


    public void selectLogo(View view) {
        choosePic();
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
            logoIV.setImageURI(imageUri);
            uploadImage();
        }
        catch (Exception e ){
            Toast.makeText(this, "upload Failed", Toast.LENGTH_SHORT).show();
        }
    }



    private void uploadImage() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle(R.string.uploading);
        final String uuid = UUID.randomUUID().toString();
       final String imagePath = "images/"+uuid+".jpeg";
        StorageReference riversRef = mStorageRef.child(imagePath);

        riversRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                        logoStr=taskSnapshot.getStorage()+"";
                   //need for later here      fStore.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).set(user);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), R.string.upload_not_successful, Toast.LENGTH_SHORT).show();
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