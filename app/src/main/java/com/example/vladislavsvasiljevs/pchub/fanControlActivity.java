package com.example.vladislavsvasiljevs.pchub;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class fanControlActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;//Firebase reference

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fan_control);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        fanOffButton();
        fanOnButton();
    }


    private void fanOnButton() {//Method that turns on the computer
        Button fanOn = findViewById(R.id.greenBtn);//Assigning button to computerOn
        fanOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                alertDialogOnButton();//Calling alert dialog method for on button
                databaseReference.child("fanControl").setValue(1);
            }
        });
    }

    private void fanOffButton() {//Method that turns off the computer
        Button fanOff = findViewById(R.id.redBtn);//Assigning a button to computer off
        fanOff.setOnClickListener(new View.OnClickListener() {//Creating a click listener
            @Override
            public void onClick(View view) {
//                alertDialogOffButton();//Calling alert dialog method for on button
                databaseReference.child("fanControl").setValue(0);

            }
        });
    }
}
