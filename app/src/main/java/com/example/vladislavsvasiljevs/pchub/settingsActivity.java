package com.example.vladislavsvasiljevs.pchub;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.vladislavsvasiljevs.pchub.Models.settings;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class settingsActivity extends AppCompatActivity {

    TextView getcpuTempLimit, getgpuTempLimit,getNotificationLimit;
    EditText setCPULimit,setGPULimit,setNotificationLimit;
    private DatabaseReference limitReference, limit;
    private ValueEventListener limitListener;
    Button updateValue,UpdateValue2,UpdateValue3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getcpuTempLimit = findViewById(R.id.getCPULimit);
        getgpuTempLimit = findViewById(R.id.getGPULimit);
        getNotificationLimit = findViewById(R.id.getNotificationTime);
        setCPULimit = findViewById(R.id.setCPULimit);
        setGPULimit = findViewById(R.id.setGPULimit);
        setNotificationLimit = findViewById(R.id.setNotificationLimit);

        limitReference = FirebaseDatabase.getInstance().getReference("EmailNotification");//Link to CPU temps readings
        limit = FirebaseDatabase.getInstance().getReference();//Link to CPU temps readings
        CPUAlert();
        GPUAlert();
        NotificationTimerAlert();
        deleteTextOnClick();
        deleteTextOnClick2();
        deleteTextOnClick3();

    }

    private void CPUAlert() {//Method that turns on the computer
        updateValue = findViewById(R.id.updateValue);
        updateValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String setCPULimit = settingsActivity.this.setCPULimit.getText().toString();
                limit.child("EmailNotification/CPUTempAlert").setValue(setCPULimit+" °C");

            }
        });
}

    private void GPUAlert() {//Method that turns on the computer
        UpdateValue2 = findViewById(R.id.UpdateValue2);
        UpdateValue2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String setGPULimit = settingsActivity.this.setGPULimit.getText().toString();
                limit.child("EmailNotification/GPUTempAlert").setValue(setGPULimit+" °C");
            }
        });
    }

    private void NotificationTimerAlert() {//Method that turns on the computer
        UpdateValue3 = findViewById(R.id.UpdateValue3);
        UpdateValue3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String setNotificationLimit  = settingsActivity.this.setNotificationLimit.getText().toString();
                int value;
                value = Integer.parseInt(setNotificationLimit);
                limit.child("EmailNotification/FrequencyOfNotifications").setValue(value);
            }
        });
    }


    private void deleteTextOnClick() {//Method that turns on the computer
        setCPULimit = findViewById(R.id.setCPULimit);
        setCPULimit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCPULimit.setText("");

            }
        });
    }

    private void deleteTextOnClick2() {//Method that turns on the computer
        setGPULimit = findViewById(R.id.setGPULimit);
        setGPULimit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setGPULimit.setText("");

            }
        });
    }

    private void deleteTextOnClick3() {//Method that turns on the computer
        setNotificationLimit = findViewById(R.id.setNotificationLimit);
        setNotificationLimit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNotificationLimit.setText("");

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        //CPU Temp Readings from FireBase
        ValueEventListener limitListener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    settings settings = dataSnapshot.getValue(settings.class);

                    // Log.e(TAG, "onDataChange: Message data is updated: " + cpuTempReading.Value + ", " + ", ");

                    getcpuTempLimit.setText("Current Processor Temperature limit: "+settings.CPUTempAlert);
                    getgpuTempLimit.setText("Current Graphics Processor Temperature limit: "+settings.GPUTempAlert);
                    getNotificationLimit.setText("Current wait until checking for limits in seconds: "+settings.FrequencyOfNotifications);
                    setCPULimit.setText(settings.CPUTempAlert);
                    setGPULimit.setText(settings.GPUTempAlert);


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                getcpuTempLimit.setText("");
                getgpuTempLimit.setText("");
                getNotificationLimit.setText("");
            }
        };

        limitReference.addValueEventListener(limitListener);

        // copy for removing at onStop()
        this.limitListener = limitListener;
    }
}
