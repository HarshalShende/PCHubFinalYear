package com.example.vladislavsvasiljevs.pchub;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vladislavsvasiljevs.pchub.Models.computerNameReading;
import com.example.vladislavsvasiljevs.pchub.Models.cpuLoadReading;
import com.example.vladislavsvasiljevs.pchub.Models.cpuNameReading;
import com.example.vladislavsvasiljevs.pchub.Models.cpuTempReading;
import com.example.vladislavsvasiljevs.pchub.Models.gpuLoadReading;
import com.example.vladislavsvasiljevs.pchub.Models.gpuNameReading;
import com.example.vladislavsvasiljevs.pchub.Models.gpuTempReading;
import com.example.vladislavsvasiljevs.pchub.Models.hddTempReading;
import com.example.vladislavsvasiljevs.pchub.Models.motherboardNameReading;
import com.example.vladislavsvasiljevs.pchub.Models.ssdTempReading;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class computerStatActivity extends AppCompatActivity {
    private static final String TAG = "MessageActivity";
    //private static final String REQUIRED = "Required";
    DatabaseHelper mDatabaseHelper;

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

    
    //Database Reference
    private DatabaseReference mDatabase;//Variable to get the instance of the FireBase database
    
    
    //Database Reference for  Computer Temperature Reading section
    private DatabaseReference cpuTempReference;
    private DatabaseReference gpuTempReference;
    private DatabaseReference hddTempReference;
    private DatabaseReference ssdTempReference;

    
    //Value Event Listener for Computer Temperature Reading section
    private ValueEventListener cpuTempListener;
    private ValueEventListener gpuTempListener;
    private ValueEventListener hddTempListener;
    private ValueEventListener ssdTempListener;


    //Database Reference for Computer Load Reading section
    private DatabaseReference cpuLoadReference;
    private DatabaseReference gpuLoadReference;


    //Value Event Listener for Computer Load Reading section
    private ValueEventListener cpuLoadListener;
    private ValueEventListener gpuLoadListener;


    //Database Reference for Computer Information section
    private DatabaseReference computerNameReference;
    private DatabaseReference motherboardNameReference;
    private DatabaseReference cpuNameReference;
    private DatabaseReference gpuNameReference;

    //Value Event Listener for Computer Information section
    private ValueEventListener computerNameListener;
    private ValueEventListener motherboardNameListener;
    private ValueEventListener cpuNameListener;
    private ValueEventListener gpuNameListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_computer_stat);
        mDatabaseHelper = new DatabaseHelper(this);

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
        cpuMaxLoadReading = findViewById(R.id.cpuMaxLoadReading);
        gpuCurrentLoadReading = findViewById(R.id.gpuCurrentLoadReading);
        gpuMaxLoadReading = findViewById(R.id.gpuMaxLoadReading);

        //Computer Information getting ID's
        computerName = findViewById(R.id.computerName);
        motherboardName = findViewById(R.id.motherboardName);
        cpuName = findViewById(R.id.cpuName);
        gpuName = findViewById(R.id.gpuName);

        mDatabase = FirebaseDatabase.getInstance().getReference();//Getting a instance of FireBase database
        //Computer Temperature Readings - References
        cpuTempReference = FirebaseDatabase.getInstance().getReference("PCHub/ComputerStatistics/number/Children/0/Children/1/Children/1/Children/4");//Link to CPU temps readings
        gpuTempReference = FirebaseDatabase.getInstance().getReference("PCHub/ComputerStatistics/number/Children/0/Children/3/Children/1/Children/0");//Link to GPU temps readings
        hddTempReference = FirebaseDatabase.getInstance().getReference("PCHub/ComputerStatistics/number/Children/0/Children/4/Children/1");//Link to HDD temps readings
        ssdTempReference = FirebaseDatabase.getInstance().getReference("PCHub/ComputerStatistics/number/Children/0/Children/4/Children/0/Children/0");//Link to SSD temps readings


        //Computer Temperature Reading - References
        cpuLoadReference = FirebaseDatabase.getInstance().getReference("PCHub/ComputerStatistics/number/Children/0/Children/1/Children/2/Children/0");//Link to CPU load readings
        gpuLoadReference = FirebaseDatabase.getInstance().getReference("PCHub/ComputerStatistics/number/Children/0/Children/3/Children/2/Children/0");//Link to GPU load readings


        //Computer Information Readings - References
        computerNameReference = FirebaseDatabase.getInstance().getReference("PCHub/ComputerStatistics/number/Children/0");//Link to Computers name reading
        motherboardNameReference = FirebaseDatabase.getInstance().getReference("PCHub/ComputerStatistics/number/Children/0/Children/0");//Link to Motherboard Name reading
        cpuNameReference = FirebaseDatabase.getInstance().getReference("PCHub/ComputerStatistics/number/Children/0/Children/1");//Link to CPU Name
        gpuNameReference = FirebaseDatabase.getInstance().getReference("PCHub/ComputerStatistics/number/Children/0/Children/3");//Link to GPU Name
    }

    @Override
    protected void onStart() {
        super.onStart();

        //CPU Temp Readings from FireBase
        ValueEventListener cpuListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    cpuTempReading cpuTempReading = dataSnapshot.getValue(cpuTempReading.class);

                    // Log.e(TAG, "onDataChange: Message data is updated: " + cpuTempReading.Value + ", " + ", ");

                    cpuCurrentTempReading.setText(cpuTempReading.Value);
                    cpuMaxTempReading.setText(cpuTempReading.Max);
                    Date c = Calendar.getInstance().getTime();//Getting time and date
                    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");//Formatting date and time to be dd/mm/yyyy
                    String formattedDate = df.format(c);
                    String changeToString = cpuTempReading.Value;//Storing cpuTempReading in changeToString
                    changeToString = changeToString.replaceAll("[^a-zD-Z0-9.]+","");//Removing degree sign from our string
                    Log.e(TAG, "gg "+formattedDate);
                    AddCPUData(formattedDate,changeToString);//Adding the cleaned up string to our local sqlite database
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
        cpuTempReference.addValueEventListener(cpuListener);

        // copy for removing at onStop()
        cpuTempListener = cpuListener;


        //GPU Temp Readings from FireBase
        ValueEventListener gpuListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    gpuTempReading gpuTempReading = dataSnapshot.getValue(gpuTempReading.class);

                    // Log.e(TAG, "onDataChange: Message data is updated: " + cpuTempReading.Value + ", " + ", ");

                    gpuCurrentTempReading.setText(gpuTempReading.Value);
                    gpuMaxTempReading.setText(gpuTempReading.Max);
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
        gpuTempReference.addValueEventListener(gpuListener);

        // copy for removing at onStop()
        gpuTempListener = gpuListener;


        //HDD Temp Readings from FireBase
        ValueEventListener hddListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    hddTempReading hddTempReading = dataSnapshot.getValue(hddTempReading.class);

                    // Log.e(TAG, "onDataChange: Message data is updated: " + cpuTempReading.Value + ", " + ", ");

                    hddCurrentTempReading.setText(hddTempReading.Value);
                    hddMaxTempReading.setText(hddTempReading.Max);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
                Log.e(TAG, "onCancelled: Failed to read message");

                hddCurrentTempReading.setText("");
                hddMaxTempReading.setText("");
            }
        };
        hddTempReference.addValueEventListener(hddListener);

        // copy for removing at onStop()
        hddTempListener = hddListener;


        //SSD Temp Readings from FireBase
        ValueEventListener ssdListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ssdTempReading ssdTempReading = dataSnapshot.getValue(ssdTempReading.class);

                    // Log.e(TAG, "onDataChange: Message data is updated: " + cpuTempReading.Value + ", " + ", ");

                    ssdCurrentTempReading.setText(ssdTempReading.Value);
                    ssdMaxTempReading.setText(ssdTempReading.Max);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
                Log.e(TAG, "onCancelled: Failed to read message");

                ssdCurrentTempReading.setText("");
                ssdMaxTempReading.setText("");
            }
        };
        ssdTempReference.addValueEventListener(ssdListener);

        // copy for removing at onStop()
        ssdTempListener = ssdListener;
