package com.example.dropoflife.ui.home;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.dropoflife.Classes.Post;
import com.example.dropoflife.R;

import java.util.LinkedList;
/**
 * author Bashar Khouri,Hassan wael ,Bashar Nimri
 */
public class HomeFragment extends Fragment {


    private HomeViewModel homeViewModel;
    private  ListView listView;
    private LinkedList <Post> posts;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // listView = (ListView)getView().findViewById(R.id.HomeListView);
       // MyAdapter adapter = new MyAdapter(Post info)

        
        return inflater.inflate(R.layout.fragment_home,container,false);

    }

    class MyAdapter extends ArrayAdapter<String>{
        Context context ;
        String userName [] ;
        String bloodTypeSTR[];
        String date [];
        String description[];
        // need to add images later once we figure out how to use firebase storage TBD
        MyAdapter(Context c , String userName [], String bloodTypeSTR [],String description []){
            super(c , R.layout.row, R.id.itemUserName,userName);
            this.context = c ;
            this.userName = userName;
            this.bloodTypeSTR = bloodTypeSTR;
            this.date = date ;
            this.description =description;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater =(LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row,parent,false);
            //ImageView imageView = row.findViewById(R.id.item_profile_image);
            TextView userNametxt = row.findViewById(R.id.itemUserName);
            TextView datetxt = row.findViewById(R.id.itemDateOfPublish);
            TextView descriptiontxt = row.findViewById(R.id.itemDescription);
            TextView bloodTypetxt = row.findViewById(R.id.itemBloodType);

          //here we set resources
           // imageView.setImageResources(imgpath)
            userNametxt.setText(userName[position]);
            datetxt.setText(date[position]);
            descriptiontxt.setText(description[position]);
            bloodTypetxt.setText(bloodTypeSTR[position]);


            return row;
        }
    }
}