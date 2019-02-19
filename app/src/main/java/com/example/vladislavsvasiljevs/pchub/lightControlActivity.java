package com.example.vladislavsvasiljevs.pchub;

import android.app.DownloadManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.util.Hex;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import top.defaults.colorpicker.ColorPickerView;

public class lightControlActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String SAVED_STATE_KEY_COLOR = "saved_state_key_color";
    private static final int INITIAL_COLOR = 0xFF1A86FF;//Default color when the screen turns on
    private DatabaseReference databaseReference;


    @BindView(R.id.colorPicker)
    ColorPickerView colorPickerView;
    @BindView(R.id.colorHex)
    TextView colorHex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_control);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        //Assigning variables to colour buttons located at the top of the view
        //Also creating onClickListeners for those buttons
        Button whiteColourBtn = findViewById(R.id.whiteColourBtn);
        Button redColourBtn = findViewById(R.id.redColourBtn);
        Button orangeColourBtn = findViewById(R.id.orangeColourBtn);
        whiteColourBtn.setOnClickListener(this);
        redColourBtn.setOnClickListener(this);
        orangeColourBtn.setOnClickListener(this);

        Button yellowColourBtn = findViewById(R.id.yellowColourBtn);
        Button cyanColourBtn = findViewById(R.id.cyanColourBtn);
        Button blueColourBtn = findViewById(R.id.blueColourBtn);
        yellowColourBtn.setOnClickListener(this);
        cyanColourBtn.setOnClickListener(this);
        blueColourBtn.setOnClickListener(this);

        Button purpleColourBtn = findViewById(R.id.purpleColourBtn);
        Button pinkColourBtn = findViewById(R.id.pinkColourBtn);
        Button greenColourBtn = findViewById(R.id.greenColourBtn);
        purpleColourBtn.setOnClickListener(this);
        pinkColourBtn.setOnClickListener(this);
        greenColourBtn.setOnClickListener(this);

        Button lightsOn = findViewById(R.id.lightsOn);
        Button lightsOff = findViewById(R.id.lightsOff);
        lightsOn.setOnClickListener(this);
        lightsOff.setOnClickListener(this);

//        whiteColourBtn.setOnClickListener(v -> {
//            databaseReference.child("Red").setValue(255);
//            databaseReference.child("Blue").setValue(255);
//            databaseReference.child("Green").setValue(255);
//            databaseReference.child("Brightness").setValue(255);
//        });

        ButterKnife.bind(this);

        colorPickerView.subscribe((color, fromUser) -> {
            //pickedColor.setBackgroundColor(color);
            colorHex.setText(colorHex(color));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(color);
            }
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setBackgroundDrawable(new ColorDrawable(color));
            }
        });

        int color = INITIAL_COLOR;
        if (savedInstanceState != null) {
            color = savedInstanceState.getInt(SAVED_STATE_KEY_COLOR, INITIAL_COLOR);
        }
        colorPickerView.setInitialColor(color);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_STATE_KEY_COLOR, colorPickerView.getColor());
    }

    //Converting Hex to RGB and then passing the value to firebase so we can control the LED lights
    private String colorHex(int color) {
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        int a = Color.alpha(color);
        //If the RGBA values are equal to the values in the if statement, then we do nothing until those values are changed
        if (r == 26 && g == 134 && b == 255 && a == 255) {
        } else {
            databaseReference.child("Red").setValue(r);
            databaseReference.child("Green").setValue(g);
            databaseReference.child("Blue").setValue(b);
            databaseReference.child("Brightness").setValue(a);


        }
        return String.format(Locale.getDefault(), "%03d%03d%03d%03d", r, g, b, a);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.whiteColourBtn:
                databaseReference.child("Red").setValue(255);
                databaseReference.child("Blue").setValue(255);
                databaseReference.child("Green").setValue(255);
                databaseReference.child("Brightness").setValue(255);
                break;

            case R.id.redColourBtn:
                databaseReference.child("Red").setValue(255);
                databaseReference.child("Green").setValue(0);
                databaseReference.child("Blue").setValue(0);
                databaseReference.child("Brightness").setValue(255);
                break;

            case R.id.orangeColourBtn:
                databaseReference.child("Red").setValue(255);
                databaseReference.child("Green").setValue(165);
                databaseReference.child("Blue").setValue(0);
                databaseReference.child("Brightness").setValue(255);
                break;

            case R.id.yellowColourBtn:
                databaseReference.child("Red").setValue(255);
                databaseReference.child("Green").setValue(255);
                databaseReference.child("Blue").setValue(0);
                databaseReference.child("Brightness").setValue(255);
                break;

            case R.id.cyanColourBtn:
                databaseReference.child("Red").setValue(0);
                databaseReference.child("Green").setValue(255);
                databaseReference.child("Blue").setValue(255);
                databaseReference.child("Brightness").setValue(255);
                break;

            case R.id.blueColourBtn:
                databaseReference.child("Red").setValue(0);
                databaseReference.child("Green").setValue(0);
                databaseReference.child("Blue").setValue(255);
                databaseReference.child("Brightness").setValue(255);
                break;

            case R.id.purpleColourBtn:
                databaseReference.child("Red").setValue(128);
                databaseReference.child("Green").setValue(0);
                databaseReference.child("Blue").setValue(128);
                databaseReference.child("Brightness").setValue(255);
                break;

            case R.id.pinkColourBtn:
                databaseReference.child("Red").setValue(255);
                databaseReference.child("Green").setValue(192);
                databaseReference.child("Blue").setValue(203);
                databaseReference.child("Brightness").setValue(255);
                break;

            case R.id.greenColourBtn:
                databaseReference.child("Red").setValue(0);
                databaseReference.child("Green").setValue(128);
                databaseReference.child("Blue").setValue(0);
                databaseReference.child("Brightness").setValue(255);
                break;

            case R.id.lightsOn:
                databaseReference.child("Red").setValue(255);
                databaseReference.child("Green").setValue(255);
                databaseReference.child("Blue").setValue(255);
                databaseReference.child("Brightness").setValue(255);
                break;

            case R.id.lightsOff:
                databaseReference.child("Red").setValue(0);
                databaseReference.child("Green").setValue(0);
                databaseReference.child("Blue").setValue(0);
                databaseReference.child("Brightness").setValue(0);
                break;

            default:
                break;
        }

    }
}

//    //Converting RGB to Hex
//    private String colorHex2(int color) {
//        int a = Color.alpha(color);
//        int r = Color.red(color);
//        int g = Color.green(color);
//        int b = Color.blue(color);
//        String red = String.format(Locale.getDefault(), "02X%02X%02X%02X", a, r, g, b);
//        return red;
//    }

//    @Override
//    public void onClick(View v) {
//
//    }
