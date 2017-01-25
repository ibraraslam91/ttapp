package com.example.aleson.tuitionapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aleson.tuitionapp.R;
import com.example.aleson.tuitionapp.interfaces.ItemClickListener;
import com.example.aleson.tuitionapp.model.LectureDataModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Aleson on 1/25/2017.
 */

public class LectureRecyclerViewAdapter extends RecyclerView.Adapter<LectureRecyclerViewAdapter.LectureRecyclerViewHolder> {

    ArrayList<LectureDataModel> lectureList;
    int layOut;
    ItemClickListener mListener;

    public LectureRecyclerViewAdapter(ArrayList<LectureDataModel> list, int layOutId){
        this.lectureList = list;
        this.layOut = layOutId;
    }

    @Override
    public LectureRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layOut, parent, false);
        return new LectureRecyclerViewAdapter.LectureRecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(LectureRecyclerViewHolder holder, int position) {
        LectureDataModel lectureData = lectureList.get(position);
        holder.TxtLectureTitle.setText(lectureData.getLectureTitel());
        holder.TxtLectureDec.setText(lectureData.getLectureDec());
        Date lectureTime = new Date(lectureData.getLectureTime());
        SimpleDateFormat formater = new SimpleDateFormat("dd/MMM hh:mm a");
        holder.TxtLectureTime.setText(formater.format(lectureTime));
    }

    @Override
    public int getItemCount() {
        if(lectureList!=null){
            return lectureList.size();
        }else {
            return 0;
        }
    }


    public class LectureRecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView TxtLectureTitle, TxtLectureDec, TxtLectureTime;
        public LectureRecyclerViewHolder(View itemView) {
            super(itemView);
            TxtLectureTitle = (TextView) itemView.findViewById(R.id.lecture_title);
            TxtLectureDec = (TextView) itemView.findViewById(R.id.lecture_dec);
            TxtLectureTime = (TextView) itemView.findViewById(R.id.lecture_time);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClick(v,getAdapterPosition());
                }
            });
        }
    }
}
