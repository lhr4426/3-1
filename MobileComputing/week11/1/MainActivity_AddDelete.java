package com.example.mc_week11_prac;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity_AddDelete extends AppCompatActivity {

    ListView lv;

    EditText ed;
    ArrayList<String> midList;

    ArrayAdapter<String> adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_add_delete);

        lv = (ListView) findViewById(R.id.listview);
        ed = (EditText) findViewById(R.id.editText);

        midList = new ArrayList<String>();
        midList.add("하우스");
        midList.add("빌리언즈");

        adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, midList);
        lv.setAdapter(adapter2);

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                midList.remove(arg2);
                adapter2.notifyDataSetChanged();
                return false;

            }
        });


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Toast.makeText(getApplicationContext(), "Click: " + midList.get(arg2), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addClick(View v){
        midList.add(ed.getText().toString());
        adapter2.notifyDataSetChanged();
    }
}
