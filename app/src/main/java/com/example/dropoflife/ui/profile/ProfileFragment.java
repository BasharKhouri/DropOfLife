package com.example.dropoflife.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.dropoflife.Classes.User;
import com.example.dropoflife.MainActivity;
import com.example.dropoflife.R;
/**
 * author Bashar Khouri,Hassan wael ,Bashar Nimri
 */
public class ProfileFragment extends Fragment {
    User user;
    private ProfileViewModel profileViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        user =MainActivity.user;
        return inflater.inflate(R.layout.fragment_profile,container,false);
    }


}