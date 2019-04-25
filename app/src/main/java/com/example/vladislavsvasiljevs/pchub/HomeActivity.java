package com.example.vladislavsvasiljevs.pchub;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.time.OffsetDateTime;


public class HomeActivity extends AppCompatActivity {
    public static boolean isAppRunning;
    //GridLayout mainGrid;

    private DatabaseReference databaseReference;//Firebase reference
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {

                            return;
                        }


                        String token = task.getResult().getToken();
                        String msg = getString(R.string.fcm_token, token);
                        Log.d(TAG, msg);

                    }
                });


        databaseReference = FirebaseDatabase.getInstance().getReference();

        offButtonGONE();//By default the turn off button is set to GONE, When On button clicked, then this button will appear

        goToComputerStats();//Calling method to bring us to Computer Stat screen
        goToLightControl();//Calling method to bring us to Light control screen
        goToFanControl();//Calling method to bring us to Fan control screen
        goToDashboard();//Calling method to bring us to Dashboard screen
        computerOnButton();//Calling method to trigger button
        computerOffButton();//Calling method to trigger button
    }

    private void computerOnButton() {//Method that turns on the computer
        Button computerOn = findViewById(R.id.greenBtn);//Assigning button to computerOn
        computerOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogOnButton();//Calling alert dialog method for on button
            }
        });
    }

    private void computerOffButton() {//Method that turns off the computer
        Button computerOff = findViewById(R.id.redBtn);//Assigning a button to computer off
        computerOff.setOnClickListener(new View.OnClickListener() {//Creating a click listener
            @Override
            public void onClick(View view) {
                alertDialogOffButton();//Calling alert dialog method for on button
            }
        });
    }

    private void goToComputerStats() {//Method that brings us to Computer stat screen
        CardView computerStat = findViewById(R.id.computerStatCardView);//computerStat equals computerStatCardView
        computerStat.setOnClickListener(new View.OnClickListener() {//Creating a click listener
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, computerStatActivity.class);
                intent.putExtra("info", "This is activity from card item index  ");
                startActivity(intent);

            }
        });

    }

    private void goToLightControl() {//Method that brings us to Computer stat screen
        CardView lightControl = findViewById(R.id.lightControlCardView);//computerStat equals computerStatCardView
        lightControl.setOnClickListener(new View.OnClickListener() {//Creating a click listener
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, lightControlActivity.class);
                intent.putExtra("info", "This is activity from card item index  ");
                startActivity(intent);

            }
        });

    }

    private void goToFanControl() {//Method that brings us to fan control screen
        CardView fanControl = findViewById(R.id.fanControlCardView);//computerStat equals computerStatCardView
        fanControl.setOnClickListener(new View.OnClickListener() {//Creating a click listener
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, fanControlActivity.class);
                intent.putExtra("info", "This is activity from card item index  ");
                startActivity(intent);

            }
        });

    }

    private void goToDashboard() {//Method that brings us to dashboard scree    n
        CardView dashboard = findViewById(R.id.dashboardCardView);//computerStat equals computerStatCardView
        dashboard.setOnClickListener(new View.OnClickListener() {//Creating a click listener
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, dashboardActivity.class);
                intent.putExtra("info", "This is activity from card item index  ");
                startActivity(intent);

            }
        });


    }

    private void offButtonGONE() {//Hiding off button method
        Button computerOff = findViewById(R.id.redBtn);
        computerOff.setVisibility(View.GONE);
    }

    private void offButtonVisible() {//Showing off button method
        Button computerOff = findViewById(R.id.redBtn);
        computerOff.setVisibility(View.VISIBLE);
    }

    private void onButtonVisible() {//Showing on button method
        Button computerOn = findViewById(R.id.greenBtn);
        computerOn.setVisibility(View.VISIBLE);
    }

    private void onButtonGONE() {//Hiding on Button method
        Button computerOn = findViewById(R.id.greenBtn);
        computerOn.setVisibility(View.GONE);
    }

    //This snippet has came from the following webiste: https://www.tutorialspoint.com/how-do-i-display-an-alert-dialog-on-android
//    Some parts where edited by me (Vladislavs Vasiljes) to match my projects requirements
    private void alertDialogOnButton() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("If your computer is already turned on and you wish to turn it off then tap Continue. " +
                "If your computer is turned off but you wish to turn it on then tap Continue. Otherwise tap Cancel.");
        dialog.setTitle("Warning!");
        dialog.setPositiveButton("Continue",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        Toast.makeText(getApplicationContext(), "Yes is clicked", Toast.LENGTH_LONG).show();
                        databaseReference.child("computerControl").setValue(1);
                        onButtonGONE();
                        offButtonVisible();
                    }
                });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "cancel is clicked", Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }

    //This snippet has came from the following webiste: https://www.tutorialspoint.com/how-do-i-display-an-alert-dialog-on-android
//    Some parts where edited by me (Vladislavs Vasiljes) to match my projects requirements
    private void alertDialogOffButton() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("If your computer is already turned on and you wish to turn it off then tap Continue. " +
                "If your computer is turned off but you wish to turn it on then tap Continue. Otherwise tap Cancel.");
        dialog.setTitle("Warning!");
        dialog.setPositiveButton("Continue",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        Toast.makeText(getApplicationContext(), "Yes is clicked", Toast.LENGTH_LONG).show();
                        databaseReference.child("computerControl").setValue(1);
                        onButtonVisible();
                        offButtonGONE();
                    }
                });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "cancel is clicked", Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }
}



