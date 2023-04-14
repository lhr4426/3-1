package com.example.mc_week6_homework1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.SeekBar;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    DatePicker datePicker;
    SeekBar seekBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        seekBar = (SeekBar) findViewById(R.id.seekBar);

        // 마지막 날짜 찾기 위해 사용하는 Calendar
        Calendar cal = Calendar.getInstance();

        DatePicker.OnDateChangedListener dateChangedListener = new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                // cal의 현재 날짜를 설정함
                // i1을 그대로 넣은 이유 : Calendar 클래스에서 사용하는 날짜도
                //                      월을 -1해서 사용함 (= DatePicker랑 같은 방식임)
                cal.set(i, i1, i2);
                // getActualMaximum은 캘린더가 정해진 날짜(set)에서 최대로 가질 수 있는 값을 반환
                // 인자가 Calendar.DAY_OF_MONTH 니까 해당 달의 가장 마지막 날의 날짜를 반환함
                int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
                seekBar.setMax(lastDay); // 최대 값을 마지막 날짜로 설정
                seekBar.setProgress(i2); // 현재 값을 선택한 날짜로 설정
            }
        };

        datePicker.setOnDateChangedListener(dateChangedListener);
    }
}