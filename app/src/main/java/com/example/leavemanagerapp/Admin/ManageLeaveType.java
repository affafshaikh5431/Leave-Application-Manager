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

import com.example.leavemanagerapp.DataModels.LeaveTypes;
import com.example.leavemanagerapp.MyAdapters.LeaveTypeAdapter;
import com.example.leavemanagerapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ManageLeaveType extends AppCompatActivity {

    EditText addname,editname;
    Button addBtn,editBtn;
    ListView lst;
    ArrayList<LeaveTypes> lt=new ArrayList<>();
    LeaveTypes leaveTypes;
    DatabaseReference db;
    String unique_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_leave_type);
        findwidgets();

        db = FirebaseDatabase.getInstance().getReference("LeaveTypes");
        addBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(TextUtils.isEmpty(addname.getText().toString()))
                {
                    Toast.makeText(ManageLeaveType.this, "Feild can't be Empty", Toast.LENGTH_SHORT).show();
                }
                String name = addname.getText().toString();
                checkIfAlreadyExits(name, 1);
                addname.setText("");
            }
        });

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                addname.setText("");
                setAddButtonTF(false);
                setUpdateButtonTF(true);
                LeaveTypes lt=(LeaveTypes)parent.getItemAtPosition(position);
                editname.setText(String.valueOf(lt.getName()));
                unique_id=lt.getUnique_id();
            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (editname.getText().toString().equals("")) {
                    Toast.makeText(ManageLeaveType.this, "Field cannot be empty", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    checkIfAlreadyExits(editname.getText().toString(),2);
                }

            }
        });

    }


    @Override
        protected void onStart() {
            super.onStart();
            FirebaseDatabase.getInstance().getReference("LeaveTypes").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    lt.clear();
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        LeaveTypes s = postSnapshot.getValue(LeaveTypes.class);
                        lt.add(s);
                    }
                    LeaveTypeAdapter adp = new LeaveTypeAdapter(getApplicationContext(), lt);
                    lst.setAdapter(adp);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                }
            });

        }



    private void checkIfAlreadyExits(String name,int no)
    {

        FirebaseDatabase.getInstance().getReference("LeaveTypes")
                .orderByChild("name")
                .equalTo(name)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.exists()) {

                            Toast.makeText(ManageLeaveType.this, "Record Already Exists!", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            switch (no)
                            {
                                case 1: addRecord(name);
                                break;

                                case 2:updateRecord(name);
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                    }
                });
    }



    private void addRecord(String name)
    {
        unique_id=db.push().getKey();
        leaveTypes = new LeaveTypes(unique_id,name);
        FirebaseDatabase.getInstance().getReference("LeaveTypes").child(unique_id).setValue(leaveTypes).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ManageLeaveType.this, "Type added successfully", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(ManageLeaveType.this, "Unable to Save", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateRecord(String name)
    {
        FirebaseDatabase.getInstance().getReference("LeaveTypes").child(unique_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    LeaveTypes l=snapshot.getValue(LeaveTypes.class);

                    if(!name.equals("")) {

                        l.setName(name);
                        FirebaseDatabase.getInstance().getReference("LeaveTypes").child(unique_id).setValue(l).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(Task<Void> task)
                            {
                                if (task.isSuccessful())
                                {

                                    Toast.makeText(ManageLeaveType.this, "Record updated successfully", Toast.LENGTH_SHORT).show();
                                }
                            else
                                {
                                    Toast.makeText(ManageLeaveType.this, "Unable to update record", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    else
                    {
                        Toast.makeText(ManageLeaveType.this, "Cannot Update empty value", Toast.LENGTH_SHORT).show();
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


    private void setAddButtonTF(boolean b) {
            addname.setEnabled(b);
            addBtn.setEnabled(b);
    }

    private void setUpdateButtonTF(boolean b)
    {
        editname.setEnabled(b);
        editBtn.setEnabled(b);
    }

    private void findwidgets()
    {
        addname=findViewById(R.id.addleavename);
        editname=findViewById(R.id.editleavename);
        addBtn=findViewById(R.id.addltbtn);
        editBtn=findViewById(R.id.edltbtn);
        lst=findViewById(R.id.leavetypelist);
    }
}