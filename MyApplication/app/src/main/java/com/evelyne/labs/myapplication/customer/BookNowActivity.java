package com.evelyne.labs.myapplication.customer;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.evelyne.labs.myapplication.R;
import com.evelyne.labs.myapplication.serviceprovider.SpLogIn;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class BookNowActivity extends AppCompatActivity {
    private TextView textview;
    private Button book;
    DatePickerDialog picker;
    TimePickerDialog timepicker;

    //create object of DatabaseReference class to access firebase's Realtime Database
    //FirebaseDatabase database = FirebaseDatabase.getInstance();
    //DatabaseReference myRef = database.getReference("");

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl
            ("https://my-application-566c8-default-rtdb.firebaseio.com/");
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_now);
        final EditText capacity  = findViewById(R.id.capacity);
        final EditText location=  findViewById(R.id.loc);
        final EditText date  = findViewById(R.id.date);
        final EditText time  = findViewById(R.id.time);
        final Button book = findViewById(R.id.book);

        time.setInputType(InputType.TYPE_NULL);
        time.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        final Calendar cldr = Calendar.getInstance();
                                        int hour = cldr.get(Calendar.HOUR_OF_DAY);
                                        int minutes = cldr.get(Calendar.MINUTE);
                                        // time picker dialog
                                        timepicker = new TimePickerDialog(BookNowActivity.this,
                                                new TimePickerDialog.OnTimeSetListener() {
                                                    @Override
                                                    public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                                        time.setText(sHour + ":" + sMinute);
                                                    }
                                                }, hour, minutes, true);
                                        timepicker.show();
                                    }
                                });
        date.setInputType(InputType.TYPE_NULL);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(BookNowActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
       // @SuppressLint({"MissingInflatedId", "LocalSuppress"}) final TextView loginBtnsp = findViewById(R.id.login);

        book.setOnClickListener(view -> {

            //get data from edits
            final String capacitytxt = capacity.getText().toString();
            final String locationtxt = location.getText().toString();
           // final String datetxt = date.getText().toString();
            //final String timetxt = time.getText().toString();
            date.setText("Selected Date: ");
            time.setText("Selected Time");
            //check if user fills all fields before sending data to firebase
            if(capacitytxt.isEmpty() ||locationtxt.isEmpty() ||
                    date.isImportantForAutofill() ||time.isImportantForAutofill()
                   ){
                Toast.makeText(BookNowActivity.this, "Please fill all fields", Toast.LENGTH_SHORT
                ).show();
                // check if passwords are matching
            }
            else {
                databaseReference.child("book");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                       @Override
                          public void onDataChange(@NonNull DataSnapshot snapshot) {
                                     //sending data to firebase realtime database
                          //phone number is unique identifier so comes under all other details
                               databaseReference.child("book").child("capacity").setValue(capacitytxt);
                               databaseReference.child("book").child("location").setValue(locationtxt);
                                 databaseReference.child("book").child("time").setValue(time);
                                       databaseReference.child("book").child("date").setValue(date);

                         //show success message and finish ativity
                  Toast.makeText(BookNowActivity.this, "User successfully registered", Toast.LENGTH_SHORT).show();
                  startActivity(new Intent(BookNowActivity.this, SpLogIn.class));
       finish();
                   }

         @Override
          public void onCancelled(@NonNull DatabaseError error) {

           }
           })

     //open register


                ;



                }
            // loginBtn.setOnClickListener(new View.OnClickListener() {
            // @Override
            //public void onClick(View view) {
            // finish();
            // }
            // });
            //open login
            book.setOnClickListener(view1 -> startActivity(new Intent(BookNowActivity.this, SpLogIn.class)));

        });
    }
}


