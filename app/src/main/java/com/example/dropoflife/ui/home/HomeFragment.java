package com.example.dropoflife.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dropoflife.AddRequest;
import com.example.dropoflife.Classes.AdapterPosts;
import com.example.dropoflife.Classes.BloodType;
import com.example.dropoflife.Classes.Post;
import com.example.dropoflife.Classes.SingletonPost;
import com.example.dropoflife.Interface.IObserver;
import com.example.dropoflife.Interface.ISubject;
import com.example.dropoflife.Classes.User;
import com.example.dropoflife.MainActivity;
import com.example.dropoflife.R;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HomeFragment extends Fragment implements IObserver {
    private ISubject subject;
    SingletonPost sing;
    FirebaseUser currentUser;
    User user;
    Button reqBlood;
    Button share;
//    hospital   bloodtype description date

    ProgressBar bar;
    //Posts var
    RecyclerView recyclerView;
    private ArrayList<Post> postList;
    AdapterPosts adapterPosts;
    ArrayAdapter filterAdapter;
    Spinner filter;
    String[] listFilter = new String[]{"Filter", " By Blood type", " By Location"};
    ArrayList<Post> posts = new ArrayList<Post>();
    ArrayList<Post> filteredPosts=new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //inflate the layout of this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        bar = (ProgressBar) view.findViewById(R.id.loadingBar);
        bar.setVisibility(View.VISIBLE);
        //init current user
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        // Recycler View and it's properties
        recyclerView = view.findViewById(R.id.postsRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        //init subject / singleton
        subject = SingletonPost.getInstance();
        sing=SingletonPost.getInstance();
        //show the newest post 1st,for this load from last
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        //set layout manger for the recycle view
        recyclerView.setLayoutManager(linearLayoutManager);
        //init post List
        postList = new ArrayList<>();
        //register this with the subject
        subject.register(this);
        LoadPosts();
        reqBlood = (Button) view.findViewById(R.id.req_bloodHomeButton);
        share = (Button) view.findViewById(R.id.item_share);
        filter = (Spinner) view.findViewById(R.id.spinnerFilter);

        filterAdapter = new ArrayAdapter(getActivity(), R.layout.support_simple_spinner_dropdown_item, listFilter);
        filter.setAdapter(filterAdapter);
        posts = SingletonPost.getPostArrayList();
        filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1:

                        try {
                            if (MainActivity.user.getBloodTypeString().equalsIgnoreCase("A+")) {
                                filteredPosts = (ArrayList<Post>) posts.stream().filter(p ->
                                                BloodType.bloodTypes[p.getBloodTypeID()].equals(MainActivity.user.getBloodTypeString()) ||
                                                BloodType.bloodTypes[p.getBloodTypeID()].equals("AB+"))
                                        .collect(Collectors.toList());
                            }else   if (MainActivity.user.getBloodTypeString().equalsIgnoreCase("A-")) {
                                filteredPosts = (ArrayList<Post>) posts.stream().filter(p ->
                                                BloodType.bloodTypes[p.getBloodTypeID()].equals(MainActivity.user.getBloodTypeString()) ||
                                                BloodType.bloodTypes[p.getBloodTypeID()].equals("AB+") ||
                                                BloodType.bloodTypes[p.getBloodTypeID()].equals("AB-")||
                                                BloodType.bloodTypes[p.getBloodTypeID()].equals("A+")
                                ).collect(Collectors.toList());
                            }else  if (MainActivity.user.getBloodTypeString().equalsIgnoreCase("B+")) {
                                filteredPosts = (ArrayList<Post>) posts.stream().filter(p ->
                                                BloodType.bloodTypes[p.getBloodTypeID()].equals(MainActivity.user.getBloodTypeString()) ||
                                                BloodType.bloodTypes[p.getBloodTypeID()].equals("AB+"))
                                        .collect(Collectors.toList());
                            }else if (MainActivity.user.getBloodTypeString().equalsIgnoreCase("B-")) {
                                filteredPosts = (ArrayList<Post>) posts.stream().filter(p ->
                                                BloodType.bloodTypes[p.getBloodTypeID()].equals(MainActivity.user.getBloodTypeString()) ||
                                                BloodType.bloodTypes[p.getBloodTypeID()].equals("AB+") ||
                                                BloodType.bloodTypes[p.getBloodTypeID()].equals("AB-")||
                                                BloodType.bloodTypes[p.getBloodTypeID()].equals("B+")
                                ).collect(Collectors.toList());
                            }else if (MainActivity.user.getBloodTypeString().equalsIgnoreCase("O+")) {
                                filteredPosts = (ArrayList<Post>) posts.stream().filter(p ->
                                                BloodType.bloodTypes[p.getBloodTypeID()].equals(MainActivity.user.getBloodTypeString()) ||
                                                BloodType.bloodTypes[p.getBloodTypeID()].equals("A+") ||
                                                BloodType.bloodTypes[p.getBloodTypeID()].equals("B+") ||
                                                BloodType.bloodTypes[p.getBloodTypeID()].equals("AB+")
                                ).collect(Collectors.toList());
                            }else if (MainActivity.user.getBloodTypeString().equalsIgnoreCase("AB-")) {
                                filteredPosts = (ArrayList<Post>) posts.stream().filter(p ->
                                                BloodType.bloodTypes[p.getBloodTypeID()].equals(MainActivity.user.getBloodTypeString()) ||
                                                BloodType.bloodTypes[p.getBloodTypeID()].equals("AB+")
                                ).collect(Collectors.toList());
                            }else if (MainActivity.user.getBloodTypeString().equalsIgnoreCase("AB+")) {
                                filteredPosts = (ArrayList<Post>) posts.stream().filter(p ->
                                        BloodType.bloodTypes[p.getBloodTypeID()].equals(MainActivity.user.getBloodTypeString())
                                ).collect(Collectors.toList());
                            }
                                    sing.setPostArrayList(filteredPosts);
                                    update();
                            Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        break;
                    default:

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final String searchFilter;
        reqBlood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddRequest.class);
                startActivity(intent);
            }
        });


        return view;
    }

    ArrayList<String> postID = new ArrayList<>();

    private Task LoadPosts() {

        adapterPosts = new AdapterPosts(getActivity(), SingletonPost.getPostArrayList());
        recyclerView.setAdapter(adapterPosts);
        bar.setVisibility(View.GONE);
        return null;
    }

    @Override
    public void update() {
        LoadPosts();
    }
}
