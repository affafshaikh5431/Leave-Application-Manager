package com.example.leavemanagerapp.Student;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.leavemanagerapp.DataModels.Students;
import com.example.leavemanagerapp.MainActivity;
import com.example.leavemanagerapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.MODE_PRIVATE;



public class StudSFragment extends Fragment {
    View rootView;
    TextView logoutS;
    Button btn;
    EditText edmob,edpass;
    String Sysname,uid;
    public StudSFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_stud_s, container, false);
        SharedPreferences sh=getActivity().getSharedPreferences("MysharedPref", Context.MODE_PRIVATE);
        Sysname=sh.getString("Sysname","");
        findwidgest();

        edmob.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!validatemobileno(edmob.getText().toString()))
                {
                    btn.setEnabled(false);
                    edmob.setError("Invalid Mobile Number");
                }
                else
                {
                    btn.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateacc();
            }
        });

        logoutS=rootView.findViewById(R.id.logoutStud);

        logoutS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                logout();
            }
        });


        return rootView;
    }

    private void updateacc()
    {
        if(TextUtils.isEmpty(edmob.getText().toString())||TextUtils.isEmpty(edpass.getText().toString()))
        {
            Toast.makeText(getActivity(), "Field cannot be Empty!!", Toast.LENGTH_SHORT).show();
        }

        FirebaseDatabase.getInstance().getReference("Students").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    Students ss=snapshot.getValue(Students.class);
                    ss.setStudent_no(edmob.getText().toString());
                    ss.setStudent_password(edpass.getText().toString());
                    FirebaseDatabase.getInstance().getReference("Students").child(uid).setValue(ss).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getActivity(), "Record updated successfully", Toast.LENGTH_SHORT).show();

                            }
                            else
                            {
                                Toast.makeText(getActivity(), "Unable to update record", Toast.LENGTH_SHORT).show();
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

    boolean validatemobileno(String no)
    {
        Pattern p= Pattern.compile("[6-9][0-9]{9}");
        Matcher m=p.matcher(no);
        return m.matches();
    }
    @Override
    public void onStart() {
        super.onStart();
        loadstudentdetails();
    }

    private void loadstudentdetails() {
        FirebaseDatabase.getInstance().getReference("Students")
                .orderByChild("student_name")
                .equalTo(Sysname).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                for(DataSnapshot childSnapshot : snapshot.getChildren()) {
                    Students sobj = childSnapshot.getValue(Students.class);
                    uid=sobj.getUnique_id();

                    edmob.setText(sobj.getStudent_no());
                    edpass.setText(sobj.getStudent_password());
                    break;
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }


    private void findwidgest()
    {

        edmob=rootView.findViewById(R.id.editMob);
        edpass=rootView.findViewById(R.id.editpassword);
        btn=rootView.findViewById(R.id.updateBtn);
    }

    private void logout()
    {
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("MysharedPref",MODE_PRIVATE);
        SharedPreferences.Editor myEdit=sharedPreferences.edit();
        myEdit.putInt("islogged",0);
        myEdit.apply();
        Intent intent=new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        Toast.makeText(getActivity(), "Student Logged out !!", Toast.LENGTH_SHORT).show();
    }

}