//End of Computer Temperature readings section


        //CPU Load Readings from FireBase
        ValueEventListener cpuloadListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    cpuLoadReading cpuLoadReading = dataSnapshot.getValue(cpuLoadReading.class);

                    // Log.e(TAG, "onDataChange: Message data is updated: " + cpuTempReading.Value + ", " + ", ");

                    cpuCurrentLoadReading.setText(cpuLoadReading.Value);
                    cpuMaxLoadReading.setText(cpuLoadReading.Max);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
                Log.e(TAG, "onCancelled: Failed to read message");

                ssdCurrentTempReading.setText("");
                ssdMaxTempReading.setText("");
            }
        };
        cpuLoadReference.addValueEventListener(cpuloadListener);

        // copy for removing at onStop()
        cpuLoadListener = cpuloadListener;


        //GPU Load Readings from FireBase
        ValueEventListener gpuloadListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    gpuLoadReading gpuLoadReading = dataSnapshot.getValue(gpuLoadReading.class);

                    // Log.e(TAG, "onDataChange: Message data is updated: " + cpuTempReading.Value + ", " + ", ");

                    gpuCurrentLoadReading.setText(gpuLoadReading.Value);
                    gpuMaxLoadReading.setText(gpuLoadReading.Max);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
                Log.e(TAG, "onCancelled: Failed to read message");

                gpuCurrentLoadReading.setText("");
                gpuMaxLoadReading.setText("");
            }
        };
        gpuLoadReference.addValueEventListener(gpuloadListener);

        // copy for removing at onStop()
        gpuLoadListener = gpuloadListener;
