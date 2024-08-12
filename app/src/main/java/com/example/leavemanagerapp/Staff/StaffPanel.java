package com.example.leavemanagerapp.Staff;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.multidex.MultiDex;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.leavemanagerapp.MainActivity;
import com.example.leavemanagerapp.R;
import com.google.android.material.navigation.NavigationView;

public class StaffPanel extends AppCompatActivity{

    Button btn;
    StaffViewRequestFragment staffViewRequestFragment=new StaffViewRequestFragment();
    NavigationView navigationView;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_panel);
        SharedPreferences sh=getSharedPreferences("MysharedPref",MODE_PRIVATE);
        String Sysname=sh.getString("Sysname","");


        getSupportActionBar().setTitle("Hi, "+Sysname);

        getSupportFragmentManager()
                .beginTransaction().replace(R.id.fragmentStaff, new NoticeFragment()).commit();


// drawer layout instance to toggle the menu icon to open
// drawer and back button to close drawer
        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.nav_open, R.string.nav_close);
        
// pass the Open and Close toggle for the drawer layout listener
// to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
// to make the Navigation drawer icon always appear on the action
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        navigationView = findViewById(R.id.navigation_view);

         navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
         {
             @Override
             public boolean onNavigationItemSelected(MenuItem item)
             {
                 switch (item.getItemId())
                 {
                     case R.id.nav_logout:
                         try {
                             SharedPreferences sharedPreferences = getSharedPreferences("MysharedPref", MODE_PRIVATE);
                             SharedPreferences.Editor myEdit = sharedPreferences.edit();
                             myEdit.putInt("islogged", 0);
                             myEdit.apply();

                             Toast.makeText(getApplicationContext(), "User Logged Out!!", Toast.LENGTH_SHORT).show();
                             Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                             startActivity(intent);
                            }
                         catch (Exception e)
                         {
                             e.printStackTrace();
                         }
                         return true;
                     case R.id.nav_account:
                         getSupportFragmentManager()
                                 .beginTransaction().replace(R.id.fragmentStaff, new StaffViewRequestFragment()).commit();
                         return true;


                     case R.id.noticemenu:
                         getSupportFragmentManager().beginTransaction().replace(R.id.fragmentStaff,new NoticeFragment()).commit();
                         return true;

                     case R.id.settings:
                         getSupportFragmentManager().beginTransaction().replace(R.id.fragmentStaff,new SettingsFragment()).commit();
                         return true;
                     default:
                         return false;
                 }

             }

         });


    }

    @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
                return true;
            }

        return super.onOptionsItemSelected(item);
    }

    /*@Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_logout:
                try
                {
                    SharedPreferences sharedPreferences = getSharedPreferences("MysharedPref", MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                    myEdit.putInt("islogged", 0);
                    myEdit.apply();

                    Toast.makeText(this, "User Logged Out!!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                return true;
            case R.id.nav_account:
                getSupportFragmentManager()
                        .beginTransaction().replace(R.id.fragmentStaff,new StaffViewRequestFragment() ).commit();
                return true;
            default:
                return false;
        }

    }*/
}