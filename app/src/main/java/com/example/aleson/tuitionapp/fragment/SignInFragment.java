package com.example.aleson.tuitionapp.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.aleson.tuitionapp.R;


public class SignInFragment extends Fragment {

    private OnSignInFragmentInteractionListener mListener;

    public SignInFragment() {
        // Required empty public constructor
    }


    public static SignInFragment newInstance(String param1, String param2) {
        SignInFragment fragment = new SignInFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =inflater.inflate(R.layout.fragment_sign_in, container, false);

        final EditText EdtEmail = (EditText) rootView.findViewById(R.id.EdtEmail);
        final EditText EdtPassword = (EditText) rootView.findViewById(R.id.EdtPassword);
        Button BtnSign = (Button) rootView.findViewById(R.id.BtnSignIn);
        BtnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email =  EdtEmail.getText().toString();
                String password = EdtPassword.getText().toString();
                if(email.trim().equals("")){
                    EdtEmail.setError("R");
                }else if(password.trim().equals("")){
                    EdtPassword.setError("R");
                }else {
                    mListener.onSignIn(email,password);
                }
            }
        });

        TextView TxtSignUpLabel = (TextView) rootView.findViewById(R.id.sign_up_lable);
        TxtSignUpLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onSignUpSelected();
            }
        });



        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSignInFragmentInteractionListener) {
            mListener = (OnSignInFragmentInteractionListener) context;
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


    public interface OnSignInFragmentInteractionListener {

        public void onSignIn(String email, String password);
        public void onSignUpSelected();

    }
}
