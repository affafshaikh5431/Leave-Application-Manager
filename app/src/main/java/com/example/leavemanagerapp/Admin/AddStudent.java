package com.example.leavemanagerapp.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.leavemanagerapp.DataModels.Classes;
import com.example.leavemanagerapp.DataModels.Students;
import com.example.leavemanagerapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddStudent extends AppCompatActivity {
    Button addStudentBtn;
    EditText sname,smob,sid,sroll,spass;
    TextView scls;
    List<String> data=new ArrayList<>();

    Spinner sp;
    DatabaseReference db;
    String studname,studmob,studid,studcls,studroll,studpass;
    Students studObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        finwidgets();
        scls.setVisibility(View.GONE);
        db = FirebaseDatabase.getInstance().getReference("Students");
         addStudentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(sname.getText().toString())|| TextUtils.isEmpty(sroll.getText().toString())||TextUtils.isEmpty(sid.getText().toString()) ||TextUtils.isEmpty(smob.getText().toString()))
                {
                    Toast.makeText(AddStudent.this, "Field can't be Empty", Toast.LENGTH_SHORT).show();
                }
                studname = sname.getText().toString();
                studmob=smob.getText().toString();
                studid = sid.getText().toString();
                studroll =sroll.getText().toString();
                studpass=spass.getText().toString();
                // Check for duplicate record
                //checkIfEmpty();
                checkStud(studname);
            }
        });


      sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                  scls.setText(data.get(position));
          }

          @Override
          public void onNothingSelected(AdapterView<?> parent) {
              scls.setText("");
          }
      });


    }

    /*private void checkIfEmpty() {

        if(stu)
    }*/

    @Override
    protected void onStart()
    {
        super.onStart();
        FirebaseDatabase.getInstance().getReference("Classes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot:snapshot.getChildren())
                {
                    Classes s=postSnapshot.getValue(Classes.class);

                    data.add(s.getClassName());
                }
                ArrayAdapter<String> adp=new ArrayAdapter<>(AddStudent.this, android.R.layout.simple_spinner_dropdown_item,data);
                adp.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                sp.setAdapter(adp);


            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }
    private void checkStud(String studname) {
        FirebaseDatabase.getInstance().getReference("Students")
                .orderByChild("student_name")
                .equalTo(studname)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            // Duplicate record found
                            Toast.makeText(AddStudent.this, "Record Already Exists!", Toast.LENGTH_SHORT).show();
                        } else {
                            addStudentRecord();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError error) {
                    }
                });
    }


    private void addStudentRecord() {
        studcls=scls.getText().toString();
        String unique_id = db.push().getKey();
        studObj = new Students(unique_id,studname,studmob,studid,studcls,studroll,studpass, "3");
        db.child(unique_id).setValue(studObj).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(AddStudent.this, "Staff Record added successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), ManageStudents.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(AddStudent.this, "Unable to Save", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }




    private void finwidgets() {

        addStudentBtn=findViewById(R.id.addSbtn);
        sp=findViewById(R.id.chooseClass);
        sname=findViewById(R.id.editSName);
        smob=findViewById(R.id.editSMob);
        sid=findViewById(R.id.edSId);
        scls=findViewById(R.id.edSClass);
        sroll=findViewById(R.id.edSroll);
        spass=findViewById(R.id.edSpass);

    }
}