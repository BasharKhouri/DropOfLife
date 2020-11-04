package com.example.dropoflife.ui.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentBreadCrumbs;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dropoflife.AddRequest;
import com.example.dropoflife.Classes.AdapterPosts;
import com.example.dropoflife.Classes.BloodType;
import com.example.dropoflife.Classes.Post;
import com.example.dropoflife.Classes.User;
import com.example.dropoflife.MainActivity;
import com.example.dropoflife.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

public class HomeFragment extends Fragment {

    final User user = MainActivity.user;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference postRef ;
    private HomeViewModel homeViewModel;
    private ArrayList<Post> postList;
    RecyclerView recyclerView ;
    AdapterPosts adapterPosts;
    FirebaseUser currentUser;
    Button reqBlood;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


         currentUser=FirebaseAuth.getInstance().getCurrentUser();
         View view = inflater.inflate(R.layout.fragment_home,container,false);
         // Recycler View and it's properties
         LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView = view.findViewById(R.id.PostsListRecycleView);
        //show the newest post 1st,for this load from last
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        //init post List
        postList = new ArrayList<>();
        LoadPosts();

        reqBlood = (Button)view.findViewById(R.id.req_bloodHomeButton);
        reqBlood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),AddRequest.class);
                startActivity(intent);
            }
        });

        return view;


    }

    private void LoadPosts() {
        // Path Of all Posts
        postRef= FirebaseDatabase.getInstance().getReference("Posts");
        //get all data from the postRef
        postRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postList.clear();
                for(DataSnapshot ds:snapshot.getChildren()){
                    Post post = ds.getValue(Post.class);//Here is 105
                    postList.add(post);
                    adapterPosts = new AdapterPosts(getActivity(),postList);
                    recyclerView.setAdapter(adapterPosts);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }




}
