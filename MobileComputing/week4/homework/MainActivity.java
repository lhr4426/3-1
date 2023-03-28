package com.example.mc_week4_homework;

import androidx.appcompat.app.AppCompatActivity;


import android.graphics.Matrix;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    CheckBox check_idle, check_seventeen;
    RadioGroup group_idle, group_seventeen, group_imageView;
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        check_idle = (CheckBox) findViewById(R.id.check_idle);
        check_seventeen = (CheckBox) findViewById(R.id.check_seventeen);

        group_idle = (RadioGroup) findViewById(R.id.group_idle);
        group_seventeen = (RadioGroup) findViewById(R.id.group_seventeen);
        group_imageView = (RadioGroup) findViewById(R.id.group_imageView);

        imageView = (ImageView) findViewById(R.id.imageView);

        // 기본 값을 FIT_CENTER로 설정
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);

        check_idle.setOnCheckedChangeListener(checkBoxListener);
        check_seventeen.setOnCheckedChangeListener(checkBoxListener);

        group_idle.setOnCheckedChangeListener(idolGroupListener);
        group_seventeen.setOnCheckedChangeListener(idolGroupListener);
        group_imageView.setOnCheckedChangeListener(scaleGroupListener);


    }

    // 아이돌 그룹 선택에 대한 리스너
    CompoundButton.OnCheckedChangeListener checkBoxListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            // 각 아이돌 그룹에 맞는 체크박스가 선택될 때만 Visible로 설정
            // 체크 풀리면 Gone으로 설정
            if(compoundButton.getId() == R.id.check_idle) {
                if(b)
                    group_idle.setVisibility(RadioGroup.VISIBLE);
                else{
                    group_idle.setVisibility(RadioGroup.GONE);
                    // 무한루프 방지용 리스너 연결 해제
                    group_idle.setOnCheckedChangeListener(null);
                    group_idle.clearCheck();
                    group_idle.setOnCheckedChangeListener(idolGroupListener);
                }
            }
            else {
                if(b)
                    group_seventeen.setVisibility(RadioGroup.VISIBLE);
                else{
                    group_seventeen.setVisibility(RadioGroup.GONE);
                    // 무한루프 방지용 리스너 연결 해제
                    group_seventeen.setOnCheckedChangeListener(null);
                    group_seventeen.clearCheck();
                    group_seventeen.setOnCheckedChangeListener(idolGroupListener);
                }
            }
        }
    };

    // 아이돌 멤버 선택에 대한 리스너
    RadioGroup.OnCheckedChangeListener idolGroupListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            // 만약 아이들의 멤버를 선택했을 때
            if(radioGroup.getId() == R.id.group_idle){
                // 무한루프 해결하기 위해 리스너 끊고 체크 풀고 다시 리스너 연결
                if(check_seventeen.isChecked()){
                    group_seventeen.setOnCheckedChangeListener(null);
                    group_seventeen.clearCheck();
                    group_seventeen.setOnCheckedChangeListener(idolGroupListener);
                }

                if(i == R.id.btn_miyeon)
                    imageView.setImageResource(R.drawable.miyeon);
                else if(i == R.id.btn_minnie)
                    imageView.setImageResource(R.drawable.minnie);
                else if(i == R.id.btn_soyeon)
                    imageView.setImageResource(R.drawable.soyeon);
                else if(i == R.id.btn_yuqi)
                    imageView.setImageResource(R.drawable.yuqi);
                else if(i == R.id.btn_shuhua)
                    imageView.setImageResource(R.drawable.shuhua);
            }

            // 세븐틴의 멤버를 선택했을 때
            else if(radioGroup.getId() == R.id.group_seventeen){
                if(check_idle.isChecked()){
                    group_idle.setOnCheckedChangeListener(null);
                    group_idle.clearCheck();
                    group_idle.setOnCheckedChangeListener(idolGroupListener);
                }
                if(i == R.id.btn_seungkwan)
                    imageView.setImageResource(R.drawable.seungkwan);
                else if(i == R.id.btn_dk)
                    imageView.setImageResource(R.drawable.dk);
                else if(i == R.id.btn_hoshi)
                    imageView.setImageResource(R.drawable.hoshi);
            }
        }
    };

    RadioGroup.OnCheckedChangeListener scaleGroupListener = new RadioGroup.OnCheckedChangeListener(){
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            if(i == R.id.btn_fitCenter)
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            else if(i == R.id.btn_Matrix){
                // 일단 스케일타입을 매트릭스로 설정
                imageView.setScaleType(ImageView.ScaleType.MATRIX);
                // 그런 다음 매트릭스 구해오기
                Matrix matrix = imageView.getImageMatrix();
                // 이미지뷰의 매트릭스를 방금 구해온 매트릭스로 사용
                imageView.setImageMatrix(matrix);
                // 매트릭스의 스케일 변경
                matrix.setScale(2.0f,2.0f);
            }
            else if(i == R.id.btn_fitXY)
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            else
                imageView.setScaleType(ImageView.ScaleType.CENTER);
        }
    };


}