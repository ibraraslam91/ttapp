package com.example.aleson.tuitionapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aleson.tuitionapp.R;
import com.example.aleson.tuitionapp.interfaces.ItemClickListener;
import com.example.aleson.tuitionapp.model.SubjectDataModel;

import java.util.ArrayList;

/**
 * Created by Aleson on 1/24/2017.
 */

public class SubjectRecyclerViewAdapter extends RecyclerView.Adapter<SubjectRecyclerViewAdapter.SubjectRecyclerViewHolder> {

    ArrayList<SubjectDataModel> subjectList;
    int LayoutID;
    ItemClickListener mListener;


    public SubjectRecyclerViewAdapter(ArrayList<SubjectDataModel> list, int id){
        this.subjectList = list;
        this.LayoutID = id;

    }

    @Override
    public SubjectRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(LayoutID, parent, false);
        return new SubjectRecyclerViewHolder(v);
    }

    public void setOnItemClickListener(ItemClickListener clickListener){
        this.mListener = clickListener;
    }


    @Override
    public void onBindViewHolder(SubjectRecyclerViewHolder holder, int position) {

        SubjectDataModel subjectData = subjectList.get(position);
        holder.TxtSubjectName.setText(subjectData.getSubjectName());
        holder.TxtSubjectTeacher.setText(subjectData.getTeacher());
        holder.TxtSubjectTime.setText(subjectData.getTimeing());

    }

    @Override
    public int getItemCount() {
        if(subjectList!=null)
            return subjectList.size();
        else
            return 0;
    }

    public class SubjectRecyclerViewHolder extends RecyclerView.ViewHolder{

        public TextView TxtSubjectName, TxtSubjectTeacher, TxtSubjectTime;

        public SubjectRecyclerViewHolder(View itemView) {
            super(itemView);
            TxtSubjectName = (TextView) itemView.findViewById(R.id.subject_text);
            TxtSubjectTeacher = (TextView) itemView.findViewById(R.id.subject_teacher);
            TxtSubjectTime = (TextView) itemView.findViewById(R.id.subject_time);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClick(v,getAdapterPosition());
                }
            });
        }
    }

}
