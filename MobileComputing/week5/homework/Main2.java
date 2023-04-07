package com.example.mc_week5_homework;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Main2 extends AppCompatActivity {

    // linearxml, relativexml, constraintxml 확인용 파일

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.linearxml);
        // setContentView(R.layout.relativexml);
        setContentView(R.layout.constraintxml);
    }
}