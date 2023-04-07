package com.example.mc_week6_prac3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class MainActivity extends AppCompatActivity {

    ViewFlipper flipper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flipper = (ViewFlipper) findViewById(R.id.flipper);

        ImageView img = new ImageView(this);
        img.setImageResource(R.drawable.cat6);
        flipper.addView(img);
    }

    public void nextBtnClick(View v){
        flipper.showNext();
    }
    public void previousBtnClick(View v){
        flipper.showPrevious();
    }
}