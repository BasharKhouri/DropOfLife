package com.example.dropoflife.ui.home;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dropoflife.Classes.AdapterPosts;
import com.example.dropoflife.Classes.Post;
import com.example.dropoflife.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedList;

public class HomeFragment extends Fragment {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference postRef ;
    private HomeViewModel homeViewModel;
    private ArrayList<Post> postList;
    RecyclerView recyclerView ;
    AdapterPosts adapterPosts;
    FirebaseAuth currentUser;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


         currentUser=FirebaseAuth.getInstance();

       // Recycler View and it's properties
        recyclerView = (RecyclerView) getView().findViewById(R.id.postRecycleView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        //show the newest post 1st,for this load from last
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        //init post List
        postList = new ArrayList<>();
        LoadPosts();


        return inflater.inflate(R.layout.fragment_home,container,false);


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
                    Post post = ds.getValue(Post.class);
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
