package com.example.aleson.tuitionapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import com.example.aleson.tuitionapp.R;
import com.example.aleson.tuitionapp.asynctask.CloudinaryTask;
import com.example.aleson.tuitionapp.constant.Constants;
import com.example.aleson.tuitionapp.constant.FirebasePaths;
import com.example.aleson.tuitionapp.model.UserDataModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.piotrek.customspinner.CustomSpinner;
import com.pkmmte.view.CircularImageView;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class UserInfoCollectActivity extends AppCompatActivity implements CloudinaryTask.CTcallBack {

    final int Camera_Request_ID = 2;
    final int Gallery_Requset_ID = 3;

    Bitmap bitmap;
    EditText EdtName;
    Button BtnSave;
    CircularImageView userProfileImage;
    CustomSpinner classSpinner;
    String userClass;
    CoordinatorLayout coordinatorLayout;
    boolean imageUploaded=false;
    String[] list;
    Snackbar snackbar;
    String imagePath;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_collect);
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.activity_user_info_collect);
        snackbar = Snackbar.make(coordinatorLayout,"Tap Image to Change Profile Photo",Snackbar.LENGTH_LONG);
        snackbar.show();
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
               showImageDialog();
            }
        });
        BtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(EdtName.getText().toString().equals("")){
                    EdtName.setError("R");
                }else if (userClass != null){
                    Snackbar snackbar1 = Snackbar.make(coordinatorLayout,"Please Select Class",Snackbar.LENGTH_LONG);
                    snackbar1.show();
                }
                else if(imageUploaded){

                    File ImageFile = new File(imagePath);
                    CloudinaryTask cloudinaryTask = new CloudinaryTask(UserInfoCollectActivity.this, CloudinaryTask.profileUploadTag, userID );
                    cloudinaryTask.execute(ImageFile);

                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(UserInfoCollectActivity.this);
                    builder.setTitle("Change Profile Photo");
                    final CharSequence[] items = {"Add Photo","Maybe Later"};
                    builder.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(items[which].equals("Add Photo")){
                                showImageDialog();
                            } else if(items[which].equals("Maybe Later")){
                                uploadUserData(EdtName.getText().toString(),userClass, Constants.getDefaultImage());
                            }
                        }
                    });
                    builder.show();
                }
            }
        });
    }

    public void showImageDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(UserInfoCollectActivity.this);
        builder.setTitle("Add Photo");
        final CharSequence[] items = {"By Camera", "From Gallery"};
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("By Camera")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, Camera_Request_ID);
                } else if (items[item].equals("From Gallery")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            Gallery_Requset_ID);
                }
            }
        });
        builder.show();
    }


    public void uploadUserData(String name ,String userClass, String imageUrl){
        UserDataModel userData = new UserDataModel();
        userData.setProfileImageUrl(imageUrl);
        userData.setUserName(name);
        userData.setUserClass(userClass);
        DatabaseReference userDataNode = FirebaseDatabase.getInstance().getReference(FirebasePaths.getUserDataNode()).child(userID);
        userDataNode.setValue(userData);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 2) {
                if (data != null) {
                    bitmap = data.getExtras().getParcelable("data");
                    userProfileImage.setImageBitmap(bitmap);
                    Uri imageUri = data.getData();
                    imagePath = getRealPathFromURI(imageUri);
                }
            }
            if (requestCode == 3) {
                if (data != null) {
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                        userProfileImage.setImageBitmap(bitmap);
                        Uri imageUri = data.getData();
                        imagePath = getRealPathFromURI(imageUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    @Override
    public void onUploadComplete(Map map) {
        uploadUserData(EdtName.getText().toString(),userClass,map.get("url").toString());
        Intent intent = new Intent(UserInfoCollectActivity.this,HomeActivity.class);
        startActivity(intent);
        this.finish();
    }
}
