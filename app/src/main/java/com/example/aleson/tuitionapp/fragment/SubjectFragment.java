package com.example.aleson.tuitionapp.fragment;

import android.content.Context;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aleson.tuitionapp.R;
import com.example.aleson.tuitionapp.adapter.SubjectRecyclerViewAdapter;
import com.example.aleson.tuitionapp.constant.FirebasePaths;
import com.example.aleson.tuitionapp.interfaces.ItemClickListener;
import com.example.aleson.tuitionapp.model.SubjectDataModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


public class SubjectFragment extends Fragment implements ItemClickListener {

    private OnFragmentInteractionListener mListener;

    public SubjectFragment() {
        // Required empty public constructor
    }

    ArrayList<SubjectDataModel> userSubjects;
    SubjectRecyclerViewAdapter adapter;

    public static SubjectFragment newInstance() {
        SubjectFragment fragment = new SubjectFragment();
       return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =inflater.inflate(R.layout.fragment_subject, container, false);
        RecyclerView subjectListView = (RecyclerView) rootView.findViewById(R.id.subject_list_view);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        subjectListView.setLayoutManager(llm);
        userSubjects = new ArrayList<SubjectDataModel>();
        adapter = new SubjectRecyclerViewAdapter(userSubjects,R.layout.subject_item_view);
        subjectListView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DatabaseReference userSubjectNode = FirebaseDatabase.getInstance().getReference(FirebasePaths.getUserSubjectNode()).child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        userSubjectNode.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot subject : dataSnapshot.getChildren()){
                    String subjectID = (String) subject.child("key").getValue();
                    DatabaseReference SubjectNode = FirebaseDatabase.getInstance().getReference(FirebasePaths.getSubjectNode()).child(subjectID);
                    SubjectNode.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Log.d("TAG",dataSnapshot.toString());
                            SubjectDataModel subjectData = dataSnapshot.getValue(SubjectDataModel.class);
                            subjectData.setSubjectID(dataSnapshot.getKey());
                            userSubjects.add(0,subjectData);
                            Log.d("TAG",userSubjects.size()+"");
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view, int position) {
        Log.d("TAG","Event id : "+ userSubjects.get(position).getEventNodeID());
    }

    public interface OnFragmentInteractionListener {

    }
}
