package com.dinith.rdp_hotels.ui.menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
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
//Menu database creation
public class menu extends AppCompatActivity {

    private RecyclerView food_view;
    private foodAdapter f_Adapter;
    private DatabaseReference f_DatabaseRef;
    private ValueEventListener f_DBListener;
    private List<Upload> f_Uploads;
    ProgressBar progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        f_Uploads = new ArrayList<>();
        f_Adapter = new foodAdapter(menu.this, f_Uploads);
        progress = (ProgressBar)findViewById(R.id.progress2);
        food_view = findViewById(R.id.food_view);
        food_view.setHasFixedSize(true);
        food_view.setLayoutManager(new LinearLayoutManager(menu.this));
        food_view.setAdapter(f_Adapter);
        food_view.setLayoutManager(new GridLayoutManager(menu.this, 1));


        f_DatabaseRef = FirebaseDatabase.getInstance().getReference("food"); //conect Firebase
        f_DBListener = f_DatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                f_Uploads.clear();


                for (DataSnapshot foods : dataSnapshot.getChildren()) {
                    Upload upload = foods.getValue(Upload.class);
                    upload.setKey(foods.getKey());
                    f_Uploads.add(upload);
                }
                f_Adapter.notifyDataSetChanged();
                progress.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(menu.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                progress.setVisibility(View.INVISIBLE);
            }
        });



    }
}