package com.example.aleson.tuitionapp.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aleson.tuitionapp.R;
import com.example.aleson.tuitionapp.adapter.LectureRecyclerViewAdapter;
import com.example.aleson.tuitionapp.constant.FirebasePaths;
import com.example.aleson.tuitionapp.model.LectureDataModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class LecturesFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    String subjectID;
    ArrayList<LectureDataModel> lecturesList ;
    LectureRecyclerViewAdapter adapter;

    public LecturesFragment() {

    }


    public static LecturesFragment newInstance(String subjectID) {
        LecturesFragment fragment = new LecturesFragment();
        Bundle args = new Bundle();
        args.putString("subjectID", subjectID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lectures, container, false);
        RecyclerView lectureListView = (RecyclerView) rootView.findViewById(R.id.lecture_list_view);
        lecturesList = new ArrayList<LectureDataModel>();
        adapter = new LectureRecyclerViewAdapter(lecturesList,R.layout.lecture_item_layout);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        lectureListView.setLayoutManager(llm);
        lectureListView.setAdapter(adapter);

        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DatabaseReference lectureDataNode = FirebaseDatabase.getInstance().getReference(FirebasePaths.getLectureDataNode()).child(subjectID);
        lectureDataNode.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                LectureDataModel lectureData = dataSnapshot.getValue(LectureDataModel.class);
                lectureData.setLectureID(dataSnapshot.getKey());
                lecturesList.add(0,lectureData);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

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

    public interface OnFragmentInteractionListener {

    }
}
