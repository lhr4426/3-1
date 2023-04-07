package com.example.mc_week6_prac;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Chronometer;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    Chronometer chrono1;
    Button startBtn, endBtn;
    CalendarView calendar1;
    TextView textView;
    DatePicker datepicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chrono1 = (Chronometer) findViewById(R.id.chrono1);
        startBtn = (Button) findViewById(R.id.startBtn);
        endBtn = (Button) findViewById(R.id.endBtn);
//        calendar1 = (CalendarView) findViewById(R.id.calendar1);
        textView = (TextView) findViewById(R.id.textView);

        CalendarView.OnDateChangeListener mDateChangeListener = new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                // 왜인지는 모르겠으나 월이 0부터 시작하니까 1을 더해줘야 한다는 점을 잊지 말것...
                textView.setText(new String().format("year %d: month:%d, day:%d", i, i1+1, i2));
            }
        };

//        calendar1.setOnDateChangeListener(mDateChangeListener);

        datepicker = (DatePicker) findViewById(R.id.datepicker);

        DatePicker.OnDateChangedListener mDateChangeListener2 = new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                textView.setText(new String().format("year %d: month:%d, day:%d", i, i1+1, i2));

            }
        };

        datepicker.setOnDateChangedListener(mDateChangeListener2);

//        TimePicker.OnTimeChangedListener timeChangedListener = new TimePicker.OnTimeChangedListener() {
//            @Override
//            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
//                // 타임피커는 24시간 체제이다
//                textView.setText(new String().format("hour : %d, minute : %d", i, i1));
//            }
//        };



    }

    public void chronoStartClick(View v){
        chrono1.setBase(SystemClock.elapsedRealtime());
        chrono1.setTextColor(Color.BLACK);
        chrono1.start();
    }

    public void chronoEndClick(View v){
        chrono1.stop();
        chrono1.setTextColor(Color.RED);
    }
}