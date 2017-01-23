package com.example.aleson.tuitionapp.activity;

import android.content.res.Configuration;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.aleson.tuitionapp.R;

public class HomeActivity extends AppCompatActivity {


    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    NavigationView nvDrawer;
    CoordinatorLayout coordinatorLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initiNaviDrawer();




    }

    public void initiNaviDrawer(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator);
        setSupportActionBar(toolbar);
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
        return new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer,  R.string.closeDreawer);
    }

    public void selectDrawerItem(MenuItem item){


        switch (item.getItemId()){
            case R.id.navi_schedule:
                Log.d("TAG","schedule");

                break;
            case R.id.navi_subject:
                Log.d("TAG","subject");
                break;
            case R.id.navi_notification:
                Log.d("TAG","notification");
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
}
