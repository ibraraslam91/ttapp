package com.example.aleson.tuitionapp.activity;

import android.content.res.Configuration;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.aleson.tuitionapp.R;
import com.example.aleson.tuitionapp.fragment.LecturesFragment;
import com.example.aleson.tuitionapp.fragment.NotificationFragment;
import com.example.aleson.tuitionapp.fragment.SubjectFragment;


public class HomeActivity extends AppCompatActivity implements SubjectFragment.OnFragmentInteractionListener,NotificationFragment.OnFragmentInteractionListener {


    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    NavigationView nvDrawer;
    CoordinatorLayout coordinatorLayout;
    FragmentManager fm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        fm = getSupportFragmentManager();
        initiNaviDrawer();
    }

    public void initiNaviDrawer(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        drawerLayout = (DrawerLayout) findViewById(R.id.DrawreLayout);
        toggle = setupDrawerToggle();
        drawerLayout.addDrawerListener(toggle);
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        nvDrawer.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                selectDrawerItem(item);
                return true;
            }
        });
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, drawerLayout, R.string.openDrawer,  R.string.closeDreawer);
    }

    public void selectDrawerItem(MenuItem item){
        switch (item.getItemId()){
            case R.id.navi_subject:
                Log.d("TAG","subject");
                fm.beginTransaction().replace(R.id.fragment_container, SubjectFragment.newInstance()).commit();
                break;
            case R.id.navi_notification:
                Log.d("TAG","notification");
                fm.beginTransaction().replace(R.id.fragment_container, NotificationFragment.newInstance()).commit();
                break;
            case R.id.navi_faq:

                break;
            case R.id.navi_profile:
                Log.d("TAG","profile");
                break;
            case R.id.navi_log_out:
                Log.d("TAG","log out");
                break;

        }

        item.setChecked(true);
        setTitle(item.getTitle());
        drawerLayout.closeDrawers();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        toggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onSubjectSelected(String subjectID) {
        fm.beginTransaction().replace(R.id.fragment_container, LecturesFragment.newInstance(subjectID)).commit();
    }
}
