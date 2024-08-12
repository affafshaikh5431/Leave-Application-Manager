package com.example.leavemanagerapp.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leavemanagerapp.DataModels.Classes;
import com.example.leavemanagerapp.DataModels.Staff;
import com.example.leavemanagerapp.DataModels.Students;
import com.example.leavemanagerapp.MyAdapters.StudentAdapter;
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

public class EditStudent extends AppCompatActivity {

    EditText sedname,semob,sId,sroll;
    TextView sclass;
    List<String> data=new ArrayList<>();

    Spinner sp;

    String val;
    Button btnSUpdate;

    String unique_id,name,mob,id,cls,roll;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_student);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#511a1a")));

        findwidgets();
        fetch_data();



        btnSUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(TextUtils.isEmpty(sedname.getText().toString())||TextUtils.isEmpty(sId.getText().toString()))
                {
                    Toast.makeText(EditStudent.this, "Field cannot be Empty!!", Toast.LENGTH_SHORT).show();
                }

                FirebaseDatabase.getInstance().getReference("Students").child(unique_id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if(snapshot.exists())
                        {
                            Students s=snapshot.getValue(Students.class);
                            s.setStudent_name(sedname.getText().toString());
                            s.setStudent_no(semob.getText().toString());
                            s.setStudent_id(sId.getText().toString());
                            s.setClassName(sclass.getText().toString());
                            s.setRollno(sroll.getText().toString());

                            FirebaseDatabase.getInstance().getReference("Students").child(unique_id).setValue(s).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(EditStudent.this, "Record updated successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(getApplicationContext(),ManageStudents.class);
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        Toast.makeText(EditStudent.this, "Unable to update record", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });

            }
        });

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                sclass.setText(data.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                sclass.setText("");
            }
        });

    }



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
                ArrayAdapter<String> adp=new ArrayAdapter<>(EditStudent.this, android.R.layout.simple_spinner_dropdown_item,data);
                adp.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                sp.setAdapter(adp);


            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }


    private void fetch_data() {
        unique_id = getIntent().getExtras().getString("unique_id");
        name = getIntent().getExtras().getString("name");
        sedname.setText(name);
        mob=getIntent().getExtras().getString("mob");
        semob.setText(mob);

        id = getIntent().getExtras().getString("id");
        sId.setText(id);
        cls = getIntent().getExtras().getString("class");
        sclass.setText(cls);
        roll=getIntent().getExtras().getString("rollno");
        sroll.setText(roll);



    }

    private void findwidgets()
    {
        sedname=findViewById(R.id.editUSName);
        semob=findViewById(R.id.editUSMob);
        sId=findViewById(R.id.edUSId);
        sclass=findViewById(R.id.edUSClass);
        sroll=findViewById(R.id.edUSroll);
        sp=findViewById(R.id.chooseUClass);
        btnSUpdate=findViewById(R.id.updateSbtn);
    }
}