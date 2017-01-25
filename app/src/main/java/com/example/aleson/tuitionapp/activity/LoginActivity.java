package com.example.aleson.tuitionapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.aleson.tuitionapp.R;
import com.example.aleson.tuitionapp.constant.FirebasePaths;
import com.example.aleson.tuitionapp.fragment.SignInFragment;
import com.example.aleson.tuitionapp.fragment.SignUpFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity implements SignInFragment.OnSignInFragmentInteractionListener,SignUpFragment.OnSignUpFragmentInteractionListener {

    FirebaseAuth mAuth;

    FirebaseAuth.AuthStateListener mAuthStateListener;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initProgressDialog();
        mAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                Log.d("TAG","auth states change");
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user!=null){
                    if(progressDialog!=null){
                        progressDialog.show();
                    }
                    Log.d("TAG",user.getUid());
                    final DatabaseReference userDataNode = FirebaseDatabase.getInstance().getReference(FirebasePaths.getUserDataNode());
                    userDataNode.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Log.d("TAG",dataSnapshot.toString());
                            if(dataSnapshot.child(user.getUid()).exists()){
                                Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                                startActivity(intent);
                                if(progressDialog!=null){
                                    progressDialog.dismiss();
                                }
                            }
                            else {
                                Intent intent = new Intent(LoginActivity.this,UserInfoCollectActivity.class);
                                startActivity(intent);
                                if(progressDialog!=null){
                                    progressDialog.dismiss();
                                }
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }
            }
        };
        getSupportFragmentManager().beginTransaction().add(R.id.frag_container,SignInFragment.newInstance()).commit();
    }
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }
    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthStateListener);
    }
    public void initProgressDialog(){
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
    }
    @Override
    public void onSignIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
            }
        });
    }
    @Override
    public void onSignUpSelected() {

        getSupportFragmentManager().beginTransaction().replace(R.id.frag_container,SignUpFragment.newInstance()).commit();
    }

    @Override
    public void onSignUp(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

            }
        });
    }

    @Override
    public void onSignInSelected() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frag_container,SignInFragment.newInstance()).commit();
    }
}
