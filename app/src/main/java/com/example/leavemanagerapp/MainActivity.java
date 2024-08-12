package com.example.leavemanagerapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.leavemanagerapp.Admin.AdminPanel;
import com.example.leavemanagerapp.DataModels.AdminModel;
import com.example.leavemanagerapp.DataModels.Staff;
import com.example.leavemanagerapp.DataModels.Students;
import com.example.leavemanagerapp.Staff.StaffPanel;
import com.example.leavemanagerapp.Student.StudentDashboard;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    EditText edName,edPass;
    Button btnLog;
    DatabaseReference db;
    boolean isUserFound=false;
    String roles[]={"Admin","Staff","Student"};
    Spinner sp;
    TextView getRole;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();


                if(checkLogin())
                {
                    SharedPreferences sh=getSharedPreferences("MysharedPref",MODE_PRIVATE);
                    int checkRoleId=sh.getInt("islogged",0);

                    if (checkRoleId==1)
                    {
                        Intent intent = new Intent(getApplicationContext(), AdminPanel.class);
                        startActivity(intent);
                    }
                    else if(checkRoleId==2)
                    {
                        Intent intent = new Intent(getApplicationContext(), StaffPanel.class);
                        startActivity(intent);
                    }

                    else if(checkRoleId==3)
                    {
                        Intent intent = new Intent(getApplicationContext(), StudentDashboard.class);
                        startActivity(intent);
                    }

                }

        /*//This code should be done only once To create Database on Firebase
        //!! Do this code and run once then comment it !!

        //add user to the table
        AdminModel am=new AdminModel("1","admin","admin");
        //push to the firebase
        db=FirebaseDatabase.getInstance().getReference("AdminModel");
        String id=db.push().getKey();
        FirebaseDatabase.getInstance().getReference("AdminModel").push().setValue(am).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                }
            }
        });*/

        findwidgets();
        ArrayAdapter role = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, roles);
        sp.setAdapter(role);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getRole.setText(roles[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                getRole.setText("");
            }

        });

        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(edName.getText().toString()) || TextUtils.isEmpty(edPass.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Field cannot be empty", Toast.LENGTH_SHORT).show();
                    edName.requestFocus();
                    return;
                }
                setButton(true);
                //11

                switch (getRole.getText().toString()) {
                    case "Admin":
                        AdminLog();
                        break;

                    case "Staff":
                        StaffLog();
                        break;
                    case "Student":
                        StudLog();
                        break;

                }

            }
        });
    }



    private void AdminLog() {

        FirebaseDatabase.getInstance().
                getReference("AdminModel").orderByChild("name").
                equalTo(edName.getText().toString()).addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                isUserFound=false;

                for(DataSnapshot postSnapShot:snapshot.getChildren())
                {
                    AdminModel am=postSnapShot.getValue(AdminModel.class);
                    if(am==null)
                    {
                        //if object is null
                        setButton(false);
                        Toast.makeText(MainActivity.this, "User Credentials ", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else
                    {
                        Log.d("edPass",edPass.getText().toString());
                        Log.d("vpass",am.getPassword());
                        //if password is correct is not
                        if(am.getPassword().equals(edPass.getText().toString()))
                        {
                            //if password is correct
                            isUserFound=true;
                            // Here we go to Admin Panel
                            AddSharedPref(1,am.getName());
                            Intent intent = new Intent(getApplicationContext(), AdminPanel.class);
                            intent.putExtra("Name", edName.getText().toString());
                            startActivity(intent);
                        }
                        else
                        {
                            //if password is wrong
                            setButton(false);
                            Toast.makeText(MainActivity.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }//For loop ends
                if(!isUserFound)
                {
                    setButton(false);
                    Toast.makeText(MainActivity.this, "Invalid Username", Toast.LENGTH_SHORT).show();
                }
            }//Here ends the Username search
            @Override
            public void onCancelled(DatabaseError error)
            {
                setButton(false);
            }
        });
    }

    private void StaffLog() {
        FirebaseDatabase.getInstance().
                getReference("Staff").orderByChild("staff_id").
                equalTo(edName.getText().toString()).addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                isUserFound=false;

                for(DataSnapshot postSnapShot:snapshot.getChildren())
                {
                    Staff s=postSnapShot.getValue(Staff.class);
                    if(s==null)
                    {
                        //if object is null
                        setButton(false);
                        Toast.makeText(MainActivity.this, "User Credentials ", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else
                    {
                        Log.d("edPass",edPass.getText().toString());
                        Log.d("vpass",s.getPassword());
                        //if password is correct is not
                        if(s.getPassword().equals(edPass.getText().toString()))
                        {
                            //if password is correct
                            isUserFound=true;
                            // Here we go to Admin Panel
                            AddSharedPref(2,s.getStaff_name());
                            Intent intent = new Intent(getApplicationContext(), StaffPanel.class);

                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                        else
                        {
                            //if password is wrong
                            setButton(false);
                            Toast.makeText(MainActivity.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }//For loop ends
                if(!isUserFound)
                {
                    setButton(false);
                    Toast.makeText(MainActivity.this, "Invalid Username", Toast.LENGTH_SHORT).show();
                }
            }//Here ends the Username search
            @Override
            public void onCancelled(DatabaseError error)
            {
                setButton(false);
            }
        });
    }


    private void StudLog() {
        FirebaseDatabase.getInstance().
                getReference("Students").orderByChild("student_id").
                equalTo(edName.getText().toString()).addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                isUserFound=false;

                for(DataSnapshot postSnapShot:snapshot.getChildren())
                {
                    Students s=postSnapShot.getValue(Students.class);
                    if(s==null)
                    {
                        //if object is null
                        setButton(false);
                        Toast.makeText(MainActivity.this, "User Credentials ", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else
                    {
                        Log.d("edPass",edPass.getText().toString());
                        Log.d("vpass",s.getStudent_password());
                        //if password is correct is not
                        if(s.getStudent_password().equals(edPass.getText().toString()))
                        {
                            //if password is correct
                            isUserFound=true;
                            // Here we go to Admin Panel
                            AddSharedPref(3,s.getStudent_name());
                            Intent intent = new Intent(getApplicationContext(), StudentDashboard.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                        else
                        {
                            //if password is wrong
                            setButton(false);
                            Toast.makeText(MainActivity.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }//For loop ends
                if(!isUserFound)
                {
                    setButton(false);
                    Toast.makeText(MainActivity.this, "Invalid Username", Toast.LENGTH_SHORT).show();
                }
            }//Here ends the Username search
            @Override
            public void onCancelled(DatabaseError error)
            {
                setButton(false);
            }
        });
    }

    private void AddSharedPref(int roleid,String Sysname) {
        SharedPreferences sharedPreferences=getSharedPreferences("MysharedPref",MODE_PRIVATE);
        SharedPreferences.Editor myEdit=sharedPreferences.edit();
        myEdit.putInt("islogged",roleid);
        myEdit.putString("Sysname",Sysname);
        myEdit.apply();
    }


    private boolean checkLogin() {
        SharedPreferences sh=getSharedPreferences("MysharedPref",MODE_PRIVATE);
        int a=sh.getInt("islogged",0);
        sh.getString("Sysname","");
        if(a==0)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    private void findwidgets()
    {
        edName=findViewById(R.id.edUserName);
        edPass=findViewById(R.id.edPassword);
        btnLog=findViewById(R.id.loginbtn);
        sp=findViewById(R.id.chooseRole);
        getRole=findViewById(R.id.selectedrole);
    }
    private void setButton(boolean isBusy)
    {

        if(isBusy)
        {
            btnLog.setText("Logging...");
            btnLog.setEnabled(false);
        }
        else
        {
            btnLog.setText("Log In");
            btnLog.setEnabled(true);
        }
    }
}