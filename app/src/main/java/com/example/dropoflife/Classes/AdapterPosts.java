package com.example.dropoflife.Classes;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dropoflife.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.Executor;

/**
 * @author Bashar Khouri
 *
 */
public class AdapterPosts extends  RecyclerView.Adapter<AdapterPosts.MyHolder>{
    Context context;
    List<Post> postList;
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    User user;
    public AdapterPosts(Context context, List<Post> postList) {
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      //inflate layout
       View view= LayoutInflater.from(context).inflate(R.layout.row,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        //get data
        String userID =postList.get(position).getUserID();
        String description =postList.get(position).getDescription();
        String location =postList.get(position).getLocation();
        String time =postList.get(position).getDateOfPublish().toString();
        String blood = BloodType.bloodTypes[postList.get(position).getBloodTypeID()];
        Uri userPic;
        //convert time stamps to dd/mm/yyyy hh:mm am/pm
      //  Calendar calendar = Calendar.getInstance(Locale.getDefault());
        //calendar.setTimeInMillis(Long.parseLong(time));
        //String postTime = (String) android.text.format.DateFormat.format("dd/mm/yyyy hh:mm aa",calendar);

        //set Data


        DocumentReference documentReference = fStore.collection("users").document(userID);
       ApiFuture<DocumentSnapshot> task = documentReference.get();
       // DocumentSnapshot documentSnapshot = .get();
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {

            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
               if(value.exists())
                user = value.toObject(User.class);
            }
        });

    try {
        userPic = Uri.parse(user.getProfilePic());
        holder.userName.setText(user.getUserName());

    }catch (Exception e){
        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        System.out.println(e.getMessage());

    }
        holder.blood.setText(blood);
        holder.description.setText(description);
        holder.location.setText(location);
       // holder.uPic.setImageURI(userPic);

    }



    @Override
    public int getItemCount() {
        return postList.size();
    }


    //View Holder Class
    class MyHolder extends RecyclerView.ViewHolder{
        //views from row.xml
        ImageView uPic ;
        TextView userName , time  , description , blood , location;
        Button callMe , chat , shareButton;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
          //init Views
            uPic = (ImageView) itemView.findViewById(R.id.item_profile_image);
            userName = (TextView)itemView.findViewById(R.id.itemUserName);
            time = (TextView)itemView.findViewById(R.id.itemDateOfPublish);
            description= (TextView)itemView.findViewById(R.id.itemDescription);
            blood= (TextView)itemView.findViewById(R.id.itemBloodType);
            location= (TextView)itemView.findViewById(R.id.location);
            callMe = (Button) itemView.findViewById(R.id.item_call_me);
            chat= (Button) itemView.findViewById(R.id.item_chat);
            shareButton= (Button) itemView.findViewById(R.id.item_share);
         }



    }
}
