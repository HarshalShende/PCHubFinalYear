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

import com.example.vladislavsvasiljevs.pchub.DatabaseHelpers.DatabaseHelperCPUFreq;

import com.example.vladislavsvasiljevs.pchub.HomeActivity;
import com.example.vladislavsvasiljevs.pchub.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class cpuFreqLineChart extends AppCompatActivity {

    DatabaseHelperCPUFreq mDatabaseHelperCPUFreq;
    SQLiteDatabase db;
    Button showUpdateButton;
    LineChart lineChart;
    Button ExportPlusdeleteData;
    LineDataSet lineDataSet = new LineDataSet(null, null);
    ArrayList<ILineDataSet> dataSets = new ArrayList<>();
    LineData lineData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpu_freq_line_chart);

        mDatabaseHelperCPUFreq = new DatabaseHelperCPUFreq(this);
        db = mDatabaseHelperCPUFreq.getWritableDatabase();
        showUpdateButton = findViewById(R.id.updateShowButton);
        lineChart = findViewById(R.id.mp_LineChart1);
        ExportPlusdeleteData = findViewById(R.id.ExportPlusDeleteData);

        showChart();
        genChart();
        deleteData();
    }


    private void genChart() {
        lineDataSet.setValues(tableData());
        lineDataSet.setLabel("Processor Frequency");
        dataSets.clear();
        dataSets.add(lineDataSet);
        lineData = new LineData(dataSets);
        lineChart.clear();
        lineChart.setData(lineData);
        lineChart.invalidate();
    }

    //Button which shows and updates the chart
    private void showChart() {
        showUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lineDataSet.setValues(tableData());
                lineDataSet.setLabel("Processor Frequency");
                dataSets.clear();
                dataSets.add(lineDataSet);
                lineData = new LineData(dataSets);
                lineChart.clear();
                lineChart.setData(lineData);
                lineChart.invalidate();
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
        File backupDB = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "cpu_freq " + formattedDate);//giving the file a name and date/timestamp
        File currentDB = getApplicationContext().getDatabasePath("cpu_freq");
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
        dialog.setMessage("You are about to delete " + mDatabaseHelperCPUFreq.getDatabaseName() + " table. Would you like to save the table to your downloads folder");
        dialog.setTitle("Warning!");
        dialog.setPositiveButton("Yes save, and delete",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        Toast.makeText(getApplicationContext(), "Yes is clicked", Toast.LENGTH_LONG).show();
                        try {
                            saveSQLiteToDownloads();
                            mDatabaseHelperCPUFreq.deleteAll();
                            startActivity(new Intent(cpuFreqLineChart.this, HomeActivity.class));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
        dialog.setNegativeButton("No don't save, just delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mDatabaseHelperCPUFreq.deleteAll();
                startActivity(new Intent(cpuFreqLineChart.this, HomeActivity.class));
            }
        });
        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }


    //Arraylist that gets out data from the table
    private ArrayList<Entry> tableData() {
        ArrayList<Entry> dataVals = new ArrayList<>();
        String[] colums = {"ID", "DATE", "CPU_Freq_Value"};
        Cursor cursor = db.query("cpu_freq", colums, null, null, null, null, null, null);

        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();
            dataVals.add(new Entry(cursor.getFloat(0), cursor.getFloat(2)));
        }
        return dataVals;
    }
}
