package com.dinith.rdp_hotels.ui.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.dinith.rdp_hotels.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale; //
//Add cart function
public class add_to_cart extends AppCompatActivity {
    DatabaseReference nm;
    cart listData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);

    }


    //Connect cart database
    private void add_cart(String name , String size , String available ,int quantity) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        listData = new cart();
        nm = FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).child("cart");

        listData.setname(name);
        listData.setsize(size);
        listData.setquntity(quantity);
        listData.setavailable(available);
        nm.setValue(listData);
    }


}