package com.example.leavemanagerapp.Admin;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.leavemanagerapp.DataModels.Classes;
import com.example.leavemanagerapp.MyAdapters.ClassAdapter;
import com.example.leavemanagerapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ManageClass extends AppCompatActivity {
    Button addClassBtn,updateClassBtn;
    EditText edname,editname;
    String classname;
    String unique_id="";
    Classes cr;
    ListView lstcls;
    ArrayList<Classes> ss=new ArrayList<>();
    DatabaseReference db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_class);
        findwidgets();
        setUpdateButtonTF(false);
        db=FirebaseDatabase.getInstance().getReference("Classes");

        addClassBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(edname.getText().toString()))
                {
                    Toast.makeText(ManageClass.this, "Field Cannot be Empty", Toast.LENGTH_SHORT).show();
                }
                classname=edname.getText().toString();
                checkName(classname);
                edname.setText("");
            }
        });


        // Get data
        lstcls.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                edname.setText("");
                setAddButtonTF(false);
                setUpdateButtonTF(true);

                Classes c=(Classes)parent.getItemAtPosition(position);
                editname.setText(String.valueOf(c.getClassName()));
                unique_id=c.getUnique_id();

            }
        });

        //update data
        updateClassBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                if (editname.getText().toString().equals("")) {
                    Toast.makeText(ManageClass.this, "Field cannot be empty", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    checkNameforUpdate(editname.getText().toString());
                }

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseDatabase.getInstance().getReference("Classes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                ss.clear();
                for (DataSnapshot postSnapshot:snapshot.getChildren())
                {
                    Classes s=postSnapshot.getValue(Classes.class);
                    ss.add(s);
                }
                ClassAdapter adp=new ClassAdapter(getApplicationContext(),ss);
                lstcls.setAdapter(adp);
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

    private void checkNameforUpdate(String classname) {

        FirebaseDatabase.getInstance().getReference("Classes")
                .orderByChild("className")
                .equalTo(classname)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            // Duplicate record found
                            Toast.makeText(ManageClass.this, "Record Already Exists!", Toast.LENGTH_SHORT).show();
                        } else {
                            // No duplicate, add the record
                            updateRecord(classname);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                    }
                });
    }

    private void updateRecord(String classname)
    {
        FirebaseDatabase.getInstance().getReference("Classes").child(unique_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    Classes s=snapshot.getValue(Classes.class);

                    if(!classname.equals("")) {

                        s.setClassName(classname);
                        FirebaseDatabase.getInstance().getReference("Classes").child(unique_id).setValue(s).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(Task<Void> task) {
                                if (task.isSuccessful()) {

                                    Toast.makeText(ManageClass.this, "Record updated successfully", Toast.LENGTH_SHORT).show();
                                    /*Intent intent=new Intent(getApplicationContext(),ManageClass.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);*/
                                } else {
                                    Toast.makeText(ManageClass.this, "Unable to update record", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    else
                    {
                        Toast.makeText(ManageClass.this, "Cannot Update empty value", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

        editname.setText("");
        setUpdateButtonTF(false);
        setAddButtonTF(true);

    }





   private void checkName(String classname) {

        FirebaseDatabase.getInstance().getReference("Classes")
                .orderByChild("className")
                .equalTo(classname)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            // Duplicate record found
                            Toast.makeText(ManageClass.this, "Record Already Exists!", Toast.LENGTH_SHORT).show();
                        } else {
                            // No duplicate, add the record
                           addClassRecord(classname);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                    }
                });
    }


    private void updateClassRecord(String classname)
    {
        FirebaseDatabase.getInstance().getReference("Classes").child(unique_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists())
                {


                    Classes s=snapshot.getValue(Classes.class);
                    s.setClassName(editname.getText().toString());


                    FirebaseDatabase.getInstance().getReference("Classes").child(unique_id).setValue(s).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ManageClass.this, "Record updated successfully", Toast.LENGTH_SHORT).show();

                            }
                            else
                            {
                                Toast.makeText(ManageClass.this, "Unable to update record", Toast.LENGTH_SHORT).show();
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

    private void addClassRecord(String classname) {

        unique_id=db.push().getKey();
        cr = new Classes(unique_id,classname);

        FirebaseDatabase.getInstance().getReference("Classes").child(unique_id).setValue(cr).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ManageClass.this, "Class added successfully", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(ManageClass.this, "Unable to Save", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setAddButtonTF(boolean val)
    {
        edname.setEnabled(val);
        addClassBtn.setEnabled(val);
    }

    private void setUpdateButtonTF(boolean val)
    {
        editname.setEnabled(val);
        updateClassBtn.setEnabled(val);
    }


    private void findwidgets()
    {
        addClassBtn=findViewById(R.id.addclstn);
        edname=findViewById(R.id.edClassName);
        lstcls=findViewById(R.id.listViewCls);

        editname=findViewById(R.id.putName);
        updateClassBtn=findViewById(R.id.editclsbtn);

    }

}