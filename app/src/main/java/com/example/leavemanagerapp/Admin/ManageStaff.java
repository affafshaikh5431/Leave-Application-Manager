package com.example.leavemanagerapp.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.leavemanagerapp.DataModels.Staff;
import com.example.leavemanagerapp.MyAdapters.StaffAdapter;
import com.example.leavemanagerapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ManageStaff extends AppCompatActivity {
    Button addStaff;
    ListView lst;

    ArrayList<Staff> ss=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_staff);
        getSupportActionBar().setTitle("LeaveManagerApp");
        addStaff=findViewById(R.id.Addbtn);
        lst=findViewById(R.id.listView1);

        addStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),AddStaff.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseDatabase.getInstance().getReference("Staff").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                ss.clear();
                for (DataSnapshot postSnapshot:snapshot.getChildren())
                {
                    Staff s=postSnapshot.getValue(Staff.class);
                    ss.add(s);
                }
                StaffAdapter adp=new StaffAdapter(getApplicationContext(),ss);
                lst.setAdapter(adp);
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         super.onCreateOptionsMenu(menu);
         getMenuInflater().inflate(R.menu.adminmenus,menu);
         return true;
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String str="";
        switch(item.getItemId())
        {
            case R.id.submenu
        }
        return super.onOptionsItemSelected(item);
    }*/
}