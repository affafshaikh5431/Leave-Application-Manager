package com.example.leavemanagerapp.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.leavemanagerapp.DataModels.Staff;
import com.example.leavemanagerapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddStaff extends AppCompatActivity {
    EditText edName,edId,edmob,edPass;
    Button btnSave;
    CheckBox status;
    DatabaseReference db;
    Staff ss;
    boolean isFound;
    String name,mob,id,pass,userstatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_staff);
        findwidgets();

        db=FirebaseDatabase.getInstance().getReference("Staff");

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(edName.getText().toString())|| TextUtils.isEmpty(edId.getText().toString()) ||TextUtils.isEmpty(edmob.getText().toString()))
                {
                    Toast.makeText(AddStaff.this, "Field can't be Empty", Toast.LENGTH_SHORT).show();
                }

                name = edName.getText().toString();
                mob=edmob.getText().toString();
                id = edId.getText().toString();
                pass = edPass.getText().toString();

                if (status.isChecked()) {
                    userstatus = "Active";
                } else {
                    userstatus = "Not Active";
                }

                // Check for duplicate record
                checkUser(name);
            }
        });




    }


    private void checkUser(String name) {
        FirebaseDatabase.getInstance().getReference("Staff")
                .orderByChild("staff_name")
                .equalTo(name)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            // Duplicate record found
                            Toast.makeText(AddStaff.this, "Record Already Exists!", Toast.LENGTH_SHORT).show();
                        } else {
                            // No duplicate, add the record
                            addStaffRecord();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                    }
                });
    }

    private void addStaffRecord() {
        String unique_id = db.push().getKey();
        ss = new Staff(unique_id,name,mob, id, pass, userstatus, "2");

        db.child(unique_id).setValue(ss).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(AddStaff.this, "Staff Record added successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), com.example.leavemanagerapp.Admin.ManageStaff.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(AddStaff.this, "Unable to Save", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void findwidgets()
    {
        edName=findViewById(R.id.editTextTextPersonName);
        edmob=findViewById(R.id.editMob);
        edId=findViewById(R.id.editTextTextPersonName2);
        edPass=findViewById(R.id.editTextTextPassword);
        edPass.setText("123");
        edPass.setEnabled(false);
        btnSave=findViewById(R.id.button2);
        status=findViewById(R.id.checkBox);
    }
}