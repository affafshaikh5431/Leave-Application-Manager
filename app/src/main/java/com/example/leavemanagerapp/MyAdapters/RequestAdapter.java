package com.example.leavemanagerapp.MyAdapters;

import android.app.VoiceInteractor;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.leavemanagerapp.DataModels.LeaveRequestModel;
import com.example.leavemanagerapp.DataModels.Staff;
import com.example.leavemanagerapp.R;

import java.util.ArrayList;

public class RequestAdapter extends ArrayAdapter<LeaveRequestModel> {
    public RequestAdapter(Context context, ArrayList<LeaveRequestModel> lrm) {
        super(context, 0, lrm);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.request_list, parent, false);

        }

        LeaveRequestModel lrm = getItem(position);
        int i = position + 1;
        TextView leavetype = view.findViewById(R.id.leavetype);
        //leavetype.setText(String.valueOf(i)+". "+lrm.getLeaveType());
        leavetype.setText(lrm.getLeaveType());

        TextView Fdate = view.findViewById(R.id.Fdate);
        Fdate.setText("Request Sent on :"+lrm.getFromDate());

        TextView status = view.findViewById(R.id.status);
        status.setText("Status: "+lrm.getStatus());

        return view;
    }
}
