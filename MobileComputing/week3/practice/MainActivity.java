package com.example.mc_week3_prac;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    // 생성 자체는 onCreate 밖에서 만들어도 됨. 연결은 안에서 해야함
    Button button1, button2, button4, button5;
    TextView textview;

    EditText ed1, ed2;

    String num1, num2;
    Integer result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 참고 : 위젯을 변수와 연결시키려면 무조건 setContentView 이후에 해야함. 보여야지 연결하니까!!
        textview = (TextView) findViewById(R.id.textview);

        // EditText는 기본적으로 String으로 가져옴. 그래서 더하기 할때는 Integer로 바꿔줘야함
        ed1 = (EditText) findViewById(R.id.editText1);
        ed2 = (EditText) findViewById(R.id.editText2);


        button1 = (Button) findViewById(R.id.button1); // 뷰에서 아이디로 찾아서 연결하기
        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "더하기", Toast.LENGTH_SHORT).show();
                // textview.setText("버튼 1번을 누르셨군요");

                num1 = ed1.getText().toString();
                num2 = ed2.getText().toString();

                result = Integer.parseInt(num1) + Integer.parseInt(num2);
                textview.setText("결과 : " + result.toString());
            }
        }); // 클릭했을 때 실행되는걸 설정. 여기서는 익명클래스로 설정함


        // 버튼2 : 익명클래스를 만들어서 연결하기
        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "버튼2을 눌렀어요", Toast.LENGTH_SHORT).show();
            }
        });

        // implements로 onClickListener를 받은 다음에 그걸 구현해버리면 this로도 쓸 수 있음
        // 근데 그렇게 잘 안씀.
        button4 = (Button) findViewById(R.id.button4);
        button4.setOnClickListener(this);

        button5 = (Button) findViewById(R.id.button5);
        button5.setOnClickListener(new MyOnClick());
    }

    // 버튼 3 : XML에서 지정한 onClick을 만들기 (onCreate 안에 하면 안됨)
    public void btn3Click(View v) {
        Toast.makeText(getApplicationContext(), "버튼3을 눌렀어요", Toast.LENGTH_SHORT).show();
    }

    // 버튼4 : MainActivity 자체에 인터페이스를 상속하고, this로 받는방법

    @Override
    public void onClick(View view) {
        Toast.makeText(getApplicationContext(), "버튼4를 눌렀어요", Toast.LENGTH_SHORT).show();
    }

    // 버튼5 : 내부에 클래스를 만들고, View.OnClickListener 인터페이스를 상속하는 방법
    class MyOnClick implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Toast.makeText(getApplicationContext(), "버튼5를 눌렀어요", Toast.LENGTH_SHORT).show();
        }
    }
}