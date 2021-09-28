package com.dinith.rdp_hotels.ui.dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dinith.rdp_hotels.R;
import com.squareup.picasso.Picasso;

public class select_room extends AppCompatActivity {
ImageView setimg_url;
RatingBar rate;
    String img,key,name,rate_,price_,room_type_;
    TextView room_name,room_type,price;
    ImageButton btn_select_room;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_room);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        btn_select_room = (ImageButton)findViewById(R.id.btn_select_room);
        price=(TextView)findViewById(R.id.price);
        room_type=(TextView)findViewById(R.id.room_type);
        rate = (RatingBar)findViewById(R.id.rate);
        room_name =(TextView)findViewById(R.id.room_name);
        room_type=(TextView)findViewById(R.id.room_type);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            img = extras.getString("img");
            key = extras.getString("key");
            name = extras.getString("name");
            rate_ = extras.getString("rate");
            price_= extras.getString("price");
            room_type_= extras.getString("room_type");
        }

        room_name.setText(name);
        price.setText(price_);
        room_type.setText(room_type_);
       rate.setRating(Float.parseFloat(rate_));
        setimg_url = (ImageView)findViewById(R.id.setimg_url);
        Picasso.get()
                .load(img)
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(setimg_url);



        btn_select_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(select_room.this, room_customize.class);
                i.putExtra("key",key);
                i.putExtra("img",img);
                startActivity(i);
                finish();
            }
        });

    }

    public void finish2() {
        super.finish();
        overridePendingTransition(R.anim.a, R.anim.b);
    }

    @Override
    public void finish() {
        super.finish();

        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}