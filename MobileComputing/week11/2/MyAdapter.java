package com.example.mc_week11_prac2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

// 하는 일
// 1. Viewholder(변수로 다 연결해둠)를 생성
// 2. Viewholder를 재활용
public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private ArrayList<PaintTitle> mDataset;

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<PaintTitle> myDataset) {
        mDataset = myDataset;
    }

    // 처음 생성
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewitem, parent, false);  // recyclerview
        Log.d("hwang", "onCreateViewHolder");
        //View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardviewitem, parent, false);  // cardview
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // 만들어진거 재활용
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.imageView.setImageResource(mDataset.get(position % 2).imageId);
        holder.tv1.setText(mDataset.get(position % 2).title);


        final int newpos = position%2;
        final Context mycontext = holder.itemView.getContext();

        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mycontext, mDataset.get(newpos).title, Toast.LENGTH_SHORT).show();
            }
        });


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return 100; //
        //  return mDataset.size();
    }
}
