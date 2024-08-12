package com.example.leavemanagerapp.Staff;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.leavemanagerapp.DataModels.ClassTeacher;
import com.example.leavemanagerapp.DataModels.LeaveRequestModel;
import com.example.leavemanagerapp.MyAdapters.StaffViewRequest;
import com.example.leavemanagerapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;



public class StaffViewRequestFragment extends Fragment {


    View rootView;
    ListView lst;
    boolean hasPendingRequests = false;
    String Sysname,sclsname;
    ArrayList<LeaveRequestModel> lrm=new ArrayList<>();

    public StaffViewRequestFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView= inflater.inflate(R.layout.fragment_staff_view_request, container, false);

        SharedPreferences sh=getContext().getSharedPreferences("MysharedPref", Context.MODE_PRIVATE);
        Sysname=sh.getString("Sysname","");

        findwidgest();
        return rootView;
    }
    @Override
    public void onStart() {
        super.onStart();
        classteacher();
    }

    private void classteacher()
    {
        FirebaseDatabase.getInstance().getReference("ClassTeacher")
                .orderByChild("staffname")
                .equalTo(Sysname).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                for(DataSnapshot childSnapshot : snapshot.getChildren()) {
                    ClassTeacher ctobj = childSnapshot.getValue(ClassTeacher.class);
                   String name=ctobj.getClassname();
                    addlist(name);
                    break;

                }

            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }


    private void addlist(String name)
    {

        FirebaseDatabase.getInstance().getReference("LeaveRequestModel").orderByChild("fromDate").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                lrm.clear();
                for (DataSnapshot postSnapshot:snapshot.getChildren())
                {
                    LeaveRequestModel obj=postSnapshot.getValue(LeaveRequestModel.class);
                    if(obj.getStatus().equals("Pending")&&obj.getCls().equals(name)) {
                        lrm.add(obj);
                        hasPendingRequests=true;
                    }
                }

                if(hasPendingRequests)
                {

                    StaffViewRequest adp=new StaffViewRequest(getContext(),lrm);
                    lst.setAdapter(adp);
                }
                else
                {
                    showAlertDialog("Leave Request are only show to the respective Class Teachers!!");
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }
    private void showAlertDialog(String message) {
        if (getContext() != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Alert");
            builder.setMessage(message);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    NoticeFragment NF = new NoticeFragment();
                    FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragmentStaff, NF); // R.id.fragment_container is the ID of the container where fragments are placed
                    transaction.addToBackStack(null); // Optional: Add the transaction to the back stack
                    transaction.commit();


                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }


    private void findwidgest() {
        lst=rootView.findViewById(R.id.lst);
    }


}