package com.example.leavemanagerapp.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.leavemanagerapp.DataModels.NoticeModel;
import com.example.leavemanagerapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NoticetoStaff extends AppCompatActivity {

    DatabaseReference db;
    DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
    Calendar obj = Calendar.getInstance();
    String str;
    EditText Edreason;
    Button sendNoticebtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticeto_staff);
        sendNoticebtn=findViewById(R.id.sendNoticebtn);
        Edreason=findViewById(R.id.Edreason);

        db=FirebaseDatabase.getInstance().getReference("NoticeModel");
        sendNoticebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Edreason.getText().equals(""))
                {
                    Toast.makeText(NoticetoStaff.this, "Meassge Cannot be Blank!!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    str=formatter.format(obj.getTime());
                    sendMessage();

                }
            }
        });
    }

    private void sendMessage()
    {
        String unique_id = db.push().getKey();
        NoticeModel nm=new NoticeModel(unique_id,str,Edreason.getText().toString());
        db.child(unique_id).setValue(nm).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(NoticetoStaff.this, "Staff Record added successfully", Toast.LENGTH_SHORT).show();
                    Edreason.setText("");
                } else {
                    Toast.makeText(NoticetoStaff.this, "Unable to Save", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}