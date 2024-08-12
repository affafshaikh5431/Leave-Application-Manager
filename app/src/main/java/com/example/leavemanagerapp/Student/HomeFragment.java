package com.example.leavemanagerapp.Student;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.leavemanagerapp.DataModels.LeaveRequestModel;
import com.example.leavemanagerapp.MyAdapters.RequestAdapter;
import com.example.leavemanagerapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    ListView lst;
    View rootView;
    String Sysname;
    LeaveRequestModel obj=new LeaveRequestModel();
    ArrayList<LeaveRequestModel> lrm=new ArrayList<>();

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView= inflater.inflate(R.layout.fragment_home, container, false);
        findwidgets();
        SharedPreferences sh=getContext().getSharedPreferences("MysharedPref",getContext().MODE_PRIVATE);
        Sysname=sh.getString("Sysname","");


        return rootView;
    }
    @Override
    public void onStart() {
        super.onStart();
        FirebaseDatabase.getInstance().getReference("LeaveRequestModel").orderByChild("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                lrm.clear();

                for (DataSnapshot postSnapshot:snapshot.getChildren())
                {

                    obj=postSnapshot.getValue(LeaveRequestModel.class);
                    
                    if(obj.getName().equals(Sysname)) {

                        lrm.add(obj);
                    }
                }
                RequestAdapter adp = new RequestAdapter(getContext(), lrm);
                lst.setAdapter(adp);
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

    private void findwidgets() {

        lst=rootView.findViewById(R.id.listv);
    }
}