//End of Computer Load Reading Section


        //Computer Name from FireBase
        ValueEventListener computernameListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    computerNameReading computerNameReading = dataSnapshot.getValue(computerNameReading.class);

                    // Log.e(TAG, "onDataChange: Message data is updated: " + cpuTempReading.Value + ", " + ", ");

                    computerName.setText(computerNameReading.Text);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
                Log.e(TAG, "onCancelled: Failed to read message");

                computerName.setText("Not Found");
            }
        };
        computerNameReference.addValueEventListener(computernameListener);

        // copy for removing at onStop()
        computerNameListener = computernameListener;


        //Motherboard name from FireBase
        ValueEventListener motherboardnameListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    motherboardNameReading motherboardNameReading = dataSnapshot.getValue(motherboardNameReading.class);

                    // Log.e(TAG, "onDataChange: Message data is updated: " + cpuTempReading.Value + ", " + ", ");

                    motherboardName.setText(motherboardNameReading.Text);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
                Log.e(TAG, "onCancelled: Failed to read message");

                motherboardName.setText("Not Found");
            }
        };
        motherboardNameReference.addValueEventListener(motherboardnameListener);

        // copy for removing at onStop()
        motherboardNameListener = motherboardnameListener;


        //CPU name from FireBase
        ValueEventListener cpunameListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    cpuNameReading cpuNameReading = dataSnapshot.getValue(cpuNameReading.class);

                    // Log.e(TAG, "onDataChange: Message data is updated: " + cpuTempReading.Value + ", " + ", ");

                    cpuName.setText(cpuNameReading.Text);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
                Log.e(TAG, "onCancelled: Failed to read message");

                cpuName.setText("Not Found");
            }
        };
        cpuNameReference.addValueEventListener(cpunameListener);

        // copy for removing at onStop()
        cpuNameListener = cpunameListener;


        //CPU name from FireBase
        ValueEventListener gpunameListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    gpuNameReading gpuNameReading = dataSnapshot.getValue(gpuNameReading.class);

                    // Log.e(TAG, "onDataChange: Message data is updated: " + cpuTempReading.Value + ", " + ", ");

                    gpuName.setText(gpuNameReading.Text);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
                Log.e(TAG, "onCancelled: Failed to read message");

                gpuName.setText("Not Found");
            }
        };
        gpuNameReference.addValueEventListener(gpunameListener);

        // copy for removing at onStop()
        gpuNameListener = gpunameListener;




    }


    public void AddCPUData(String newEntry,String newEntry2) {
        boolean insertData = mDatabaseHelper.addCPUData(newEntry,newEntry2);

        if (insertData) {
            toastMessage("Data Successfully Inserted!");
        } else {
            toastMessage("Something went wrong");
        }
    }

    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }



//        ValueEventListener messageListener2 = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//
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

