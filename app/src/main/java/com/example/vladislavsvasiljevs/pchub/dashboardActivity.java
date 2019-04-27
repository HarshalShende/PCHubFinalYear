package com.example.vladislavsvasiljevs.pchub;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;


public class dashboardActivity extends AppCompatActivity {

    DatabaseHelper mDatabaseHelper;
    SQLiteDatabase db;
    Button showUpdateButton;
    Button deleteData;
    BarChart barChart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        mDatabaseHelper = new DatabaseHelper(this);
        db = mDatabaseHelper.getWritableDatabase();
        showUpdateButton = findViewById(R.id.updateShowButton);
        deleteData = findViewById(R.id.deleteData);
        showBtn();
        deleteData();


        barChart = findViewById(R.id.mp_BarChart);

    }

    private void showBtn() {
        showUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BarDataSet barDataSet = new BarDataSet(dataValue1(), "CPU Temp Reading");
                BarData barData = new BarData();
                barData.addDataSet((barDataSet));

                barChart.setData(barData);
                barChart.invalidate();
            }
        });
    }


    //Method that deletes data from cpu_temp table and brings us to HomeActivity
    private void deleteData() {
        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 mDatabaseHelper.deleteAll();
                 startActivity(new Intent(dashboardActivity.this, HomeActivity.class));
            }
        });
    }

    private ArrayList<BarEntry> dataValue1() {
        ArrayList<BarEntry> dataVals = new ArrayList<BarEntry>();
        String[] colums = {"ID", "DATE", "CPU_Temp_Value"};
        Cursor cursor = db.query("cpu_temp", colums, null, null, null, null, null, null);

        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();
            dataVals.add(new BarEntry(cursor.getFloat(0), cursor.getFloat(2)));
        }
        return dataVals;
    }

//    private ArrayList<Entry> getDataValues() {
//        ArrayList<Entry> dataVals = new ArrayList<Entry>();
//        String[] colums = {"CPU_Temp_Value"};
//        Cursor cursor = db.query("cpu_temp", colums, null, null, null, null, null, null);
//
//        for(int i=0; i<cursor.getCount();i++){
//            cursor.moveToNext();
//            dataVals.add(new Entry(cursor.getFloat(0),cursor.getFloat(0)));
//            Log.e(TAG, "onCancelled: Failed to read message"+cursor.getCount());
//        }
//        return dataVals;
//    }





}
