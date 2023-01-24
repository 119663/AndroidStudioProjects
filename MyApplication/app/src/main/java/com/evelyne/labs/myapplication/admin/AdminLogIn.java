package com.evelyne.labs.myapplication.admin;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.evelyne.labs.myapplication.R;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminLogIn extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl
            ("https://my-application-566c8-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_log_in);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) final EditText passwordsp = findViewById(R.id.passwordsp);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) final EditText emailsp= findViewById(R.id.emailaddresssp);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) final Button loginBtnsp = findViewById(R.id.loginsp);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) final TextView registerBtnsp = findViewById(R.id.registersp);



        loginBtnsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String emailsptxt = emailsp.getText().toString();
                final String passwordsptxt = passwordsp.getText().toString();

                if(emailsptxt.isEmpty() || passwordsptxt.isEmpty()){
                    Toast.makeText(AdminLogIn.this, "Please enter both Emailaddress and Password", Toast.LENGTH_SHORT).show();

                } else {
                    databaseReference.child("Admin").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            // check if phone number exist in database
                            if(snapshot.hasChild(emailsptxt)){
                                // no exist in db
                                //now get password of user from db and match
                                final String getpasswordsp = snapshot.child(emailsptxt).child("password")
                                        .getValue(String.class);
                                if(getpasswordsp.equals(passwordsptxt)){
                                    Toast.makeText(AdminLogIn.this,"LogIn Successful", Toast.LENGTH_SHORT).show();

                                    // open mainActivity on successful login
                                    startActivity(new Intent(AdminLogIn.this, AdminMenu.class));
                                    finish();
                                }
                                else{
                                    Toast.makeText(AdminLogIn.this,"Wrong Password .Try Again"
                                            , Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(AdminLogIn.this,"Wrong Phone Number.Try Again",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        //open register
       // registerBtnsp.setOnClickListener(new View.OnClickListener() {
          //  @Override
           // public void onClick(View view) {
              //  startActivity(new Intent(SpLogIn.this, SpSignUp.class));
           // }
       // });


    }
}


