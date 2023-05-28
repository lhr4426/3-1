package com.example.mc_week11_prac2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        // mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);  // for general
        //   mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);  // for general, horizontal
        //    mLayoutManager = new GridLayoutManager(this,2);   //  Grid, (linear  cardview )

        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter PaintTitle
        ArrayList<PaintTitle> myDataset=new ArrayList<PaintTitle>();
        myDataset.add(new PaintTitle(R.drawable.cat1, "cat1"));
        myDataset.add(new PaintTitle(R.drawable.cat2, "cat2"));

        // adapter: 데이터 보여주는 애
        mAdapter = new MyAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);

    }
}