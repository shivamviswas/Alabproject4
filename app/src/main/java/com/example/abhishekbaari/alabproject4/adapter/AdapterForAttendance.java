package com.example.abhishekbaari.alabproject4.adapter;

/**
 * Created by wikav-pc on 7/4/2018.
 */


import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.abhishekbaari.alabproject4.R;
import com.example.abhishekbaari.alabproject4.modal.Attendance;

import java.util.List;

/**
 * Created by Aws on 11/03/2018.
 */

public class AdapterForAttendance extends RecyclerView.Adapter<AdapterForAttendance.MyViewHolder> {

    private Context mContext ;
    private List<Attendance> myData ;

    public AdapterForAttendance(Context mContext, List<Attendance> mData) {
        this.mContext = mContext;
        this.myData = mData;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view ;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.attendance_feed,parent,false) ;
        final MyViewHolder viewHolder = new MyViewHolder(view) ;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.date.setText(myData.get(position).getDate());
        holder.AP.setText(myData.get(position).getPresentAbsent());
        if(myData.get(position).getPresentAbsent().equals("A"))
        {int Black = Color.parseColor("#FF0000");
            holder.AP.setTextColor(Black);
        }
        else {
            int Black = Color.parseColor("#00A100");
            holder.AP.setTextColor(Black);
        }

    }

    @Override
    public int getItemCount() {
        return myData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView date , AP;

        public MyViewHolder(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            AP = itemView.findViewById(R.id.ap);
            }
    }


}

