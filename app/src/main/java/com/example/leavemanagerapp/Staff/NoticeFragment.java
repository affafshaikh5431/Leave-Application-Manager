package com.example.leavemanagerapp.Staff;

import android.graphics.ColorSpace;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.leavemanagerapp.DataModels.LeaveRequestModel;
import com.example.leavemanagerapp.DataModels.NoticeModel;
import com.example.leavemanagerapp.MyAdapters.NoticeViewRequest;
import com.example.leavemanagerapp.MyAdapters.StaffViewRequest;
import com.example.leavemanagerapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NoticeFragment extends Fragment {

    View rootview;
    ListView lst;

    ArrayList<NoticeModel> lrm=new ArrayList<>();

    public NoticeFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview= inflater.inflate(R.layout.fragment_notice, container, false);
        findwidgets();
        return  rootview;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseDatabase.getInstance().getReference("NoticeModel").orderByChild("date").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                lrm.clear();
                for (DataSnapshot postSnapshot:snapshot.getChildren())
                {
                    NoticeModel obj=postSnapshot.getValue(NoticeModel.class);
                        lrm.add(obj);
                    NoticeViewRequest adp=new NoticeViewRequest(getContext(),lrm);
                    lst.setAdapter(adp);
                    }
                }

            @Override
            public void onCancelled(DatabaseError error) {

            }



        });
    }

    private void findwidgets()
    {
        lst=rootview.findViewById(R.id.lst);
    }
}