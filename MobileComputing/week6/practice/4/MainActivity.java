package com.example.mc_week6_prac4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {


    EditText editText;
    Button btn1, btn2;
    WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        editText = (EditText) findViewById(R.id.editText);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        webView = (WebView) findViewById(R.id.webView);

        //webView.setWebViewClient(new MyWebViewClient());
        webView.setWebViewClient(new WebViewClient());  // 이것만 해 줘도 됨. 크롬으로 실행되지 않게.

        webView.getSettings().setJavaScriptEnabled(true);


        webView.getSettings().setBuiltInZoomControls(true);

        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                webView.loadUrl("https://" + editText.getText().toString());

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                webView.goBack();
            }
        });
    }

    class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
        }
       /*
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            //Toast.makeText(, "Oh no! " + description, Toast.LENGTH_SHORT).show();

        }
        */

    }

}