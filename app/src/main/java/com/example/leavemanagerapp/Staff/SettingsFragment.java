package com.example.leavemanagerapp.Staff;

import android.content.Context;
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
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.leavemanagerapp.DataModels.Staff;
import com.example.leavemanagerapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SettingsFragment extends Fragment {

    View rootView;
    EditText edname,edmob,edid,edpass;
    String Sysname,uid;
    Button btn;
    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        rootView= inflater.inflate(R.layout.fragment_settings, container, false);
        SharedPreferences sh=getContext().getSharedPreferences("MysharedPref", Context.MODE_PRIVATE);
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
                updatedetails();
            }
        });
        return rootView;

    }

    private void updatedetails()
    {
        if(TextUtils.isEmpty(edmob.getText().toString())||TextUtils.isEmpty(edpass.getText().toString()))
        {
            Toast.makeText(getActivity(), "Field cannot be Empty!!", Toast.LENGTH_SHORT).show();
        }

        FirebaseDatabase.getInstance().getReference("Staff").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    Staff ss=snapshot.getValue(Staff.class);
                     ss.setStaff_no(edmob.getText().toString());
                    ss.setPassword(edpass.getText().toString());
                    FirebaseDatabase.getInstance().getReference("Staff").child(uid).setValue(ss).addOnCompleteListener(new OnCompleteListener<Void>() {
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
        loadstaffdetails();
    }

    private void loadstaffdetails()
    {
        FirebaseDatabase.getInstance().getReference("Staff")
                .orderByChild("staff_name")
                .equalTo(Sysname).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                for(DataSnapshot childSnapshot : snapshot.getChildren()) {
                    Staff sobj = childSnapshot.getValue(Staff.class);
                    uid=sobj.getUnique_id();

                    edmob.setText(sobj.getStaff_no());
                    edpass.setText(sobj.getPassword());
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
        btn=rootView.findViewById(R.id.updateBtn);
        edpass=rootView.findViewById(R.id.editpassword);
    }
}