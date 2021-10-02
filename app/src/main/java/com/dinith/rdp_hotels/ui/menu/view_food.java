package com.dinith.rdp_hotels.ui.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dinith.rdp_hotels.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class view_food extends AppCompatActivity {
String img ,key,desc,name,price;
ImageView imageView;
TextView title,Desc,Price;
ImageButton add_to_cart;
EditText qunt;
    DatabaseReference nm;
    cart listData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_food);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        Price =  (TextView)findViewById(R.id.price);
        Desc =  (TextView)findViewById(R.id.desc);
        title =  (TextView)findViewById(R.id.title);
        add_to_cart =  (ImageButton)findViewById(R.id.add_to_cart);
        imageView = (ImageView)findViewById(R.id.imgg);
        qunt = (EditText)findViewById(R.id.qunt);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            img = extras.getString("img");
            key = extras.getString("key");
            desc = extras.getString("desc");
            name = extras.getString("name");
            price = extras.getString("price");


        }

        Price.setText(price);
        title.setText(name);
        Desc.setText(desc);
        Picasso.get()
                .load(img)
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(imageView);



        add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(qunt.getText().toString().equals("")){
                    Toast.makeText(view_food.this,"Quantity Cant be Null",Toast.LENGTH_SHORT).show();
                }else {
                    add_cart(qunt.getText().toString(),key);
                }

            }
        });

    }

    private void add_cart(String value , String key) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        listData = new cart();
        nm = FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).child("cart").child(key);

        listData.setprice(price);
        listData.setname(name);
        listData.setimage(img);
        listData.setdesc(desc);
        listData.setquantity(value);
        listData.setkey(key);

        nm.setValue(listData);


    }
}