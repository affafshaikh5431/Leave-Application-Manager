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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.leavemanagerapp.DataModels.LeaveRequestModel;
import com.example.leavemanagerapp.DataModels.NoticeModel;
import com.example.leavemanagerapp.R;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class NoticeViewRequest extends ArrayAdapter<NoticeModel>
{
    LinearLayout lin;
    NoticeModel nm;
    public NoticeViewRequest(Context context, ArrayList<NoticeModel> nm)
    {
        super(context, 0,nm);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.viewnotice_list, parent, false);

        }

        FirebaseDatabase.getInstance().getReference("LeaveRequestModel");
        nm = getItem(position);



        int i = position + 1;


        TextView date=view.findViewById(R.id.date);
        date.setText(String.valueOf(i)+". Notice. Date: "+nm.getDate());


        TextView subject=view.findViewById(R.id.subject);
        subject.setText("Message: "+nm.getMessage());


        LinearLayout lin=view.findViewById(R.id.matter);
        lin.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        TextView show=view.findViewById(R.id.showMatter);
        show.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {


                show.setText("View");

                int check=(lin.getVisibility()==View.GONE)? View.VISIBLE:View.GONE;
                TransitionManager.beginDelayedTransition(lin,new AutoTransition());
                lin.setVisibility(check);


            }
        });

        return view;
    }

}
