package com.example.dropoflife.Classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dropoflife.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author Bashar Khouri
 *
 */
public class AdapterPosts extends  RecyclerView.Adapter<AdapterPosts.MyHolder>{
    Context context;
    List<Post> postList;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Users");

    public AdapterPosts(Context context, List<Post> postList) {
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
        String blood =postList.get(position).getBloodType().getBloodType();
        String userPic;
        //convert time stamps to dd/mm/yyyy hh:mm am/pm
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(Long.parseLong(time));
        String postTime = (String) android.text.format.DateFormat.format("dd/mm/yyyy hh:mm aa",calendar);

        //set Data
        final User[] user = new User[1];

        myRef.orderByKey().equalTo(userID).limitToFirst(1).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                user[0] = snapshot.getValue(User.class);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
         //User user = new User( myRef.orderByChild("fireBaseAuthID").equalTo(userID).limitToFirst(1));
        holder.userName.setText(user[0].getFullName());
        holder.blood.setText(blood);
        holder.description.setText(description);
        holder.location.setText(location);


        try {
           userPic= user[0].getUserPhotoURL();
            //set profile pic once  Storage  is configerd
        }catch (Exception e){

        }
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        ImageView uPic ;
        TextView userName , time  , description , blood , location;
        Button callMe , chat , shareButton;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            uPic = (ImageView) itemView.findViewById(R.id.item_profile_image);
            userName = (TextView)itemView.findViewById(R.id.itemUserName);
            time = (TextView)itemView.findViewById(R.id.itemDateOfPublish);
            description= (TextView)itemView.findViewById(R.id.itemDescription);
            blood= (TextView)itemView.findViewById(R.id.itemBloodType);
            location= (TextView)itemView.findViewById(R.id.item_location);
            callMe = (Button) itemView.findViewById(R.id.item_Call_me);
            chat= (Button) itemView.findViewById(R.id.item_Chat);
            shareButton= (Button) itemView.findViewById(R.id.item_Share);
         }



    }
}
