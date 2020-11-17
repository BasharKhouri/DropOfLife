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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AdapterUsers extends RecyclerView.Adapter<AdapterUsers.UserHolder> {

    Context context;
    List<User> userList;
    List<String> userIDList;
    private FirebaseStorage storage;
    private OnNoteListner mOnNoteListner;

    public AdapterUsers(Context context, List<User> userList, List<String> userIDList, OnNoteListner mOnNoteListner) {
        this.context = context;
        this.userList = userList;
        this.userIDList = userIDList;
        this.mOnNoteListner = mOnNoteListner;
    }


    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.users_card, parent, false);
        return new UserHolder(view, mOnNoteListner);

    }

    @Override
    public void onBindViewHolder(@NonNull final UserHolder holder, int position) {
        final User user = userList.get(position);
        final String userID = userIDList.get(position);
        final String profilePicPath = user.getProfilePic();
        storage = FirebaseStorage.getInstance();
        if(profilePicPath!=null){
           Picasso.get().load(profilePicPath).placeholder(R.drawable.profile).into(holder.profilePicET);

        }
        holder.userNameET.setText(user.getUserName());
        holder.userEmailET.setText(user.getEmail());


    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class UserHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //views from user_card.xml
        ImageView profilePicET;
        TextView userNameET, userEmailET;
        OnNoteListner onNoteListner;

        public UserHolder(@NonNull View itemView, OnNoteListner onNoteListner) {
            super(itemView);
            //init Views
            profilePicET = (ImageView) itemView.findViewById(R.id.userCardProfilePic);
            userEmailET = (TextView) itemView.findViewById(R.id.userCardEmail);
            userNameET = (TextView) itemView.findViewById(R.id.userCardUserName);
            this.onNoteListner = onNoteListner;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onNoteListner.onNoteClick(getAdapterPosition());
        }
    }

    public interface OnNoteListner {
        void onNoteClick(int position);
    }

}
