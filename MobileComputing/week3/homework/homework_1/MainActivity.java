package com.example.mc_week3_homework;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.lang.Math;

public class MainActivity extends AppCompatActivity {

    TextView textview;
    Button num1, num2, num3, num4, num5, num6, num7, num8, num9, num0, numDot,
            AC, square, modulo, divide, multiply, sub, add, equal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textview = (TextView) findViewById(R.id.textview);
        num1 = (Button) findViewById(R.id.num1);
        num1.setOnClickListener(new BtnListener());
        num2 = (Button) findViewById(R.id.num2);
        num2.setOnClickListener(new BtnListener());
        num3 = (Button) findViewById(R.id.num3);
        num3.setOnClickListener(new BtnListener());
        num4 = (Button) findViewById(R.id.num4);
        num4.setOnClickListener(new BtnListener());
        num5 = (Button) findViewById(R.id.num5);
        num5.setOnClickListener(new BtnListener());
        num6 = (Button) findViewById(R.id.num6);
        num6.setOnClickListener(new BtnListener());
        num7 = (Button) findViewById(R.id.num7);
        num7.setOnClickListener(new BtnListener());
        num8 = (Button) findViewById(R.id.num8);
        num8.setOnClickListener(new BtnListener());
        num9 = (Button) findViewById(R.id.num9);
        num9.setOnClickListener(new BtnListener());
        num0 = (Button) findViewById(R.id.num0);
        num0.setOnClickListener(new BtnListener());
        numDot = (Button) findViewById(R.id.numDot);
        numDot.setOnClickListener(new BtnListener());
        AC = (Button) findViewById(R.id.AC);
        AC.setOnClickListener(new BtnListener());
        square = (Button) findViewById(R.id.square);
        square.setOnClickListener(new BtnListener());
        modulo = (Button) findViewById(R.id.modulo);
        modulo.setOnClickListener(new BtnListener());
        divide = (Button) findViewById(R.id.divide);
        divide.setOnClickListener(new BtnListener());
        multiply = (Button) findViewById(R.id.multiply);
        multiply.setOnClickListener(new BtnListener());
        sub = (Button) findViewById(R.id.sub);
        sub.setOnClickListener(new BtnListener());
        add = (Button) findViewById(R.id.add);
        add.setOnClickListener(new BtnListener());
        equal = (Button) findViewById(R.id.equal);
        equal.setOnClickListener(new BtnListener());
    }

    class BtnListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            int vid = view.getId();
            StringBuilder strBuilder = new StringBuilder();

            // 클릭 된 버튼 객체 알아오기
            String clickedBtn = ((Button)view).getText().toString();
            // 결과 저장할 변수 생성
            Double resultNumber = null;

            // 눌린 버튼이 = 이면
            if(vid == R.id.equal) {
                TextView textView = (TextView)findViewById(R.id.textview);
                String resultString = textView.getText().toString();
                // 혹시 모를 앞뒤 공백 제거
                resultString.trim();
                // 공백 기준 문자 자르기
                String[] resultStringArr = resultString.split(" ");
                Double[] numbers = new Double[2];
                // 개수가 안맞으면 parse부터 오류발생
                // 그래서 여기서부터 try-catch
                try{
                    numbers[0] = Double.parseDouble(resultStringArr[0]);
                    String operator = resultStringArr[1];
                    numbers[1] = Double.parseDouble(resultStringArr[2]);
                    // 스위치 사용해서 연산 어떻게 할지 판단 (나누기랑 나머지는 numbers[1] == 0이면 ERROR)
                    switch(operator) {
                        case "+" :
                            resultNumber = numbers[0] + numbers[1];
                            textView.setText(resultString + " = " +(String)resultNumber.toString());
                            break;
                        case "-" :
                            resultNumber = numbers[0] - numbers[1];
                            textView.setText(resultString + " = " +(String)resultNumber.toString());
                            break;
                        case "*" :
                            resultNumber = numbers[0] * numbers[1];
                            textView.setText(resultString + " = " +(String)resultNumber.toString());
                            break;
                        case "/" :
                            if (numbers[1] != 0){
                                resultNumber = numbers[0] / numbers[1];
                                textView.setText(resultString + " = " +(String)resultNumber.toString());
                            }
                            else {
                                textView.setText("ERROR");
                            }
                            break;
                        case "%" :
                            if (numbers[1] != 0){
                                resultNumber = numbers[0] % numbers[1];
                                textView.setText(resultString + " = " +(String)resultNumber.toString());
                            }
                            else {
                                textView.setText("ERROR");
                            }
                            break;
                        case "**" :
                            resultNumber = Math.pow(numbers[0], numbers[1]);
                            textView.setText(resultString + " = " +(String)resultNumber.toString());
                            break;
                    }

                }
                catch (Exception e){
                    textView.setText("ERROR");
                }
            }
            // 눌린 버튼이 AC 이면
            else if(vid == (R.id.AC)) {
                TextView textView = (TextView)findViewById(R.id.textview);
                // testView를 전부 지움
                textView.setText("");
            }
            // 눌린 버튼이 연산자면
            else if(vid == (R.id.modulo) || vid == (R.id.square) || vid == (R.id.divide) ||
                    vid == (R.id.multiply) || vid == (R.id.sub) || vid == (R.id.add)) {
                TextView textView = (TextView)findViewById(R.id.textview);
                String textViewText = textView.getText().toString();
                // 나중에 split으로 떼어내기 편하게 숫자와 연산자 사이에는 공백을 두었음
                strBuilder.append(textViewText).append(" ").append(clickedBtn).append(" ");
                textView.setText((String)strBuilder.toString());
            }
            else if(vid == (R.id.numDot)){
                TextView textView = (TextView)findViewById(R.id.textview);
                String textViewText = textView.getText().toString();
                strBuilder.append(textViewText).append(".");
                textView.setText((String)strBuilder.toString());
            }
            // 눌린 버튼이 숫자면
            else {
                TextView textView = (TextView)findViewById(R.id.textview);
                String textViewText = textView.getText().toString();
                strBuilder.append(textViewText).append(clickedBtn);
                textView.setText((String)strBuilder.toString());
            }
        }
    }
}