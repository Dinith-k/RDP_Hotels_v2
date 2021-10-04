package com.dinith.rdp_hotels.ui.qr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.dinith.rdp_hotels.MainActivity;
import com.dinith.rdp_hotels.R;
import com.dinith.rdp_hotels.ui.checkout.checkout;
import com.dinith.rdp_hotels.ui.menu.Food;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class generate_qr extends AppCompatActivity {
    ImageView imageView;
    String final_code ;
    DatabaseReference nm,nm2;
    ImageButton back_home;
    private DatabaseReference cart_DatabaseRef,cart_DatabaseRef2;
    private ValueEventListener cart_DBListener,cart_DBListener2;
    purchase listData;
    DatabaseReference dr;
    FirebaseUser user;
String tot,date;
    public final static int QRcodeWidth = 500 ;
    Bitmap bitmap ;
  String Keys ="";
    boolean processDone = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qr);
        back_home = (ImageButton)findViewById(R.id.back_home);
        imageView = (ImageView)findViewById(R.id.imageview);
        user = FirebaseAuth.getInstance().getCurrentUser();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            tot = extras.getString("tot");
            date = extras.getString("date");




            cart_DatabaseRef = FirebaseDatabase.getInstance().getReference("users").child(user.getUid().toString()).child("cart");
            cart_DBListener = cart_DatabaseRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot keys : dataSnapshot.getChildren()) {

                        Keys  = Keys+","+keys.getKey().toString();

                    }


                    cart_DatabaseRef2 = FirebaseDatabase.getInstance().getReference("selected_rooms").child(user.getUid().toString()).child("room_key");
                    cart_DBListener2 = cart_DatabaseRef2.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                          //  Toast.makeText(generate_qr.this, dataSnapshot.getValue().toString(), Toast.LENGTH_SHORT).show();

if(dataSnapshot.exists()&& !processDone){
    listData = new purchase();
    dr = FirebaseDatabase.getInstance().getReference("purchased").child(user.getUid().toString());
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    listData.setdate(date);
    listData.setmenu_items(Keys);
    listData.setroom_key(dataSnapshot.getValue().toString());
    listData.settotal_price(tot);
    String key = dr.push().getKey();
    dr.child(key).setValue(listData);

    String qr_code = "date/"+date+"menu_keys/"+Keys+"room_key/"+dataSnapshot.getValue().toString()+"total/"+tot;
    try {
        bitmap = TextToImageEncode(qr_code);

        imageView.setImageBitmap(bitmap);

    } catch (WriterException e) {
        e.printStackTrace();
    }
    processDone = true;
}else {

}

                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(generate_qr.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });





                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(generate_qr.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });






        }

        back_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nm = FirebaseDatabase.getInstance().getReference("users").child(user.getUid().toString()).child("cart");
                nm.getRef().removeValue();

                nm2 = FirebaseDatabase.getInstance().getReference("selected_rooms").child(user.getUid().toString());
                nm2.getRef().removeValue();

                Intent intent = new Intent(getApplication(), MainActivity.class);
               startActivity(intent);
               finish2();
            }
        });


    }

    Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.black):getResources().getColor(R.color.white);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }

    public void finish2() {
        super.finish();
        overridePendingTransition(R.anim.a, R.anim.b);
    }
}