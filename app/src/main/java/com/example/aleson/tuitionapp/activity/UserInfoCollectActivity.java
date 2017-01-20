package com.example.aleson.tuitionapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import com.example.aleson.tuitionapp.R;
import com.example.aleson.tuitionapp.model.UserDataModel;

public class UserInfoCollectActivity extends AppCompatActivity {

    EditText EdtName,EdtClass;
    Button BtnSave;
    ImageView userProfileImage;

    boolean imageUploaded=false;
    String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_collect);

        EdtName = (EditText) findViewById(R.id.EdtName);

        BtnSave = (Button) findViewById(R.id.BtnSave);

        userProfileImage = (ImageView) findViewById(R.id.user_profile_image);

        BtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(imageUploaded){

                    String name = EdtName.getText().toString();


                    UserDataModel userData = new UserDataModel();
                    userData.setProfileImageUrl(imageUrl);
                }


            }
        });



    }
}
