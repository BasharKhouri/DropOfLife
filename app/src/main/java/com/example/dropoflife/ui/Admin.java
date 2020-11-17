package com.example.dropoflife.ui;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.example.dropoflife.AddHospital;
import com.example.dropoflife.ChangeRole;
import com.example.dropoflife.Classes.AdapterUsers;
import com.example.dropoflife.Classes.User;
import com.example.dropoflife.MainActivity;
import com.example.dropoflife.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import io.grpc.internal.JsonUtil;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class Admin extends AppCompatActivity implements AdapterUsers.OnNoteListner {
    AdapterUsers adapterUsers;
        User user;
        RecyclerView userRecyclerView;
        EditText search ;
        List<User> users ;
    FirebaseFirestore fStore;
    List<String> usersID;
    AdapterUsers.OnNoteListner mOnNoteListner ;
    Context context = this;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_admin);

        // Inflate the layout for this fragment
       // View view = inflater.inflate(R.layout.fragment_admin, container, false);
         //init values
        fStore = FirebaseFirestore.getInstance();
         user = MainActivity.user;
         search = findViewById(R.id.searchUser);
         //userRecyclerView and it's property
        userRecyclerView = (RecyclerView)findViewById(R.id.userRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //show the newest users 1st,for this load from last
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        userRecyclerView.setLayoutManager(linearLayoutManager);
        users =new ArrayList<>();
        usersID=new ArrayList<>();
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                LoadUsers(search.getText().toString());
            }
        });
       // to add a custom on click for the recycle view
        mOnNoteListner = this;


    }


    private void LoadUsers(String value){
     users.clear();
     usersID.clear();
      fStore.collection("users").whereGreaterThanOrEqualTo("email",value.toLowerCase()).limit(10).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot tk: task.getResult()) {
                    User user =tk.toObject(User.class);
                    String id = tk.getId();
                    users.add(user);
                    usersID.add(id);
                }
                adapterUsers = new  AdapterUsers(context,users,usersID,mOnNoteListner);
                userRecyclerView.setAdapter(adapterUsers);
            }
        });

    }
    //form the custom onclick
    @Override
    public void onNoteClick(int position) {
        Intent intent = new Intent(this, ChangeRole.class);
        intent.putExtra("user",users.get(position));
        intent.putExtra("UID",users.get(position).getUserID());
        startActivity(intent);
    }

    public void goToAddHospitalView(View view) {
        Intent intent = new Intent(context,AddHospital.class);
        startActivity(intent);
    }
}