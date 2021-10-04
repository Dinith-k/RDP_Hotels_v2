package com.dinith.rdp_hotels.ui.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.dinith.rdp_hotels.R;
import com.dinith.rdp_hotels.ui.settings.u_image;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class edit_room extends AppCompatActivity {
String key,img,name,price,room_type,food,gym,transport,wifi;
ImageView imageview;
    private static final int PICK_IMAGE_REQUEST = 1;
ImageButton update;
    private Uri ImageUri;
    private StorageTask mUploadTask;
    private DatabaseReference mDatabaseRef;
private StorageReference mStorageRef;
EditText roomtitle,price1,room_type1,wifi1,gym1,tra,food1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_room);
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");

        imageview = (ImageView)findViewById(R.id.imageview);
        roomtitle= (EditText)findViewById(R.id.text);
        price1= (EditText)findViewById(R.id.price);
        room_type1= (EditText)findViewById(R.id.room_type);

        wifi1= (EditText)findViewById(R.id.wifi);
        gym1= (EditText)findViewById(R.id.gym);
        tra= (EditText)findViewById(R.id.trsaa);
        food1= (EditText)findViewById(R.id.food);

        update=  (ImageButton) findViewById(R.id.update);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            key = extras.getString("key");
            img = extras.getString("img");
            name = extras.getString("name");
            price = extras.getString("price");
            room_type = extras.getString("room_type");
            food = extras.getString("food");
            gym = extras.getString("gym");
            transport = extras.getString("transport");
            wifi= extras.getString("wifi");



            roomtitle.setText(name);
            Picasso.get().load(img).placeholder(R.mipmap.ic_launcher).fit().centerCrop().into(imageview);
            price1.setText(price);
            room_type1.setText(room_type);
            wifi1.setText(wifi);
            gym1.setText(gym);
            tra.setText(transport);
            food1.setText(food);





        }
        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ImageUri != null) {
                    StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                            + "." + getFileExtension(ImageUri));
                    mUploadTask = fileReference.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Toast.makeText(edit_room.this, "Upload successful", Toast.LENGTH_LONG).show();

                            taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(
                                    new OnCompleteListener<Uri>() {

                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {
                                            String image_link = task.getResult().toString();

                                            mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads").child(key);
                                            update_list user_data = new update_list(food1.getText().toString(),gym1.getText().toString(),image_link,roomtitle.getText().toString(),price1.getText().toString(),"4.5",room_type1.getText().toString(),tra.getText().toString(),wifi1.getText().toString());
                                            mDatabaseRef.setValue(user_data);

                                            Toast.makeText(edit_room.this,"updated successfully",Toast.LENGTH_SHORT).show();

                                        }
                                    });
                        }
                    })

                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(edit_room.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });


                }else {
                    mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads").child(key);
                    update_list user_data = new update_list(food1.getText().toString(),gym1.getText().toString(),img,roomtitle.getText().toString(),price1.getText().toString(),"4.5",room_type1.getText().toString(),tra.getText().toString(),wifi1.getText().toString());
                    mDatabaseRef.setValue(user_data);
                    Toast.makeText(edit_room.this,"updated successfully",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            ImageUri = data.getData();
            Picasso.get().load(ImageUri).into(imageview);
        }
    }
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getApplicationContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }



    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
}