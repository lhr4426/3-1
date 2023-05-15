package com.example.mc_week10_homework2;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.style.TtsSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageView img_cat_1, img_cat_2, img_cat_3;
    TextView txt_cat_1, txt_cat_2, txt_cat_3;
    Button btn_result;

    private ActivityResultLauncher<Intent> resultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img_cat_1 = (ImageView) findViewById(R.id.img_cat_1);
        img_cat_2 = (ImageView) findViewById(R.id.img_cat_2);
        img_cat_3 = (ImageView) findViewById(R.id.img_cat_3);

        txt_cat_1 = (TextView) findViewById(R.id.txt_cat_1);
        txt_cat_2 = (TextView) findViewById(R.id.txt_cat_2);
        txt_cat_3 = (TextView) findViewById(R.id.txt_cat_3);

        btn_result = (Button) findViewById(R.id.btn_result);

        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode()==RESULT_OK) {
                    int result_cat_1 = result.getData().getIntExtra("cat1", 0);
                    int result_cat_2 = result.getData().getIntExtra("cat2", 0);
                    int result_cat_3 = result.getData().getIntExtra("cat3", 0);

                    txt_cat_1.setText(String.valueOf(result_cat_1));
                    txt_cat_2.setText(String.valueOf(result_cat_2));
                    txt_cat_3.setText(String.valueOf(result_cat_3));

                }
            }
        });

        ImgClick imgClick = new ImgClick();

        img_cat_1.setOnClickListener(imgClick);
        img_cat_2.setOnClickListener(imgClick);
        img_cat_3.setOnClickListener(imgClick);

    }

    public void resultClick(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra("cat1", Integer.parseInt(txt_cat_1.getText().toString()));
        intent.putExtra("cat2", Integer.parseInt(txt_cat_2.getText().toString()));
        intent.putExtra("cat3", Integer.parseInt(txt_cat_3.getText().toString()));

        resultLauncher.launch(intent);
    }

    class ImgClick implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.img_cat_1:
                    Integer txt1 = Integer.parseInt(txt_cat_1.getText().toString());
                    txt_cat_1.setText(String.valueOf(txt1 + 1));
                    break;
                case R.id.img_cat_2:
                    Integer txt2 = Integer.parseInt(txt_cat_2.getText().toString());
                    txt_cat_2.setText(String.valueOf(txt2 + 1));
                    break;
                case R.id.img_cat_3:
                    Integer txt3 = Integer.parseInt(txt_cat_3.getText().toString());
                    txt_cat_3.setText(String.valueOf(txt3 + 1));
                    break;
            }
        }
    }
}