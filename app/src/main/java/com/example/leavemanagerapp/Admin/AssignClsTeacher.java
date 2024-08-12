package com.example.leavemanagerapp.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leavemanagerapp.DataModels.ClassTeacher;
import com.example.leavemanagerapp.DataModels.Classes;
import com.example.leavemanagerapp.DataModels.Staff;
import com.example.leavemanagerapp.DataModels.Students;
import com.example.leavemanagerapp.MyAdapters.ClassAdapter;
import com.example.leavemanagerapp.MyAdapters.ClassTeacherAdapter;
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

public class AssignClsTeacher extends AppCompatActivity {

    ListView lst;
    ClassTeacher ct;
    Spinner spinnerClass,spinnerName;
    TextView txtCls,txtname;
    Button assignBtn;
    String clsname,unique_id;
    DatabaseReference db;
    ArrayList<ClassTeacher> objlist=new ArrayList<>();
    List<String> listCls=new ArrayList<>();
    List<String> listName=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_cls_teacher);
        findwidgets();
        db=FirebaseDatabase.getInstance().getReference("ClassTeacher");
        unique_id = db.push().getKey();
        spinnerClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                txtCls.setText(listCls.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                txtname.setText(listName.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        assignBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clsname=txtCls.getText().toString();
                checkifExist(clsname);
            }
        });


    }


    private void checkifExist(String clsname)
    {

        FirebaseDatabase.getInstance().getReference("ClassTeacher")
                .orderByChild("classname")
                .equalTo(clsname)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (!snapshot.exists())
                        {
                            addRecord();
                        }
                        else
                        {
                            for(DataSnapshot childSnapshot : snapshot.getChildren()) {
                                ClassTeacher ctobj = childSnapshot.getValue(ClassTeacher.class);
                                String id = ctobj.getUnique_id();
                                updateRecord(id);
                                // If you only want to handle the first matching record, you can break here
                                break;
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError error) {
                    }
                });

    }

    private void updateRecord(String id)
    {
        ClassTeacher obj=new ClassTeacher(txtCls.getText().toString(),txtname.getText().toString());
        obj.setUnique_id(id);
        FirebaseDatabase.getInstance().getReference("ClassTeacher").child(id).setValue(obj).addOnCompleteListener(new OnCompleteListener<Void>()
        {
            @Override
            public void onComplete(Task<Void> task)
            {
                if(task.isSuccessful())
                {
                    Toast.makeText(AssignClsTeacher.this, "Class Teacher Assinged Sucessfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addRecord() 
    {
        String staffname=txtname.getText().toString();

        ct=new ClassTeacher(unique_id,clsname,staffname);
        db.child(unique_id).setValue(ct).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(AssignClsTeacher.this, "Class Teacher Assinged Sucessfully", Toast.LENGTH_SHORT).show();         
                }
                else
                {
                    Toast.makeText(AssignClsTeacher.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
        
    }

    @Override
    protected void onStart() {
        super.onStart();
        {

            loadClassTeachers();
            loadClass();
            loadName();
        }
    }

    private void loadClassTeachers()
    {
        FirebaseDatabase.getInstance().getReference("ClassTeacher").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                objlist.clear();
                for (DataSnapshot postSnapshot:snapshot.getChildren())
                {
                    ClassTeacher objectct=postSnapshot.getValue(ClassTeacher.class);
                    objlist.add(objectct);
                }
                ClassTeacherAdapter adp=new ClassTeacherAdapter(getApplicationContext(),objlist);
                lst.setAdapter(adp);
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

    private void loadName()
    {
        FirebaseDatabase.getInstance().getReference("Staff").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {

                for (DataSnapshot postSnapshot : snapshot.getChildren())
                {
                    Staff s = postSnapshot.getValue(Staff.class);
                    if(!s.getStatus().equals("Not Active"))
                    {
                        listName.add(s.getStaff_name());
                    }
                }
                ArrayAdapter<String> adp = new ArrayAdapter(AssignClsTeacher.this, android.R.layout.simple_spinner_dropdown_item, listName);
                adp.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                spinnerName.setAdapter(adp);

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }

        });
    }

    private void loadClass()
    {
        FirebaseDatabase.getInstance().getReference("Classes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {

                for (DataSnapshot postSnapshot : snapshot.getChildren())
                {
                    Classes s = postSnapshot.getValue(Classes.class);

                    listCls.add(s.getClassName());
                }
                ArrayAdapter<String> adp = new ArrayAdapter(AssignClsTeacher.this, android.R.layout.simple_spinner_dropdown_item, listCls);
                adp.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                spinnerClass.setAdapter(adp);

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }

        });
    }
        


    private void findwidgets()
    {
        txtCls=findViewById(R.id.txtClass);
        txtname=findViewById(R.id.txtstaff);
        spinnerClass=findViewById(R.id.chooseclsspinner);
        spinnerName=findViewById(R.id.choosenamespinner);
        assignBtn=findViewById(R.id.assignBtn);
        lst=findViewById(R.id.lst);
    }
}