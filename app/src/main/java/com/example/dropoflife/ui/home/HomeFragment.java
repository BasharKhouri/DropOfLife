package com.example.dropoflife.ui.home;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dropoflife.AddRequest;
import com.example.dropoflife.Classes.AdapterPosts;
import com.example.dropoflife.Classes.Post;
import com.example.dropoflife.Classes.SingletonPost;
import com.example.dropoflife.Interface.IObserver;
import com.example.dropoflife.Interface.ISubject;
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

public class HomeFragment extends Fragment implements IObserver {
    private ISubject subject;
    FirebaseUser currentUser;
    Button reqBlood;
    Button share;
//    hospital   bloodtype description date

    ProgressBar bar;
    //Posts var
    RecyclerView recyclerView ;
    private ArrayList<Post> postList;
    AdapterPosts adapterPosts;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //inflate the layout of this fragment
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        bar=(ProgressBar)view.findViewById(R.id.loadingBar);
        bar.setVisibility(View.VISIBLE);
        //init current user
        currentUser= FirebaseAuth.getInstance().getCurrentUser();
         // Recycler View and it's properties
        recyclerView = view.findViewById(R.id.postsRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
       //init subject / singleton
        subject = SingletonPost.getInstance();
        //show the newest post 1st,for this load from last
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        //set layout manger for the recycle view
        recyclerView.setLayoutManager(linearLayoutManager);
        //init post List
        postList = new ArrayList<>();
        //register this with the subject
        subject.register(this);
      //  LoadPosts();
        reqBlood = (Button)view.findViewById(R.id.req_bloodHomeButton);
        share = (Button)view.findViewById(R.id.item_share);
        reqBlood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),AddRequest.class);
                startActivity(intent);
            }
        });
       

        return view;
    }
    ArrayList<String> postID = new ArrayList<>();
    private Task  LoadPosts() {

        adapterPosts = new AdapterPosts(getActivity(),SingletonPost.getPostArrayList());
        recyclerView.setAdapter(adapterPosts);
        bar.setVisibility(View.GONE);
        return null;
    }

    @Override
    public void update() {
     LoadPosts();
    }
}
