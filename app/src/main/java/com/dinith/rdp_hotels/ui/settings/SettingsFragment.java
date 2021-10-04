package com.dinith.rdp_hotels.ui.settings;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.dinith.rdp_hotels.R;
import com.dinith.rdp_hotels.ui.home.Upload;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import static android.app.Activity.RESULT_OK;

public class SettingsFragment extends Fragment {
    private static final int PICK_IMAGE_REQUEST = 1;
                                ImageButton profile;
    private StorageTask mUploadTask;
    DatabaseReference nm;
    private Uri ImageUri;
    EditText Name,email;
    TextView setemail;
ImageButton updateprofile,logout,changepasss;
    private DatabaseReference mDatabaseRef;
    Button add;
String Name_f_db;
    private StorageReference mStorageRef;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_settings, container, false);
Name = (EditText)view.findViewById(R.id.getname) ;
        logout = (ImageButton)view.findViewById(R.id.logout);
        setemail = (TextView)view.findViewById(R.id.setemail) ;
        changepasss= (ImageButton)view.findViewById(R.id.changepasss);
        profile  = (ImageButton)view.findViewById(R.id.profile);
        updateprofile = (ImageButton)view.findViewById(R.id.changepw);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        setemail.setText(user.getEmail().toString());

        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("username").exists()){
                    Name.setHint(snapshot.child("username").getValue().toString());
                }else {
                    Name.setHint("user name not found");
                }
                if(snapshot.child("imageUrl").exists()){


                    Picasso.get().load(snapshot.child("imageUrl").getValue().toString()).placeholder(R.mipmap.ic_launcher).fit().centerCrop().into(profile);



                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






        updateprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Name.getText().toString().equals("")){

                    if (mUploadTask != null && mUploadTask.isInProgress()) {
                        Toast.makeText(getActivity(), "Upload in progress", Toast.LENGTH_SHORT).show();
                    } else {
                        upload_profile();
                    }

                }else {
 update_user_name(Name.getText().toString());

                    if (mUploadTask != null && mUploadTask.isInProgress()) {
                        Toast.makeText(getActivity(), "Upload in progress", Toast.LENGTH_SHORT).show();
                    } else {
                        upload_profile();
                    }
                }



            }
        });



        changepasss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.resetpawd);
                dialog.setCancelable(true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                add = (Button) dialog.findViewById(R.id.add);
                email = (EditText) dialog.findViewById(R.id.capital21);

                dialog.show();


                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        resetUserPassword(email.getText().toString());

                        dialog.dismiss();

                    }
                });

            }
        });







        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Are you sure you want to sign out?");
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Logout();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();

                    }
                });
                AlertDialog d = builder.create();
                d.show();
            }
        });





        return view;
    }

    private void update_user_name(String name) {

        HashMap hashMap = new HashMap();
        hashMap.put("username",name);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        nm = FirebaseDatabase.getInstance().getReference("users").child(user.getUid().toString());
        nm.updateChildren(hashMap);
        Toast.makeText(getContext(),"User name updated successfully",Toast.LENGTH_SHORT).show();



    }

    public void resetUserPassword(String email){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("verifying..");
        progressDialog.show();

        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Reset password instructions has sent to your email",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(getContext(),
                                    "Email don't exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void Logout() {
        FirebaseAuth.getInstance().signOut();
        getActivity().finish();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            ImageUri = data.getData();
            Picasso.get().load(ImageUri).into(profile);
        }
    }
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getActivity().getApplicationContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


    private void upload_profile() {
        if (ImageUri != null) {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(ImageUri));
            mUploadTask = fileReference.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Toast.makeText(getContext(), "Upload successful", Toast.LENGTH_LONG).show();

                    taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(
                            new OnCompleteListener<Uri>() {

                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    String image_link = task.getResult().toString();

                                        String name = Name.getHint().toString();
                                        u_image user_data = new u_image(image_link,name);
                                        mDatabaseRef.setValue(user_data);

                                }
                            });
                }
            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


        }


        else {
            Toast.makeText(getContext(), "No image selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
}