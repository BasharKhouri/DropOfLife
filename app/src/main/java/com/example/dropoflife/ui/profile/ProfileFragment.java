package com.example.dropoflife.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.dropoflife.Classes.User;
import com.example.dropoflife.MainActivity;
import com.example.dropoflife.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ProfileFragment extends Fragment {
    User user;
    private ProfileViewModel profileViewModel;
    private StorageReference mStorageRef;
    TextView userName;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,container,false);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        user = MainActivity.user;
        userName = (TextView) view.findViewById(R.id.profile_page_userName);
        userName.setText(user.getUserName());
        return view ;
    }


}