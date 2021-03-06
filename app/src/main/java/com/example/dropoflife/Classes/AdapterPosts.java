package com.example.dropoflife.Classes;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dropoflife.MainActivity;
import com.example.dropoflife.R;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;

/**
 * @author Bashar Khouri
 */
public class AdapterPosts extends RecyclerView.Adapter<AdapterPosts.MyHolder> {
    Context context;
    List<Post> postList;
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    Uri userPic;
    ArrayList postIDList;
    DatabaseReference myRef = database.getReference("Posts");
    private FirebaseStorage storage;
    Hospitals hospitals;
    String phoneNumber;
    GeoPoint location;


    /**
     * @param context
     * @param postList
     */
    public AdapterPosts(Context context, List<Post> postList) {
        this.context = context;
        this.postList = postList;
    }

    /**
     * @param parent
     * @param viewType
     * @return return row (post holder )
     */
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout
        View view = LayoutInflater.from(context).inflate(R.layout.row, parent, false);
        return new MyHolder(view);
    }



    /**
     * @param holder   the raw that has been created
     * @param position the Index form the postList
     */
    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {
        //get data
        final Post post = postList.get(position);
        final String hospitalID = postList.get(position).getHospitalID();
        final String description = postList.get(position).getDescription();
        final Date time = postList.get(position).getDateOfPublish();
        final String blood = BloodType.bloodTypes[postList.get(position).getBloodTypeID()];
        final String postTime = (String) android.text.format.DateFormat.format("MMM dd yyyy", time);
        final String postID = postList.get(position).getPostID();

        //get hospital
        fStore.collection("Hospitals").document(hospitalID).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                hospitals = value.toObject(Hospitals.class);
                final String address = hospitals.getAddress();
                holder.location.setText(address);
                phoneNumber = hospitals.getPhoneNumber();
                location = hospitals.getLocation();
                holder.userName.setText(hospitals.getName());

                if (hospitals.getLogo() != null) {
                    Picasso.get().load(hospitals.getLogo()).placeholder(R.drawable.profile).into(holder.uPic);

                }
            }
        });

        storage = FirebaseStorage.getInstance();

        //if the user has a profile pic

        holder.blood.setText(blood);
        holder.description.setText(description);
        holder.time.setText(postTime);
        //Program each raw buttons function down here

        holder.moreOption.setOnClickListener(new View.OnClickListener() {
            /**
             * it gives a drop down list where it gives the user 2 options.
             * option 1 to report the post to the developers in that case the post will be submitted to the review database.
             * option 2 if the user is the owner of this post he/she can delete it.
             *
             * @param v the current view that we are in
             */
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                moreOption(holder.moreOption, FirebaseAuth.getInstance().getCurrentUser().getUid(), hospitals.getName(), postID);
            }
        });

        holder.callMe.setOnClickListener(new View.OnClickListener() {
            /**
             * it send the phone number that is associated with the post to the phone dialer
             *
             * @param v the current view that we are in
             */
            @Override
            public void onClick(View v) {
                try {
                    Uri uri = Uri.parse("tel:" + phoneNumber);
                    Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                    context.startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(context, "you need to have whatsapp ", Toast.LENGTH_SHORT).show();
                }
            }

        });
                /*
                TODO Hassan you said that this one is on you to do so I'll leave it be don't forget to add comments tho
                 */
        holder.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        holder.locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri hospitalGeoPoint = Uri.parse("google.navigation:q=" + location.getLatitude() + "," + location.getLongitude());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, hospitalGeoPoint);
                mapIntent.setPackage("com.google.android.apps.maps");
                context.startActivity(mapIntent);
            }
        });
    }





    /**
     * @param moreOption the Image that has the on Click listener
     * @param uid        the Current user
     * @param userID     the owner of this post
     * @param postID     the Id that the post has
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void moreOption(ImageView moreOption, final String uid, String userID, final String postID) {
        //first we will create the pop up menu
        PopupMenu popupMenu = new PopupMenu(context, moreOption, Gravity.END);
        //add items in menu
        if (uid.equals(userID)) {
            popupMenu.getMenu().add(Menu.NONE, 0, 0, "Delete");
        } else {
            popupMenu.getMenu().add(Menu.NONE, 1, 1, "Report");
        }
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == 0) {
                    delete(postID);
                } else {
                    report(postID, uid);
                }
                return false;
            }
        });

        popupMenu.show();
    }

    /**
     * @param postID it sends the post ID to the database for the developers to check out the post if it is insulting the post will be removed and the account weill be disabled.
     */
    private void report(String postID, String currnetUser) {
        DatabaseReference reportRef = database.getReference("Report");
        HashMap<String, String> PostMap = new HashMap<>();
        reportRef.child("PostID: " + postID).child("Reporter: " + currnetUser).setValue(new Date());

    }

    /**
     * @param postID the  child ID in posts inside the realtime firebase database
     */
    private void delete(String postID) {
        //Todo if the take a long time am thinking to add a progress bar come bake later
        myRef.child(postID).removeValue();
    }


    @Override
    public int getItemCount() {
        return postList.size();
    }

    /**
     * in here we set the values for the raw Holder
     */

    //View Holder Class
    class MyHolder extends RecyclerView.ViewHolder {
        //views from row.xml
        ImageButton moreOption;
        ImageView uPic;
        TextView userName, time, description, blood, location;
        Button callMe, locationButton, shareButton;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            //init Views
            uPic = (ImageView) itemView.findViewById(R.id.item_profile_image);
            userName = (TextView) itemView.findViewById(R.id.itemUserName);
            time = (TextView) itemView.findViewById(R.id.itemDateOfPublish);
            description = (TextView) itemView.findViewById(R.id.itemDescription);
            blood = (TextView) itemView.findViewById(R.id.itemBloodType);
            location = (TextView) itemView.findViewById(R.id.location);
            callMe = (Button) itemView.findViewById(R.id.item_call_me);
            locationButton = (Button) itemView.findViewById(R.id.item_location);
            shareButton = (Button) itemView.findViewById(R.id.item_share);
            moreOption = (ImageButton) itemView.findViewById(R.id.moreOption);
        }
    }
}