package com.example.vladislavsvasiljevs.pchub.charts;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.vladislavsvasiljevs.pchub.DatabaseHelpers.DatabaseHelperCPU;
import com.example.vladislavsvasiljevs.pchub.DatabaseHelpers.DatabaseHelperGPU;
import com.example.vladislavsvasiljevs.pchub.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class cpuLineChart extends AppCompatActivity {

    DatabaseHelperCPU mDatabaseHelperCPU;
    DatabaseHelperGPU mDatabaseHelperGPU;
    SQLiteDatabase db, db2;
    Button showUpdateButton;
    LineChart lineChart;
    LineChart lineChart2;
    LineDataSet lineDataSet = new LineDataSet(null, null);
    LineDataSet lineDataSet2 = new LineDataSet(null, null);
    ArrayList<ILineDataSet> dataSets = new ArrayList<>();
    ArrayList<ILineDataSet> dataSets2 = new ArrayList<>();
    LineData lineData;
    LineData lineData2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpu_line_chart);

        mDatabaseHelperCPU = new DatabaseHelperCPU(this);
        mDatabaseHelperGPU = new DatabaseHelperGPU(this);
        db = mDatabaseHelperCPU.getWritableDatabase();
        db2 = mDatabaseHelperGPU.getWritableDatabase();

        showUpdateButton = findViewById(R.id.updateShowButton);
        lineChart = findViewById(R.id.mp_LineChart);
        lineChart2 = findViewById(R.id.mp_LineChart1);

        showChart();
        genChart();


    }


    private void genChart() {
        lineDataSet.setValues(tableData());
        lineDataSet.setLabel("Processor Temperature (CPU)");
        dataSets.clear();
        dataSets.add(lineDataSet);
        lineData = new LineData(dataSets);
        lineChart.clear();
        lineChart.setData(lineData);
        lineChart.invalidate();


        lineDataSet2.setValues(tableData2());
        lineDataSet2.setLabel("Graphics Processor Temperature (GPU)");
        dataSets2.clear();
        dataSets2.add(lineDataSet2);
        lineData2 = new LineData(dataSets2);
        lineChart2.clear();
        lineChart2.setData(lineData2);
        lineChart2.invalidate();
    }

    //Button which shows and updates the chart
    private void showChart() {
        showUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lineDataSet.setValues(tableData());
                lineDataSet.setLabel("Processor Temperature (CPU)");
                dataSets.clear();
                dataSets.add(lineDataSet);
                lineData = new LineData(dataSets);
                lineChart.clear();
                lineChart.setData(lineData);
                lineChart.invalidate();

                lineDataSet2.setValues(tableData2());
                lineDataSet2.setLabel("Graphics Processor Temperature (GPU)");
                dataSets2.clear();
                dataSets2.add(lineDataSet2);
                lineData2 = new LineData(dataSets2);
                lineChart2.clear();
                lineChart2.setData(lineData2);
                lineChart2.invalidate();

            }
        });
    }


    //Arraylist that gets out data from the table
    private ArrayList<Entry> tableData() {
        ArrayList<Entry> dataVals = new ArrayList<>();
        String[] colums = {"ID", "DATE", "CPU_Temp_Value"};
        Cursor cursor = db.query("cpu_temp", colums, null, null, null, null, null, null);

        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();
            dataVals.add(new Entry(cursor.getFloat(0), cursor.getFloat(2)));
        }
        return dataVals;
    }


    //Arraylist that gets out data from the table
    private ArrayList<Entry> tableData2() {
        ArrayList<Entry> dataVals = new ArrayList<Entry>();
        String[] colums = {"ID", "DATE", "GPU_Temp_Value"};
        Cursor cursor = db2.query("gpu_temp", colums, null, null, null, null, null, null);

        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();
            dataVals.add(new BarEntry(cursor.getFloat(0), cursor.getFloat(2)));
        }
        return dataVals;
    }

}
