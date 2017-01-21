package com.example.aleson.tuitionapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import com.example.aleson.tuitionapp.R;
import com.example.aleson.tuitionapp.constant.FirebasePaths;
import com.example.aleson.tuitionapp.model.UserDataModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.piotrek.customspinner.CustomSpinner;
import com.pkmmte.view.CircularImageView;

public class UserInfoCollectActivity extends AppCompatActivity {

    EditText EdtName;
    Button BtnSave;
    CircularImageView userProfileImage;
    CustomSpinner classSpinner;
    String userClass;

    boolean imageUploaded=false;
    String imageUrl;
    String[] list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_collect);
        EdtName = (EditText) findViewById(R.id.EdtName);
        classSpinner = (CustomSpinner) findViewById(R.id.class_spinner);
        list = getResources().getStringArray(R.array.class_array);
        Log.d("TAG",list.length+"");
        classSpinner.initializeStringValues(list, getString(R.string.spinner_prompt));
        classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (!parent.getSelectedItem().equals(getString(R.string.spinner_prompt)))
                    userClass = parent.getSelectedItem().toString();
                else onNothingSelected(parent);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                userClass = null;
            }
        });
        BtnSave = (Button) findViewById(R.id.BtnSave);
        userProfileImage = (CircularImageView) findViewById(R.id.user_profile_image);
        userProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG","click");
            }
        });
        BtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageUploaded){
                    if(EdtName.getText().toString().trim().equals("")){
                        EdtName.setError("R");

                    }if(userClass!=null){

                    }else {
                        String name = EdtName.getText().toString();
                        UserDataModel userData = new UserDataModel();
                        userData.setProfileImageUrl(imageUrl);
                        userData.setUserName(name);
                        userData.setUserClass(userClass);
                        DatabaseReference userDataNode = FirebaseDatabase.getInstance().getReference(FirebasePaths.getUserDataNode()).child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        userDataNode.setValue(userData);
                    }

                }else {

                }
            }
        });
    }
}
