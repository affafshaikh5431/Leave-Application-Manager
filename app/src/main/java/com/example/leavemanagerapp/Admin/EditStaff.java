package com.example.leavemanagerapp.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.leavemanagerapp.DataModels.Staff;
import com.example.leavemanagerapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditStaff extends AppCompatActivity {
    EditText edname,edId,edMob;
    CheckBox status;
    String val;
    Button btnUpdate;

    String unique_id,name,num,id,user_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_staff);
        findwidgets();
        fetch_data();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(TextUtils.isEmpty(edname.getText().toString())||TextUtils.isEmpty(edId.getText().toString()))
                {
                    Toast.makeText(EditStaff.this, "Field cannot be Empty!!", Toast.LENGTH_SHORT).show();
                }

                FirebaseDatabase.getInstance().getReference("Staff").child(unique_id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if(snapshot.exists())
                        {
                            if (status.isChecked()) {val = "Active";}
                            else { val = "Not Active"; }

                            Staff ss=snapshot.getValue(Staff.class);
                            ss.setStaff_name(edname.getText().toString());
                            ss.setStaff_no(edMob.getText().toString());
                            ss.setStaff_id(edId.getText().toString());
                            ss.setStatus(val);

                            FirebaseDatabase.getInstance().getReference("Staff").child(unique_id).setValue(ss).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(EditStaff.this, "Record updated successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(getApplicationContext(),ManageStaff.class);
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        Toast.makeText(EditStaff.this, "Unable to update record", Toast.LENGTH_SHORT).show();
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
    }

    private void fetch_data() {
        unique_id = getIntent().getExtras().getString("unique_id");
        name = getIntent().getExtras().getString("name");
        edname.setText(name);
        num=getIntent().getExtras().getString("mob");
        edMob.setText(num);
        id = getIntent().getExtras().getString("id");
        edId.setText(id);
        user_status = getIntent().getExtras().getString("status");
        if (user_status.equals("Active")) {
            status.setChecked(true);

        } else {
            status.setChecked(false);
        }



    }

    private void findwidgets()
    {
        edname=findViewById(R.id.editName);
        edId=findViewById(R.id.editId);
        edMob=findViewById(R.id.editMob);
        status=findViewById(R.id.checkBox);
        btnUpdate=findViewById(R.id.updateBtn);
    }
}