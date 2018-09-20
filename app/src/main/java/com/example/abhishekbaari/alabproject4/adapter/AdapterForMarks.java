package com.example.abhishekbaari.alabproject4.adapter;

/**
 * Created by wikav-pc on 7/4/2018.
 */


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.example.abhishekbaari.alabproject4.R;
import com.example.abhishekbaari.alabproject4.modal.Marks;


import java.util.List;

/**
 * Created by Aws on 11/03/2018.
 */

public class AdapterForMarks extends RecyclerView.Adapter<AdapterForMarks.MyViewHolder> {

    private Context mContext ;
    private List<Marks> myData ;
    RequestOptions option;
    String ComId;


    public AdapterForMarks(Context mContext, List<Marks> mData) {
        this.mContext = mContext;
        this.myData = mData;


        // Request option for Glide
        option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view ;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.marksfeed,parent,false) ;
        final MyViewHolder viewHolder = new MyViewHolder(view) ;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.st_name.setText(myData.get(position).getStName());
        holder.st_sub.setText(myData.get(position).getStSubject());
        holder.st_marks.setText(myData.get(position).getObtmarks());
        holder.examName.setText(myData.get(position).getExamName());
        holder.totalmarks.setText(myData.get(position).getTotalmarks());
        int value=Integer.parseInt(myData.get(position).getObtmarks());

        holder.seekBar.setProgress(value);
        holder.seekBar.setEnabled(false);
        holder.seekBar.setClickable(false);
    }

    @Override
    public int getItemCount() {
        return myData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView st_name ,st_sub,st_marks,totalmarks,examName;
        SeekBar seekBar;

        public MyViewHolder(View itemView) {
            super(itemView);
            seekBar=itemView.findViewById(R.id.seekBarScale);
            st_name = itemView.findViewById(R.id.st_markName);
            st_sub = itemView.findViewById(R.id.MarkSubject);
            examName = itemView.findViewById(R.id.examName);
            st_marks = itemView.findViewById(R.id.obtainMarks);
            totalmarks = itemView.findViewById(R.id.totalMark);
            }
    }


}

