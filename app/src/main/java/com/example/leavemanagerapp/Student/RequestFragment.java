package com.example.leavemanagerapp.Student;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.leavemanagerapp.DataModels.ClassTeacher;
import com.example.leavemanagerapp.DataModels.LeaveRequestModel;
import com.example.leavemanagerapp.DataModels.LeaveTypes;
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
import java.util.Calendar;
import java.util.List;


public class RequestFragment extends Fragment {
    View rootView;
    TextView type;
    Spinner sp;
    DatabaseReference db;
    LeaveRequestModel lrm;
    Button sendReq,dateFButton,dateTButton;
    EditText edN,edCls,edReason;
    String className;
    String Sysname,Studentname,StudentClass,LeaveType,reason,fDate,Tdate,status="Pending";
    DatePickerDialog datePickerDialog;
    List<String> data=new ArrayList<>();
    private int notificationId = 0;
    public RequestFragment()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_request, container, false);
        SharedPreferences sh=getContext().getSharedPreferences("MysharedPref",Context.MODE_PRIVATE);
        Sysname=sh.getString("Sysname","");
        findwidgets();

        fetchStudentData();
        db=FirebaseDatabase.getInstance().getReference("LeaveRequestModel");
        initDatePicker(dateFButton);
        initDatePicker(dateTButton);



        fetchclassteacher();

        sendReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                LeaveType=type.getText().toString();
                reason=edReason.getText().toString();
                fDate=dateFButton.getText().toString();
                Tdate=dateTButton.getText().toString();

                sendRequest();




            }
        });
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                type.setText(data.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                type.setText("");
            }
        });
        return  rootView;
    }

    private void sendRequest() {

        if(TextUtils.isEmpty(edReason.getText().toString()))
        {
            Toast.makeText(getActivity(), "Feilds cannot be empty", Toast.LENGTH_SHORT).show();
        }

        String unique_id = db.push().getKey();
        lrm = new LeaveRequestModel(unique_id,Studentname,StudentClass,LeaveType,reason,fDate,Tdate,status);
        db.child(unique_id).setValue(lrm).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "Request Send SuccessFully !!", Toast.LENGTH_SHORT).show();
                    clearFeilds();
                } else {
                    Toast.makeText(getContext(), "Unable to Send Request !!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void clearFeilds()
    {
        edReason.setText("");
        initDatePicker(dateFButton);
        initDatePicker(dateTButton);
        type.setText("");
    }






    @Override
    public void onStart() {
        super.onStart();
        fetchStudentData();
        fetchLeaveTypes();

    }

    private void fetchclassteacher()
    {
        FirebaseDatabase.getInstance().getReference("ClassTeacher")
                .orderByChild("classname")
                .equalTo(StudentClass)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                            for(DataSnapshot childSnapshot : snapshot.getChildren()) {
                                ClassTeacher ctobj = childSnapshot.getValue(ClassTeacher.class);
                                String staffname = ctobj.getStaffname();
                                Toast.makeText(getContext(), staffname, Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }
                    @Override
                    public void onCancelled(DatabaseError error) {
                    }
                });

    }

    private void findwidgets() {

        //edN=rootView.findViewById(R.id.edStudentName);
        //edCls=rootView.findViewById(R.id.edStudentClassName);
        edReason=rootView.findViewById(R.id.Edreason);
        type=rootView.findViewById(R.id.type);
        sendReq=rootView.findViewById(R.id.sendreqBTN);
        sp=rootView.findViewById(R.id.chooseLeaveType);
        dateFButton=rootView.findViewById(R.id.dateFbtn);
        dateTButton=rootView.findViewById(R.id.dateTbtn);
    }



    private void fetchStudentData()
    {
        FirebaseDatabase.getInstance().
                getReference("Students").orderByChild("student_name").
                equalTo(Sysname).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot postSnapShot:snapshot.getChildren()) {
                    Students s = postSnapShot.getValue(Students.class);

                    Studentname=s.getStudent_name();
                    StudentClass=s.getClassName();


                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    private void fetchLeaveTypes()
    {
            FirebaseDatabase.getInstance().getReference("LeaveTypes").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    for (DataSnapshot postSnapshot:snapshot.getChildren())
                    {
                        LeaveTypes s=postSnapshot.getValue(LeaveTypes.class);
                        data.add(s.getName());
                    }
                    ArrayAdapter<String> adp=new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,data);
                    adp.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                    sp.setAdapter(adp);
                }
                @Override
                public void onCancelled(DatabaseError error) {
                }
            });
        }


    private void initDatePicker(final Button btn) {
        final Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_DEVICE_DEFAULT_DARK;

        final DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), style, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = makeDateString(dayOfMonth, month, year);
                btn.setText(date); // Set the selected date to the button
            }
        }, year, month, day);

        // Show the current date on the button when it's initialized
        btn.setText(makeDateString(day, month + 1, year));

        // Set a click listener to open the DatePickerDialog
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
    }

    private String makeDateString(int dayOfMonth, int month, int year) {
        String dayStr = String.valueOf(dayOfMonth);
        String monthStr = String.valueOf(month);
        if (dayOfMonth < 10) {
            dayStr = "0" + dayStr; // Add leading zero for single-digit days
        }
        if (month < 10) {
            monthStr = "0" + monthStr; // Add leading zero for single-digit months
        }
        return dayStr + "/" + monthStr + "/" + year; // Format: MM/DD/YYYY
    }


}