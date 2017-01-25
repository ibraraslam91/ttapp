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
import com.example.aleson.tuitionapp.adapter.NotificationRecyclerViewAdapter;
import com.example.aleson.tuitionapp.constant.FirebasePaths;
import com.example.aleson.tuitionapp.interfaces.ItemClickListener;
import com.example.aleson.tuitionapp.model.LectureDataModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class NotificationFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private ArrayList<LectureDataModel> notificationsList;
    private NotificationRecyclerViewAdapter adapter;


    public NotificationFragment() {

    }

    public static NotificationFragment newInstance() {
        NotificationFragment fragment = new NotificationFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =inflater.inflate(R.layout.fragment_notification, container, false);
        notificationsList = new ArrayList<LectureDataModel>();
        adapter = new NotificationRecyclerViewAdapter(notificationsList,R.layout.lecture_item_layout);
        adapter.setOnItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.d("TAG","Lecture id : "+ notificationsList.get(position).getLectureID());
            }
        });
        RecyclerView notificationListView = (RecyclerView) rootView.findViewById(R.id.notification_list_view);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        notificationListView.setLayoutManager(llm);
        notificationListView.setAdapter(adapter);



        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DatabaseReference offsetRef = FirebaseDatabase.getInstance().getReference(".info/serverTimeOffset");
        offsetRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                double offset = dataSnapshot.getValue(Double.class);
                final double currentTime = System.currentTimeMillis() + offset;
                DatabaseReference userSubject = FirebaseDatabase.getInstance().getReference(FirebasePaths.getUserDataNode()).child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                userSubject.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot subject : dataSnapshot.getChildren()){
                            String subjectID = (String) subject.child("key").getValue();
                            DatabaseReference lectureDataNode = FirebaseDatabase.getInstance().getReference(FirebasePaths.getLectureDataNode()).child(subjectID);
                            Query lectureQuery = lectureDataNode.orderByChild("lectureTime").startAt(currentTime);
                            lectureQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for(DataSnapshot lectureData : dataSnapshot.getChildren()){
                                        LectureDataModel lectureDataModel = new LectureDataModel();
                                        lectureDataModel.setLectureID(lectureData.getKey());
                                        notificationsList.add(lectureDataModel);
                                        adapter.notifyDataSetChanged();
                                    }
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
