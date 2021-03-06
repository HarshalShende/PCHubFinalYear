package com.example.vladislavsvasiljevs.pchub.charts;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.vladislavsvasiljevs.pchub.DatabaseHelpers.DatabaseHelperCPU;
import com.example.vladislavsvasiljevs.pchub.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.example.vladislavsvasiljevs.pchub.*;

public class cpuBarChart extends AppCompatActivity {

    DatabaseHelperCPU mDatabaseHelperCPU;
    SQLiteDatabase db;
    Button showUpdateButton;
    Button ExportPlusdeleteData;
    BarChart barChart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpu_bar_chart);

        mDatabaseHelperCPU = new DatabaseHelperCPU(this);
        db = mDatabaseHelperCPU.getWritableDatabase();

        showUpdateButton = findViewById(R.id.updateShowButton);
        ExportPlusdeleteData = findViewById(R.id.ExportPlusDeleteData);
        barChart = findViewById(R.id.mp_BarChart);

        showChart();
        deleteData();
        genChart();


    }

    private void genChart(){
        BarDataSet barDataSet = new BarDataSet(tableData(), "Processor Temperature");
        BarData barData = new BarData();
        barData.addDataSet((barDataSet));
        barChart.setData(barData);
        barChart.invalidate();
    }



    private void showChart() {
        showUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BarDataSet barDataSet = new BarDataSet(tableData(), "Processor Temperature");
                BarData barData = new BarData();
                barData.addDataSet((barDataSet));
                barChart.setData(barData);
                barChart.invalidate();
            }
        });
    }


    //Method which deletes the table, but it also displays a pop up to show us if we want to keep the data or just delete it.
    private void deleteData() {
        ExportPlusdeleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogSaveOrDelete();
            }
        });
    }

    //Method which allows us to save sqlite table to our downloads folder/ file gets saved under the table name + time and date
    public void saveSQLiteToDownloads() throws IOException {
        Date c = Calendar.getInstance().getTime();//Getting time and date
        SimpleDateFormat df = new SimpleDateFormat("dd MM yyyy'_'HH mm ss");//Formatting date and time to be dd/mm/yyyy
        String formattedDate = df.format(c);
        File backupDB = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "cpu_temp " + formattedDate);//giving the file a name and date/timestamp
        File currentDB = getApplicationContext().getDatabasePath("cpu_temp");
        if (currentDB.exists()) {
            FileChannel src = new FileInputStream(currentDB).getChannel();
            FileChannel dst = new FileOutputStream(backupDB).getChannel();
            dst.transferFrom(src, 0, src.size());
            src.close();
            dst.close();
        }
    }

    //Alert dialog that allows us to save and delete the table or just delete it without saving it
    private void alertDialogSaveOrDelete() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("You are about to delete " + mDatabaseHelperCPU.getDatabaseName() + " table. Would you like to save the table to your downloads folder?");
        dialog.setTitle("Warning!");
        dialog.setPositiveButton("Yes save, and delete",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        try {
                            saveSQLiteToDownloads();
                            mDatabaseHelperCPU.deleteAll();
                            startActivity(new Intent(cpuBarChart.this, HomeActivity.class));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
        dialog.setNegativeButton("No don't save, just delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mDatabaseHelperCPU.deleteAll();
                startActivity(new Intent(cpuBarChart.this, HomeActivity.class));
            }
        });
        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }

    //Arraylist that gets out data from the table
    private ArrayList<BarEntry> tableData() {
        ArrayList<BarEntry> dataVals = new ArrayList<BarEntry>();
        String[] colums = {"ID", "DATE", "CPU_Temp_Value"};
        Cursor cursor = db.query("cpu_temp", colums, null, null, null, null, null, null);

        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();
            dataVals.add(new BarEntry(cursor.getFloat(0), cursor.getFloat(2)));
        }
        return dataVals;
    }

}


