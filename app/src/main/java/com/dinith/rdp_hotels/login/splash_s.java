package com.dinith.rdp_hotels.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.dinith.rdp_hotels.MainActivity;
import com.dinith.rdp_hotels.R;
import com.dinith.rdp_hotels.ui.admin.rooms;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class splash_s extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_s);



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

               checklogin();

            }
        }, 3000);

    }

    private void checklogin() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {

            if(user.getEmail().equals("admin@gmail.com")){
                Intent intent = new Intent(getApplication(), rooms.class);
                startActivity(intent);
                finish();
            }else {
                Intent intent = new Intent(getApplication(), MainActivity.class);
                startActivity(intent);
                finish();
            }


        }else {
            Intent intent = new Intent(getApplication(), welcome.class);
            startActivity(intent);
            finish();

        }



    }

    @Override
    public void finish() {
        super.finish();

         overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}