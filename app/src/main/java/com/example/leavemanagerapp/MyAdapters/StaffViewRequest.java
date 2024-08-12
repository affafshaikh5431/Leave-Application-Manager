package com.example.leavemanagerapp.MyAdapters;

import android.animation.LayoutTransition;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leavemanagerapp.DataModels.LeaveRequestModel;
import com.example.leavemanagerapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StaffViewRequest extends ArrayAdapter<LeaveRequestModel>
{
    LeaveRequestModel lrm,obj;
    String id="";
    DatabaseReference db;
    public StaffViewRequest(Context context, ArrayList<LeaveRequestModel> lrm) {
        super(context, 0,lrm);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.staffviewrequest_list, parent, false);


        }

        FirebaseDatabase.getInstance().getReference("LeaveRequestModel");
        lrm = getItem(position);



        int i = position + 1;
        TextView name=view.findViewById(R.id.name);
        name.setText(String.valueOf(i)+".  "+lrm.getName());

        TextView date=view.findViewById(R.id.date);
        date.setText(lrm.getFromDate());

        TextView subject=view.findViewById(R.id.subject);
        subject.setText("Subject: Request for "+lrm.getLeaveType()+" Leave");

        String matter="Respected Staff,\n\t\t\t\t\t I am "+lrm.getName()+" from Class "+lrm.getCls()+
                " Requesting you to allow me for a leave because of";
        String closingMatter=" .So please accept my leave request application.";
        String regards="\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tYours faithfully,";
        String endname="\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t    "+lrm.getName()+"";
        TextView reson=view.findViewById(R.id.reson);
        reson.setText(matter+" "+lrm.getReason()+closingMatter+regards+endname);



        LinearLayout lin=view.findViewById(R.id.matter);
        lin.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        TextView show=view.findViewById(R.id.showMatter);
        show.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {


                    show.setText("View Less");

                int check=(lin.getVisibility()==View.GONE)? View.VISIBLE:View.GONE;
                TransitionManager.beginDelayedTransition(lin,new AutoTransition());
                lin.setVisibility(check);


            }
        });



        Button rejectbtn=view.findViewById(R.id.rejectBtn);
        rejectbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id=getItem(position).getUnique_id();
                updateStatus("Rejected");
            }
        });

        Button accecptBtn=view.findViewById(R.id.acceptBtn);
        accecptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                id=getItem(position).getUnique_id();
                updateStatus("Accepted");

            }
        });
        return view;




    }

    private void updateStatus(String s)
    {
        FirebaseDatabase.getInstance().getReference("LeaveRequestModel").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    LeaveRequestModel obj=snapshot.getValue(LeaveRequestModel.class);
                    obj.setStatus(s);


                    FirebaseDatabase.getInstance().getReference("LeaveRequestModel").child(id).setValue(obj).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(Task<Void> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(getContext(), "Status Updated", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(getContext(), "Failed to Update the Status", Toast.LENGTH_SHORT).show();
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


}
