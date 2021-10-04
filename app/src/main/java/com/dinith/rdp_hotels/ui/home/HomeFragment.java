package com.dinith.rdp_hotels.ui.home;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.dinith.rdp_hotels.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends  Fragment  {

   ImageView profile;
   TextView name;
    private DatabaseReference mDatabaseRef;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        profile = (ImageView)view.findViewById(R.id.profile);
        name= (TextView)view.findViewById(R.id.name);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("username").exists()){
                    name.setText("Hello, "+snapshot.child("username").getValue().toString());
                }else {

                }
                if(snapshot.child("imageUrl").exists()){


                    Picasso.get().load(snapshot.child("imageUrl").getValue().toString()).placeholder(R.mipmap.ic_launcher).fit().centerCrop().into(profile);



                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        return view;
    }

}