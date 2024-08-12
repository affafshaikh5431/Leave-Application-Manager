package com.example.leavemanagerapp.Student;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.MultiDex;

import com.example.leavemanagerapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class StudentDashboard extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
        BottomNavigationView bottomNavigationView;
        HomeFragment homeFragment=new HomeFragment();
        RequestFragment requestFragment=new RequestFragment();
        SettingFragment settingsFragment=new SettingFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sh=getSharedPreferences("MysharedPref",MODE_PRIVATE);
        String Sysname=sh.getString("Sysname","");
        getSupportActionBar().setTitle("Hi, "+Sysname);


        setContentView(R.layout.activity_student_dashboard);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        if (bottomNavigationView != null) {
            bottomNavigationView.setOnNavigationItemSelectedListener(this);
            bottomNavigationView.setSelectedItemId(R.id.home);
        }
    }
        @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.home:
                getSupportFragmentManager()
                .beginTransaction().replace(R.id.flFragment, homeFragment).commit();
                return true;


                case R.id.settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment,new StudSFragment()).commit();
                return true;

            case R.id.sendrequest:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment,requestFragment)
                        .commit();
                return true;
        }
        return false;
        }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


}