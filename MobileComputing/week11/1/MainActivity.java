package com.example.mc_week11_prac;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String[] mid = {"하우스", "빌리언즈", "왕좌의게임"};

        lv = (ListView) findViewById(R.id.listview);

//        ArrayAdapter<String> adapter =
//                new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, mid);

          ArrayAdapter<String> adapter =
              new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, mid);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        //Multiple Choice
        // ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, mid);
        // lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Toast.makeText(getApplicationContext(), "Click: " + mid[arg2] + " arg2=" + arg2 + " arg3=" + arg3, Toast.LENGTH_SHORT).show();
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Toast.makeText(getApplicationContext(), "LongClick: " + mid[arg2], Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

}