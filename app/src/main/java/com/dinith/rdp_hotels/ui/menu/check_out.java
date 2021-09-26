package com.dinith.rdp_hotels.ui.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.dinith.rdp_hotels.R;
import com.dinith.rdp_hotels.ui.home.Upload;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
//menu Checkout creation
public class check_out extends AppCompatActivity {
    DatabaseReference dr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);


    /*    remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String getkey;
                getkey =  hidden_key.getText().toString();
                dr = FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).child("cart").child(getkey);

                dr.getRef().removeValue();




            }
        });
    }*/

    }

}