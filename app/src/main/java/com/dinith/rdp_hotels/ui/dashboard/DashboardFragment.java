package com.dinith.rdp_hotels.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class DashboardFragment extends Fragment implements ImageAdapter.OnItemClickListener{
    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;
    private DatabaseReference mDatabaseRef;
    private ValueEventListener mDBListener;
    private List<Upload> mUploads;
    ProgressBar progress;
    ImageButton wifi,gym,food,tranceport;
int Wifi = 0 , Gym  = 0 , Food = 0, Tranceport = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);




        wifi = (ImageButton)view.findViewById(R.id.wifi) ;
        gym = (ImageButton)view.findViewById(R.id.gym) ;
        food = (ImageButton)view.findViewById(R.id.food) ;
        tranceport = (ImageButton)view.findViewById(R.id.tranceport) ;
        mUploads = new ArrayList<>();
        mAdapter = new ImageAdapter(getActivity(), mUploads);
progress = (ProgressBar)view.findViewById(R.id.progress);
        // List<Upload> mAdapter = new ArrayList<>();
        mRecyclerView = view.findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        // mUploads = new ArrayList<>();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mUploads.clear();


                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Upload upload = postSnapshot.getValue(Upload.class);
                    upload.setKey(postSnapshot.getKey());
                   // String name = dataSnapshot.child(postSnapshot.getKey()).child("name").getValue().toString();
                  //  Toast.makeText(getActivity(), name, Toast.LENGTH_SHORT).show();
                    mUploads.add(upload);
                }
                mAdapter.notifyDataSetChanged();
                progress.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                progress.setVisibility(View.INVISIBLE);
            }
        });


        wifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Wifi ++;
                if(Wifi==1){
                    wifi.setBackgroundResource(R.drawable.wifi_lmdpi);
                }else if(Wifi>1){
                    Wifi = 0;
                    wifi.setBackgroundResource(R.drawable.wifi_dmdpi);
                }
                load_rooms(Wifi,Gym,Food,Tranceport);
            }
        });


        gym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gym++;
                if(Gym==1){
                    gym.setBackgroundResource(R.drawable.gym_lmdpi);
                }else if(Gym>1){
                    Gym = 0;
                    gym.setBackgroundResource(R.drawable.gym_dmdpi);
                }
                load_rooms(Wifi,Gym,Food,Tranceport);
            }
        });


        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
Food++;
                if(Food==1){
                    food.setBackgroundResource(R.drawable.f_lmdpi);
                }else if(Food>1){
                    Food = 0;
                    food.setBackgroundResource(R.drawable.f_dmdpi);
                }
                load_rooms(Wifi,Gym,Food,Tranceport);
            }
        });


        tranceport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
Tranceport++;
                if(Tranceport==1){
                    tranceport.setBackgroundResource(R.drawable.tra_lmdpi);
                }else if(Tranceport>1){
                    Tranceport = 0;
                    tranceport.setBackgroundResource(R.drawable.tra_dmdpi);
                }
             load_rooms(Wifi,Gym,Food,Tranceport);

            }
        });

        return view;
    }

    private void load_rooms(int wifi ,int gym ,int food ,int transport) {



        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mUploads.clear();


                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Upload upload = postSnapshot.getValue(Upload.class);
                    upload.setKey(postSnapshot.getKey());

                    int WIFI = Integer.parseInt(dataSnapshot.child(postSnapshot.getKey()).child("wifi").getValue().toString());
                    int GYM = Integer.parseInt(dataSnapshot.child(postSnapshot.getKey()).child("gym").getValue().toString());
                    int FOOD = Integer.parseInt(dataSnapshot.child(postSnapshot.getKey()).child("food").getValue().toString());
                    int Transport = Integer.parseInt(dataSnapshot.child(postSnapshot.getKey()).child("transport").getValue().toString());


              if(wifi<=WIFI && gym<=GYM && food<=FOOD &&transport <=Transport){
                  mUploads.add(upload);
              }




                }
                mAdapter.notifyDataSetChanged();
                progress.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                progress.setVisibility(View.INVISIBLE);
            }
        });

    }


    @Override
    public void onItemClick(int position) {
        Upload selectedItem = mUploads.get(position);
       final String selectedKey = selectedItem.getKey();

       //  Intent ii = new Intent(getContext(), tab1_details.class);
       // ii.putExtra("valuea", selectedKey);
        // startActivity(ii);

        // Toast.makeText(getActivity(), selectedKey+"test", Toast.LENGTH_SHORT).show();
    }
}