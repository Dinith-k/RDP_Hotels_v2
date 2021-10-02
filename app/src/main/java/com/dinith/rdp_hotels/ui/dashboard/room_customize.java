package com.dinith.rdp_hotels.ui.dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.archit.calendardaterangepicker.customviews.CalendarListener;
import com.archit.calendardaterangepicker.customviews.DateRangeCalendarView;
import com.dinith.rdp_hotels.R;
import com.dinith.rdp_hotels.login.sotre_data;
import com.dinith.rdp_hotels.login.welcome;
import com.dinith.rdp_hotels.ui.home.Upload;
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
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;


public class room_customize extends AppCompatActivity  {
    DateRangeCalendarView calendar;
    String img,key;
    ImageView setimg_url;
    ImageButton wifi,gym,food,tranceport;
    int Wifi = 0 , Gym  = 0 , Food = 0, Tranceport = 0;
    ImageButton btn_select_room;
    FirebaseUser user;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabaseRef;
    DatabaseReference reference ;
    private ValueEventListener mDBListener;
    room_data  room_data;
    String s_date , e_date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_customize);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            img = extras.getString("img");
            key = extras.getString("key");

        }

        setimg_url = (ImageView)findViewById(R.id.setimg_url) ;
        wifi = (ImageButton)findViewById(R.id.wifi1) ;
        gym = (ImageButton)findViewById(R.id.gym1) ;
        food = (ImageButton)findViewById(R.id.food1) ;
        tranceport = (ImageButton)findViewById(R.id.tra1) ;
        btn_select_room = (ImageButton)findViewById(R.id.btn_select_room);


        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads").child(key);
        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {





                    int WIFI = Integer.parseInt(dataSnapshot.child("wifi").getValue().toString());
                    int GYM = Integer.parseInt(dataSnapshot.child("gym").getValue().toString());
                    int FOOD = Integer.parseInt(dataSnapshot.child("food").getValue().toString());
                    int Transport = Integer.parseInt(dataSnapshot.child("transport").getValue().toString());

if(WIFI==1){
    wifi.setBackgroundResource(R.drawable.wifi_lmdpi);
}
if(GYM==1){
    gym.setBackgroundResource(R.drawable.gym_lmdpi);
}if(FOOD==1){
                    food.setBackgroundResource(R.drawable.f_lmdpi);
 }
if(Transport==1){

    tranceport.setBackgroundResource(R.drawable.tra_lmdpi);
}


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        Picasso.get()
                .load(img)
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(setimg_url);

        calendar = (DateRangeCalendarView)findViewById(R.id.calendar);

        calendar.setCalendarListener(new CalendarListener() {
            @Override
            public void onFirstDateSelected(Calendar startDate) {



               // Toast.makeText(room_customize.this, "Start Date: " + sdf.format(calendar.getTime()), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDateRangeSelected(Calendar startDate, Calendar endDate) {

                SimpleDateFormat start = new SimpleDateFormat("yyyy-MM-dd");
                GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("Asia/Kolkata"));
                calendar.setTimeInMillis(startDate.getTimeInMillis());

                SimpleDateFormat end = new SimpleDateFormat("yyyy-MM-dd");
                GregorianCalendar calendar2 = new GregorianCalendar(TimeZone.getTimeZone("Asia/Kolkata"));
                calendar2.setTimeInMillis(endDate.getTimeInMillis());

                s_date = start.format(calendar.getTime());
                e_date = end.format(calendar2.getTime());
            }
        });

        btn_select_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(e_date==null){
                    Toast.makeText(room_customize.this,"please select end date",Toast.LENGTH_SHORT).show();
                }else {


                    firebaseAuth = FirebaseAuth.getInstance();
                    room_data = new room_data();
                    user = firebaseAuth.getCurrentUser();
                    reference = FirebaseDatabase.getInstance("https://rdp-hotels-default-rtdb.firebaseio.com").getReference().child("selected_rooms");

                    room_data.setroom_key(key);
                    room_data.setstart_date(s_date);
                    room_data.setend_date(e_date);
                    reference.child(user.getUid()).setValue(room_data);

                    Intent intent = new Intent(getApplication(), menu.class);
                    startActivity(intent);
                    finish();


                }
            }
        });





    }



}