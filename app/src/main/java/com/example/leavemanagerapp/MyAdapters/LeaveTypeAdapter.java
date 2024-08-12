package com.example.leavemanagerapp.MyAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.leavemanagerapp.DataModels.Classes;
import com.example.leavemanagerapp.DataModels.LeaveTypes;
import com.example.leavemanagerapp.R;

import java.util.ArrayList;

public class LeaveTypeAdapter extends ArrayAdapter<LeaveTypes> {
    public LeaveTypeAdapter(Context context, ArrayList<LeaveTypes> lt)

    {
        super(context,0,lt);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=convertView;
        if(view==null)
        {
            view= LayoutInflater.from(getContext()).inflate(R.layout.leave_list,parent,false);
        }
        LeaveTypes lt=getItem(position);
        int i=position+1;
        TextView showLeaveTypeName=view.findViewById(R.id.showLeaveTypeName);
        showLeaveTypeName.setText(String.valueOf(i)+". "+lt.getName());
        TextView editClsbtn=view.findViewById(R.id.editbtn);
        return view;
    }
}
