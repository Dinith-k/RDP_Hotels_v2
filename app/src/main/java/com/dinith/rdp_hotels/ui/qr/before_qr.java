package com.dinith.rdp_hotels.ui.qr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dinith.rdp_hotels.R;

public class before_qr extends AppCompatActivity {
String room_tot,menu_tot,date;
TextView room_total,menu_total,total;
int cal,room_tot1,menu_tot1 ;
ImageButton show_qr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_qr);

        room_total = (TextView)findViewById(R.id.room_total);
        menu_total = (TextView)findViewById(R.id.menu_total);
        total = (TextView)findViewById(R.id.total);
        show_qr = (ImageButton)findViewById(R.id.show_qr);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            room_tot = extras.getString("room_tot");
            menu_tot = extras.getString("menu_tot");
            date= extras.getString("date");
            room_tot1 = Integer.parseInt(room_tot);
            menu_tot1 = Integer.parseInt(menu_tot);


           cal  = room_tot1+menu_tot1;

            room_total.setText("Total          "+room_tot);
            menu_total.setText("Menu Order          "+menu_tot);
            total.setText("Total   "+String.valueOf(cal));





            show_qr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplication(), generate_qr.class);
                    intent.putExtra("tot",String.valueOf(cal));
                    intent.putExtra("date",String.valueOf(date));
                    startActivity(intent);


                }
            });








        }else {
            Toast.makeText(before_qr.this,"Error",Toast.LENGTH_SHORT).show();
        }



    }
}