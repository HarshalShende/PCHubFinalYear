package com.example.vladislavsvasiljevs.pchub;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.vladislavsvasiljevs.pchub.DatabaseHelpers.DatabaseHelperCPU;
import com.example.vladislavsvasiljevs.pchub.charts.*;


public class dashboardActivity extends ListActivity {

    String classes[] = {"CPU Temperature - Bar Chart", "GPU Temperature - Bar Chart", "SSD Temperature - Bar Chart",
            "CPU VS GPU Temperature - Line Chart"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        setListAdapter(new ArrayAdapter<String>(dashboardActivity.this,
                android.R.layout.simple_list_item_1, classes));


    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);

        if (position == 0) {
            Intent intent = new Intent(this, cpuBarChart.class);
            startActivity(intent);
        } else if (position == 1) {
            Intent intent = new Intent(this, gpuBarChart.class);
            startActivity(intent);
        } else if (position == 2) {
            Intent intent = new Intent(this, ssdBarChart.class);
            startActivity(intent);
        } else if (position == 3) {
            Intent intent = new Intent(this, cpuLineChart.class);
            startActivity(intent);
        }
    }

}