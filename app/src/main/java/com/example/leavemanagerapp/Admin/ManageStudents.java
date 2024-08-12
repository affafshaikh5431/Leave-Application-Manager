package com.example.leavemanagerapp.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.leavemanagerapp.DataModels.Classes;
import com.example.leavemanagerapp.DataModels.Staff;
import com.example.leavemanagerapp.DataModels.Students;
import com.example.leavemanagerapp.MyAdapters.ClassAdapter;
import com.example.leavemanagerapp.MyAdapters.StaffAdapter;
import com.example.leavemanagerapp.MyAdapters.StudentAdapter;
import com.example.leavemanagerapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ManageStudents extends AppCompatActivity {
    Button addStudentbtn;
    ArrayList<Students> studObj=new ArrayList<>();
    ListView lst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_students);
        findwidgets();

        addStudentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),AddStudent.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseDatabase.getInstance().getReference("Students").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                studObj.clear();
                for (DataSnapshot postSnapshot:snapshot.getChildren())
                {
                    Students s=postSnapshot.getValue(Students.class);
                    studObj.add(s);
                }
                StudentAdapter adp=new StudentAdapter(getApplicationContext(),studObj);
                lst.setAdapter(adp);
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }



    private void findwidgets() {
        addStudentbtn=findViewById(R.id.AddStudentBtn);
        lst=findViewById(R.id.StudentlistView);

    }


}