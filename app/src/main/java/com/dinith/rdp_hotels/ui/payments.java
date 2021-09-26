package com.dinith.rdp_hotels.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dinith.rdp_hotels.R;
import com.dinith.rdp_hotels.ui.home.ImageAdapter;
import com.dinith.rdp_hotels.ui.home.Upload;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class payments extends AppCompatActivity {
    private DatabaseReference Ref;
    private ValueEventListener DBListener;
    private ImageAdapter P_adapter;
    ProgressBar progress_view;
    private List<Payments_list> payments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);
        payments = new ArrayList<>();
load_payments();
    }

    private void load_payments() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Ref = FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).child("payments");
        DBListener = Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {



                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Payments_list payment = postSnapshot.getValue(Payments_list.class);
                    payments.add(payment);
                }
                progress_view.setVisibility(View.INVISIBLE);
                P_adapter.notifyDataSetChanged();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                progress_view.setVisibility(View.INVISIBLE);
                Toast.makeText(payments.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


}