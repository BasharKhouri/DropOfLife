package com.example.dropoflife.Classes;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.dropoflife.ui.home.HomeFragment;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

/**
 * @author Bashar
 * this calss is to optimize the post list so we dont call the documents every time the usere move between fragments
 * wich in result will be faster and more efficient, and will drive the cost down.
 */
public class SingletonPost {
   private static SingletonPost singletonPost = null;
    private   ArrayList<Post>postArrayList ;

    DatabaseReference postRef ;

    static  boolean   firstThread  = true;
    //constructor
    private SingletonPost(){
        postArrayList = new ArrayList<>();
    }
   public static SingletonPost getInstance(){
        //in case of concurrent Threads
       if(singletonPost==null){
           if(firstThread){
               Thread.currentThread();
               try {
                   firstThread=false;
                   Thread.sleep(20);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
           singletonPost =  new SingletonPost();
       }
       return singletonPost;
   }

   public  ArrayList<Post> LoadPosts(){

       // Path Of all Posts
       postRef= FirebaseDatabase.getInstance().getReference("Posts");
        //get first 20 post
       postRef.limitToFirst(20).addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               postArrayList.clear();
               for (DataSnapshot ds :snapshot.getChildren()) {
                  Post post = ds.getValue(Post.class);
                  postArrayList.add(post);
               }

           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {
               Log.w("Error",error.getMessage());
           }
       });



        return postArrayList;
   }







}
