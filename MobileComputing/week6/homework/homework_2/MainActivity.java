package com.example.mc_week6_homework2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    WebView webView;
    Button naverBtn, daumBtn, googleBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.webView);
        naverBtn = (Button) findViewById(R.id.naverBtn);
        daumBtn = (Button) findViewById(R.id.daumBtn);
        googleBtn = (Button) findViewById(R.id.googleBtn);

        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);

        naverBtn.setOnClickListener(new MyBtnClickListener());
        daumBtn.setOnClickListener(new MyBtnClickListener());
        googleBtn.setOnClickListener(new MyBtnClickListener());
    }

    class MyBtnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.naverBtn:
                    webView.loadUrl("https://www.naver.com");
                    break;
                case R.id.daumBtn:
                    webView.loadUrl("https://www.daum.net");
                    break;
                case R.id.googleBtn:
                    webView.loadUrl("https://www.google.com");
                    break;
            }
        }
    }
}