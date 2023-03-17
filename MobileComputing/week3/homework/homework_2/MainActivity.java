package com.example.mc_week3_homework_2;

import androidx.appcompat.app.AppCompatActivity;
import org.mariuszgromada.math.mxparser.*;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText inputFormula;
    Button button;
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputFormula = (EditText) findViewById(R.id.inputFormula);
        button = (Button) findViewById(R.id.button);
        result = (TextView) findViewById(R.id.result);

        // 버튼 하나만 지정해주면 되니까 익명 클래스로 사용해도 무방할 듯
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String formula = inputFormula.getText().toString();
                Expression expression = new Expression(formula);
                result.setText(Double.toString(expression.calculate()));
            }
        });

    }
}