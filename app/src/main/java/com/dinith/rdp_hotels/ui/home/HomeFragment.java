package com.dinith.rdp_hotels.ui.home;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.dinith.rdp_hotels.R;
import com.dinith.rdp_hotels.ui.admin.rooms;
import com.dinith.rdp_hotels.ui.checkout.checkout;
import com.dinith.rdp_hotels.ui.menu.menu;
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
   ImageButton reservation,menu,checkin,checkout;
   TextView name;
    private DatabaseReference mDatabaseRef;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        profile = (ImageView)view.findViewById(R.id.profile);
        name= (TextView)view.findViewById(R.id.name);

        reservation = (ImageButton) view.findViewById(R.id.reservation);
        menu = (ImageButton) view.findViewById(R.id.menu);
        checkin = (ImageButton) view.findViewById(R.id.checkin);
        checkout = (ImageButton) view.findViewById(R.id.checkout);


        reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                  navController.navigate(R.id.navigation_dashboard);
            }
        });


        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), com.dinith.rdp_hotels.ui.menu.menu.class);
                startActivity(intent);
            }
        });


        checkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), com.dinith.rdp_hotels.ui.menu.menu.class);
                startActivity(intent);

            }
        });



        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), com.dinith.rdp_hotels.ui.checkout.checkout.class);
                startActivity(intent);

            }
        });


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