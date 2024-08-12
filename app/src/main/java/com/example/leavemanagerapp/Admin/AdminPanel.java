package com.example.leavemanagerapp.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.leavemanagerapp.MainActivity;
import com.example.leavemanagerapp.R;
import com.example.leavemanagerapp.R;

public class AdminPanel extends AppCompatActivity {
    CardView staff,studentCard,classroomcard,leavetypecard,assignteacher,sendNoticeCard;
    Intent intent_obj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        getSupportActionBar().setTitle("Admin Panel");
        findwidgets();

        staff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent_obj=new Intent(getApplicationContext(), com.example.leavemanagerapp.Admin.ManageStaff.class);
                startActivity(intent_obj);

            }
        });

       studentCard.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               intent_obj=new Intent(getApplicationContext(), ManageStudents.class);
               startActivity(intent_obj);
           }
       });

       classroomcard.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                    intent_obj=new Intent(getApplicationContext(),ManageClass.class);
                    startActivity(intent_obj);
           }
       });

       leavetypecard.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               intent_obj=new Intent(getApplicationContext(),ManageLeaveType.class);
               startActivity(intent_obj);
           }
       });

       assignteacher.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               intent_obj=new Intent(getApplicationContext(),AssignClsTeacher.class);
               startActivity(intent_obj);
           }
       });

        sendNoticeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent_obj=new Intent(getApplicationContext(),NoticetoStaff.class);
                startActivity(intent_obj);
            }
        });



    }
    private void findwidgets()

    {
        staff=findViewById(R.id.addStaff);
        studentCard=findViewById(R.id.addStudents);
        classroomcard=findViewById(R.id.addClassroom);
        leavetypecard=findViewById(R.id.addLeaveType);
        assignteacher=findViewById(R.id.assignteacher);
        sendNoticeCard=findViewById(R.id.sendNotice);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.adminmenus,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.logout:
                SharedPreferences sharedPreferences=getSharedPreferences("MysharedPref",MODE_PRIVATE);
                SharedPreferences.Editor myEdit=sharedPreferences.edit();
                myEdit.putInt("islogged",0);
                myEdit.apply();

                Toast.makeText(this, "User Logged Out!!", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
