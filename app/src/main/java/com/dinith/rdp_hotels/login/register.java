package com.dinith.rdp_hotels.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dinith.rdp_hotels.MainActivity;
import com.dinith.rdp_hotels.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.Nullable;

public class register extends AppCompatActivity {
ImageButton back,signup,google_login2;
TextView back2;
EditText email2 , password , password2;
    private FirebaseAuth mAuth;
    private FirebaseAuth firebaseAuth;
    FirebaseUser user;
    sotre_data store_data;
    DatabaseReference reference ;
    private GoogleSignInClient mGoogleSignInClient;
    private int RC_SIGN_IN = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        mAuth = FirebaseAuth.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        back = (ImageButton)findViewById(R.id.back);
        back2 = (TextView)findViewById(R.id.back2);
        email2 = (EditText)findViewById(R.id.email);
password = (EditText)findViewById(R.id.password);
password2 = (EditText)findViewById(R.id.password2);
        signup = (ImageButton)findViewById(R.id.signuo);
        google_login2 = (ImageButton)findViewById(R.id.google_login2);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             back_to_welcome();
            }
        });

        google_login2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });


        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), login.class);
                startActivity(intent);
                finish();
            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                registerNewUser();

            }
        });










    }

    private void signIn(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    private void back_to_welcome() {
        Intent intent = new Intent(getApplication(), welcome.class);
        startActivity(intent);
        finish2();
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

    @Override
    public void onBackPressed() {
        back_to_welcome();
    }

    private void registerNewUser() {

        String email, password;

        email = email2.getText().toString();
        password = password2.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter email...", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
            return;
        }


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            storedata();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Registration failed! Please try again later", Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }


    private void storedata() {

        store_data = new sotre_data();
        String username;
        username = "Default user";
        user = firebaseAuth.getCurrentUser();

        reference = FirebaseDatabase.getInstance().getReference().child("users");

            store_data.setusername(username);
            reference.child(user.getUid()).setValue(store_data);
            Intent intent = new Intent(register.this, MainActivity.class);
            startActivity(intent);
            finish2();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask){
        try{

            GoogleSignInAccount acc = completedTask.getResult(ApiException.class);
            Toast.makeText(register.this,"Signed In Successfully",Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(acc);
        }
        catch (ApiException e){
            Toast.makeText(register.this,"Sign In Failed  "+e,Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(null);
        }
    }

    private void FirebaseGoogleAuth(GoogleSignInAccount acct) {
        //check if the account is null

        if (acct != null) {
            Toast.makeText(register.this,"account   ",Toast.LENGTH_SHORT).show();
            AuthCredential authCredential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
            mAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete( Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(register.this, "Successful", Toast.LENGTH_SHORT).show();
                        storedata();
                    } else {
                        Toast.makeText(register.this, "Failed", Toast.LENGTH_SHORT).show();


                    }
                }
            });
        }else {
            Toast.makeText(register.this,"account null  ",Toast.LENGTH_SHORT).show();
        }

    }


}