package com.example.vladislavsvasiljevs.pchub;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.vladislavsvasiljevs.pchub.Models.cpuTempReading;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class computerStatActivity extends AppCompatActivity {
    private static final String TAG = "MessageActivity";
    //private static final String REQUIRED = "Required";

    private TextView tvAuthor;
    private TextView tvTime;

    //Average Temperatures
    private TextView avgCpuTemp;//Displays average CPU Temperature
    private TextView avgGpuTemp;//Displays average GPU Temperature
    private TextView avgHddTemp;//Displays average HDD Temperature


    //Computer Temperature Reading
    private TextView cpuCurrentTempReading;//Displays Current CPU Temperature
    private TextView cpuMaxTempReading;//Displays Max CPU Temperature
    private TextView gpuCurrentTempReading;//Displays GPU Current Temperature
    private TextView gpuMaxTempReading;//Displays Max GPU Temperature
    private TextView hddCurrentTempReading;//Displays Current HDD Temperature
    private TextView hddMaxTempReading;//Displays Max HDD Temperature
    private TextView ssdCurrentTempReading;//Displays Current SSD Temperature
    private TextView ssdMaxTempReading;//Displays Max SSD Temperature


    //Computer Load Readings section
    private TextView cpuCurrentLoadReading;//Displays Current load of CPU
    private TextView cpuMaxLoadReading;//Displays Max load of CPU
    private TextView gpuCurrentLoadReading;//Displays Current load of GPU
    private TextView gpuMaxLoadReading;//Displays Max load of GPU


    //Computer Information section
    private TextView computerName;//Computer name
    private TextView motherboardName;//Motherboard name, if a laptop it will display model name normally.
    private TextView cpuName;//Computers CPU name
    private TextView gpuName;//Computer GPU name


    private DatabaseReference mDatabase;
    private DatabaseReference mMessageReference;
    private DatabaseReference mMessageReference2;
    private ValueEventListener mMessageListener;
    private ValueEventListener mMessageListener2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_computer_stat);


        //Average Temperatures getting ID's
        avgCpuTemp = findViewById(R.id.avgCpuTemp);
        avgGpuTemp = findViewById(R.id.avgGpuTemp);
        avgHddTemp = findViewById(R.id.avgHddTemp);


        //Computer Temperature Reading
        cpuCurrentTempReading = findViewById(R.id.cpuCurrentTempReading);
        cpuMaxTempReading = findViewById(R.id.cpuMaxTempReading);
        gpuCurrentTempReading = findViewById(R.id.gpuCurrentTempReading);
        gpuMaxTempReading = findViewById(R.id.gpuMaxTempReading);
        hddCurrentTempReading = findViewById(R.id.hddCurrentTempReading);
        hddMaxTempReading = findViewById(R.id.hddMaxTempReading);
        ssdCurrentTempReading = findViewById(R.id.ssdCurrentTempReading);
        ssdMaxTempReading = findViewById(R.id.ssdMaxTempReading);


        //Computer Load Readings getting ID's
        cpuCurrentLoadReading = findViewById(R.id.cpuCurrentLoadReading);
        motherboardName = findViewById(R.id.motherboardName);
        cpuName = findViewById(R.id.cpuName);
        gpuName = findViewById(R.id.gpuName);


        //Computer Information getting ID's
        computerName = findViewById(R.id.computerName);
        motherboardName = findViewById(R.id.motherboardName);
        cpuName = findViewById(R.id.cpuName);
        gpuName = findViewById(R.id.gpuName);



        tvAuthor = findViewById(R.id.activityTitle);
        tvTime = findViewById(R.id.cardViewTitle);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mMessageReference = FirebaseDatabase.getInstance().getReference("PCHub/ComputerStatistics/number/Children/0/Children/1/Children/1/Children/6");
        mMessageReference2 = FirebaseDatabase.getInstance().getReference("PCHub/message/messages");
    }

    @Override
    protected void onStart() {
        super.onStart();

        ValueEventListener messageListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    cpuTempReading cpuTempReading = dataSnapshot.getValue(cpuTempReading.class);

                   // Log.e(TAG, "onDataChange: Message data is updated: " + cpuTempReading.Value + ", " + ", ");

                    cpuCurrentTempReading.setText(cpuTempReading.Value);
                    cpuMaxTempReading.setText(cpuTempReading.Max);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
                Log.e(TAG, "onCancelled: Failed to read message");

                cpuCurrentTempReading.setText("");
                cpuMaxTempReading.setText("");
            }
        };
        mMessageReference.addValueEventListener(messageListener);

        // copy for removing at onStop()
        mMessageListener = messageListener;

    }

//        ValueEventListener messageListener2 = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    stats2 message2 = dataSnapshot.getValue(stats2.class);
//
//                    Log.e(TAG, "onDataChange: Message data is updated: " + ", " + message2.Value + ", ");
//
//                    //tvAuthor.setText(message.Value);
//                    tvTime.setText(message2.Value);
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Failed to read value
//                Log.e(TAG, "onCancelled: Failed to read message");
//
//                tvAuthor.setText("");
//                tvTime.setText("");
//            }
//        };
//        mMessageReference2.addValueEventListener(messageListener2);
//
//        // copy for removing at onStop()
//        mMessageListener = messageListener2;
//    }



}

