package com.dinith.rdp_hotels.ui.menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dinith.rdp_hotels.R;
import com.dinith.rdp_hotels.login.register;
import com.dinith.rdp_hotels.ui.checkout.checkout;
import com.dinith.rdp_hotels.ui.home.ImageAdapter;
import com.dinith.rdp_hotels.ui.home.Upload;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.common.data.DataHolder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
//Menu database creation
public class menu extends AppCompatActivity  {

    private RecyclerView food_view;
    private foodAdapter f_Adapter;
    private DatabaseReference f_DatabaseRef,c_DatabaseRef;
    private ValueEventListener f_DBListener,c_DBListener;
    private List<Food> f_Uploads;
    ImageView cart_img;
    ProgressBar progress;
EditText   searchtext;
TextView cart_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }


        cart_count  = (TextView)findViewById(R.id.cart_count);
        searchtext = (EditText)findViewById(R.id.searchtext) ;
        f_Uploads = new ArrayList<>();
        f_Adapter = new foodAdapter(menu.this, f_Uploads);
        progress = (ProgressBar)findViewById(R.id.progress2);
        food_view = findViewById(R.id.food_view);
        food_view.setHasFixedSize(true);
        food_view.setLayoutManager(new LinearLayoutManager(menu.this));
        food_view.setAdapter(f_Adapter);
        food_view.setLayoutManager(new GridLayoutManager(menu.this, 1));



 update_cart();


        cart_img = (ImageView)findViewById(R.id.cart_img) ;


        cart_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), checkout.class);
                startActivity(intent);
               // finish();

            }
        });





        f_DatabaseRef = FirebaseDatabase.getInstance().getReference("food"); //connect Firebase

        searchtext.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                search(s.toString());
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // filter your list from your input

                //you can use runnable postDelayed like 500 ms to delay search text
            }
        });



        f_DBListener = f_DatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                f_Uploads.clear();


                for (DataSnapshot foods : dataSnapshot.getChildren()) {


                    Food upload = foods.getValue(Food.class);
                    upload.setkey(foods.getKey());
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

    private void update_cart() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        c_DatabaseRef = FirebaseDatabase.getInstance().getReference("users").child(user.getUid().toString()).child("cart");
        c_DBListener = c_DatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

int Cart_count = 0 ;

                for (DataSnapshot c_count : dataSnapshot.getChildren()) {
if(c_count.exists()){
    Cart_count++;

    // Toast.makeText(menu.this, c_count.getKey().toString(), Toast.LENGTH_SHORT).show();
    cart_count.setText(String.valueOf(Cart_count));
}else {
    cart_count.setText("0");
    Toast.makeText(menu.this, "cart is empty", Toast.LENGTH_SHORT).show();
}



                }


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(menu.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }


    public void search(String s){

        f_Uploads.clear();
        progress.setVisibility(View.VISIBLE);
                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

                DatabaseReference dateRef = rootRef.child("food");

                Query query = dateRef.orderByChild("name").startAt(s).endAt(s + "\uf8ff");


                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                            Food upload = postSnapshot.getValue(Food.class);
                            f_Uploads.add(upload);
                         //   Datastore upload = postSnapshot.getValue(Datastore.class);
                          //  mUploads.add(upload);
                        }
                        f_Adapter.notifyDataSetChanged();
                        progress.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(menu.this, databaseError.getMessage(),Toast.LENGTH_SHORT).show();
                        progress.setVisibility(View.INVISIBLE);
                    }
                });
            }


}