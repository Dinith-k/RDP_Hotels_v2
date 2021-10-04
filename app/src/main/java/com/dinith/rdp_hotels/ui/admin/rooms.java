package com.dinith.rdp_hotels.ui.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.dinith.rdp_hotels.R;
import com.dinith.rdp_hotels.ui.home.ImageAdapter;
import com.dinith.rdp_hotels.ui.home.Upload;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class rooms extends AppCompatActivity {
RecyclerView recyclerview;
    private room_adapter mAdapter;
    private List<room_list> mUploads;
    private DatabaseReference mDatabaseRef;
    private ValueEventListener mDBListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);


        mUploads = new ArrayList<>();
        mAdapter = new room_adapter(rooms.this, mUploads);
        recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(mAdapter);
        recyclerview.setLayoutManager(new GridLayoutManager(this, 1));

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mUploads.clear();


                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    room_list upload = postSnapshot.getValue(room_list.class);
                    upload.setkey(postSnapshot.getKey());
                    mUploads.add(upload);
                }
                mAdapter.notifyDataSetChanged();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(rooms.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}