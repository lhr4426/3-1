package com.example.mc_week4_prac;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Matrix;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    CheckBox androidBox, iphoneBox;

    RadioGroup rGroup;

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        androidBox = (CheckBox) findViewById(R.id.androidBox);
        iphoneBox = (CheckBox) findViewById(R.id.iphoneBox);
        rGroup = (RadioGroup) findViewById(R.id.rGroup);
        imageView = (ImageView) findViewById(R.id.imageView);

        androidBox.setOnCheckedChangeListener(myCheckListener);
        iphoneBox.setOnCheckedChangeListener(myCheckListener);

        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        // 기본값을 고양이로 설정했으니까 사진도 고양이
        imageView.setImageResource(R.drawable.cat2);

        Matrix matrix = imageView.getImageMatrix();
        float scale = 2.0f;
        matrix.setScale(scale,scale);
        imageView.setImageMatrix(matrix);

        // 인자로 라디오 그룹이랑 체크된 id를 받음.
        rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                StringBuilder mStr = new StringBuilder();

                if (i == R.id.cat) {
                    mStr.append("Cat selected");
                    imageView.setImageResource(R.drawable.cat2);
                } else {
                    mStr.append("Dog selected");
                    imageView.setImageResource(R.drawable.dog3);
                }

                Toast.makeText(getApplicationContext(), mStr, Toast.LENGTH_SHORT).show();
            }
        });
    }


    // 체크박스랑 라디오그룹은 OnCheckedChangeListener 씀 (근데 상위가 다름)
    CompoundButton.OnCheckedChangeListener myCheckListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            StringBuilder mStr = new StringBuilder();

            if (compoundButton.getId() == R.id.androidBox){
                mStr.append(androidBox.getText().toString());
            }
            else {
                mStr.append(iphoneBox.getText().toString());
            }

            if (b) {
                mStr.append(" " + "checked");
            }
            else {
                mStr.append(" " + "unchecked");
            }
            Toast.makeText(getApplicationContext(), mStr, Toast.LENGTH_SHORT).show();
        }
    };



}