package com.example.mc_week11_prac;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity_Custom extends AppCompatActivity {

    ListView lv;
    ArrayList<PaintTitle> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.listview);

        data = new ArrayList<PaintTitle>();
        data.add(new PaintTitle(R.drawable.cat1, "Cat1"));
        data.add(new PaintTitle(R.drawable.cat2, "Cat2"));

        MyBaseAdapter adapter = new MyBaseAdapter(this, data);

        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                Toast.makeText(getApplicationContext(), "Click: position=" + arg2, Toast.LENGTH_SHORT).show();
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                Toast.makeText(getApplicationContext(), "LongClick: position=" + arg2, Toast.LENGTH_SHORT).show();
                return false;

            }
        });
    }

    class MyBaseAdapter extends BaseAdapter {
        Context mContext = null;
        ArrayList<PaintTitle> mData = null;
        public MyBaseAdapter(Context context, ArrayList<PaintTitle> data) {
            mContext = context;
            mData = data;
        }
        // 리스트뷰 -> 어댑터 : 몇개 있는지 확인
        @Override
        public int getCount() {
            //   return mData.size();
            return 100;  // for test
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public PaintTitle getItem(int position) {
            return mData.get(position%2);
        }


        // 리스트뷰 -> 어댑터 : 포지션에 맞는 뷰 요청
        // convertView : 스크롤 올리고 내리다가 이 아이템이 보여야 할 때 물어봄
        // 재활용 할게 생기면 줌
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View itemLayout;
            int newposition = position % 2; // for test

            // 재활용 할게 없음 = 처음 생성함
            // 매번 새로 만들지 않기 위해 한번만 실행하게됨
            if (convertView == null) {
                itemLayout = View.inflate(mContext, R.layout.listview_item, null);
                Log.d("hwang", "convertView=null pos="+position);
            }
            // 재활용 할 껍데기는 있음
            // 새로 만든거 아님 이름만 바뀐거임
            else {
                itemLayout = convertView;
                Log.d("hwang", "convertView!=null pos="+position);
            }

            ImageView imageView = (ImageView) itemLayout.findViewById(R.id.imageview);
            imageView.setImageResource(mData.get(newposition).imageId);

            TextView textView = (TextView) itemLayout.findViewById(R.id.textview);
            textView.setText(mData.get(newposition).title);
            return itemLayout;

        }
    }

    class PaintTitle {
        int imageId;
        String title;

        public PaintTitle(int id, String str) {
            imageId = id;
            title = str;
        }

    }
}
