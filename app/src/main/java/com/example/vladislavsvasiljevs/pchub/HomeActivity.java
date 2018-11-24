package com.example.vladislavsvasiljevs.pchub;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class HomeActivity extends AppCompatActivity {
    //GridLayout mainGrid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        goToComputerStats();//Calling method to bring us to Computer Stat screen
        goToLightControl();//Calling method to bring us to Light control screen
        goToFanControl();
        goToDashboard();
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

    private void goToDashboard() {//Method that brings us to dashboard screen
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
}