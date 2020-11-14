package com.example.dropoflife.Classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dropoflife.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AdapterUsers extends  RecyclerView.Adapter<AdapterUsers.UserHolder> {

    Context context;
    List<User> userList;
    List<String> userIDList;
    private FirebaseStorage storage ;

    public AdapterUsers(Context context, List<Post> postList , List<String> userIDList) {
        this.context = context;
        this.userList = userList;
        this.userIDList=userIDList;
    }


    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.users_card,parent,false);
        return new UserHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final UserHolder holder, int position) {
        final User user = userList.get(position);
        final String userID=userIDList.get(position);
        final String profilePicPath = user.getProfilePic();
        storage = FirebaseStorage.getInstance();
        if(profilePicPath!=null){
            StorageReference riversRef = storage.getReferenceFromUrl(user.getProfilePic());

            try {
                final File localFile = File.createTempFile("images", "jpg");
                 riversRef.getFile(localFile)
                        .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                // Successfully downloaded data to local file
                                // ...
                                Picasso.get().load(localFile).placeholder(R.drawable.profile).into(holder.profilePicET);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle failed download
                        // ...
                    }
                });

            } catch (Exception e) {

            }
        }
        holder.userNameET.setText(user.getUserName());
        holder.userEmailET.setText(user.getEmail());


    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class UserHolder extends RecyclerView.ViewHolder{
        //views from user_card.xml
        ImageView profilePicET;
        TextView userNameET, userEmailET;


        public UserHolder(@NonNull View itemView) {
            super(itemView);
            //init Views
            profilePicET = (ImageView)itemView.findViewById(R.id.userCardProfilePic);
            userEmailET=(TextView)itemView.findViewById(R.id.userCardEmail) ;
            userNameET=(TextView)itemView.findViewById(R.id.userCardUserName);

        }
}}
