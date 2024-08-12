package com.example.leavemanagerapp.Student;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leavemanagerapp.DataModels.Students;
import com.example.leavemanagerapp.MainActivity;
import com.example.leavemanagerapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.Context.MODE_PRIVATE;


public class SettingFragment extends Fragment {

    View rootView;
    TextView logoutS;
    Button btn;
    EditText edname,edmob,edpass;
    String Sysname,uid;
    public SettingFragment() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_setting, container, false);
        SharedPreferences sh=getActivity().getSharedPreferences("MysharedPref", Context.MODE_PRIVATE);
        Sysname=sh.getString("Sysname","");
        findwidgest();



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
                    edname.setText(sobj.getStudent_name());
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
        edname=rootView.findViewById(R.id.editName);
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