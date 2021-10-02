package com.dinith.rdp_hotels.ui.checkout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dinith.rdp_hotels.R;
import com.dinith.rdp_hotels.ui.menu.Food;
import com.dinith.rdp_hotels.ui.menu.foodAdapter;
import com.dinith.rdp_hotels.ui.menu.menu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class checkout extends AppCompatActivity {
ImageView imageview;
    private DatabaseReference f_DatabaseRef,c_DatabaseRef,cart_DatabaseRef;
    private ValueEventListener f_DBListener,c_DBListener,cart_DBListener;
    String room_key,start_d,end_d,img,title,desc,price,total;
    TextView r_title,Desc,r_price,time_f,date_count,set_total;
    RecyclerView food_view;
    private List<Food> f_Uploads;
    private checkoutAdapter f_Adapter;
    int dateDifference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        imageview = (ImageView)findViewById(R.id.imageview);
        r_title= (TextView)findViewById(R.id.r_title);
        Desc= (TextView)findViewById(R.id.Desc);
        r_price= (TextView)findViewById(R.id.r_price);
        time_f= (TextView)findViewById(R.id.time_f);
        date_count= (TextView)findViewById(R.id.date_count);
        set_total= (TextView)findViewById(R.id.set_total);
        food_view= (RecyclerView)findViewById(R.id.food_view);


        f_Uploads = new ArrayList<>();
        f_Adapter = new checkoutAdapter(checkout.this, f_Uploads);



        //////room.//////

        f_DatabaseRef = FirebaseDatabase.getInstance().getReference("selected_rooms").child(user.getUid().toString());
        f_DBListener = f_DatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

if(dataSnapshot.exists()){


                room_key= dataSnapshot.child("room_key").getValue(String.class);
                start_d= dataSnapshot.child("start_date").getValue(String.class);
                end_d= dataSnapshot.child("end_date").getValue(String.class);


                time_f.setText(start_d+" - "+end_d);



     dateDifference = (int) getDateDiff(new SimpleDateFormat("yyyy-MM-dd"), start_d, end_d);

    date_count.setText(String.valueOf(dateDifference));




                c_DatabaseRef = FirebaseDatabase.getInstance().getReference("uploads").child(room_key);


                c_DBListener = c_DatabaseRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        img= dataSnapshot.child("imageUrl").getValue(String.class);
                        title= dataSnapshot.child("name").getValue(String.class);
                        desc= dataSnapshot.child("room_type").getValue(String.class);
                        price= dataSnapshot.child("price").getValue(String.class);

                        Desc.setText(desc);
                        r_title.setText(title);
                        r_price.setText(price);
                        Picasso.get().load(img).placeholder(R.mipmap.ic_launcher).fit().centerCrop().into(imageview);

                        String prce_l;
                        prce_l =price ;
                        prce_l = prce_l.replaceAll("[^-?0-9]+", "");
                        prce_l = prce_l.replaceAll(" ", "");

                        int toal = Integer.valueOf(prce_l)*dateDifference;
                        set_total.setText("Total "+String.valueOf(toal));

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(checkout.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });





}else {
    Toast.makeText(checkout.this, "please select room first", Toast.LENGTH_SHORT).show();

}



            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(checkout.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }

        });
/////////////end room//////



        cart_DBListener = cart_DatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                f_Uploads.clear();


                for (DataSnapshot foods : dataSnapshot.getChildren()) {


                    Food upload = foods.getValue(Food.class);
                    upload.setkey(foods.getKey());
                    f_Uploads.add(upload);


                }
                f_Adapter.notifyDataSetChanged();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(checkout.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });






    }



        public static long getDateDiff(SimpleDateFormat format, String oldDate, String newDate) {
            try {
                return TimeUnit.DAYS.convert(format.parse(newDate).getTime() - format.parse(oldDate).getTime(), TimeUnit.MILLISECONDS);
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }


}