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

        //mainGrid = (GridLayout) findViewById(R.id.mainGrid);

        //Set Event
        goToComputerStats();//Calling method to bring us to Computer Stat screen
    }
    private void goToComputerStats() {//Method that brings us to Computer stat screen
            CardView computerStat = findViewById(R.id.computerStatCardView) ;//computerStat equals computerStatCardView
            computerStat.setOnClickListener(new View.OnClickListener() {//Creating a click listener
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(HomeActivity.this,computerStatActivity.class);
                    intent.putExtra("info","This is activity from card item index  ");
                    startActivity(intent);

                }
            });

    }
}