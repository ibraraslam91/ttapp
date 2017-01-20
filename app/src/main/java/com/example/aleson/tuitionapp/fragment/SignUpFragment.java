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


public class SignUpFragment extends Fragment {

    private OnSignUpFragmentInteractionListener mListener;

    public SignUpFragment() {
        // Required empty public constructor
    }


    public static SignUpFragment newInstance() {
        SignUpFragment fragment = new SignUpFragment();
       return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =inflater.inflate(R.layout.fragment_sign_up, container, false);

        final EditText EdtEmail = (EditText) rootView.findViewById(R.id.EdtEmail);
        final EditText EdtPassword = (EditText) rootView.findViewById(R.id.EdtPassword);

        Button BtnSignUp = (Button) rootView.findViewById(R.id.BtnSignUp);

        BtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = EdtEmail.getText().toString();
                String password = EdtPassword.getText().toString();
                if(email.trim().equals("")){
                    EdtEmail.setError("R");
                }else if(password.trim().equals("")){
                    EdtPassword.setError("R");
                }else {
                    mListener.onSignUp(email,password);
                }
            }
        });

        TextView TxtSignInLable = (TextView) rootView.findViewById(R.id.already_account_lable);
        TxtSignInLable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onSignInSelected();
            }
        });

        return rootView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSignUpFragmentInteractionListener) {
            mListener = (OnSignUpFragmentInteractionListener) context;
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


    public interface OnSignUpFragmentInteractionListener {
        public void onSignUp(String email,String password);
        public void onSignInSelected();
    }
}
