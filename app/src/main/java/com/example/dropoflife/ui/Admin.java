package com.example.dropoflife.ui;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.example.dropoflife.AddHospital;
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

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class Admin extends Fragment {
    AdapterUsers adapter;
        User user;
        Button AddAHospital;
        RecyclerView userRecyclerView;
        EditText search ;
        List<User> users ;
    FirebaseFirestore fStore;
    List<String> usersID;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin, container, false);
         //init values
        fStore = FirebaseFirestore.getInstance();
        AddAHospital=(Button)view.findViewById(R.id.goToAddHospitalButton);
         user = MainActivity.user;
         search = view.findViewById(R.id.searchUser);
         //userRecyclerView and it's property
        userRecyclerView = (RecyclerView)view.findViewById(R.id.userRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
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


        AddAHospital.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(getContext(), AddHospital.class);
                 startActivity(intent);
             }
         });
        return  view;
    }


    private void LoadUsers(String value){
     users.clear();
     usersID.clear();
      fStore.collection("users").whereEqualTo("email",value).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot tk: task.getResult()) {
                    User user =tk.toObject(User.class);
                    String id = tk.getId();
                    users.add(user);
                    usersID.add(id);
                }


            }
        });

    }
}