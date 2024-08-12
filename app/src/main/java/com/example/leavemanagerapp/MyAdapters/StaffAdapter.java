package com.example.leavemanagerapp.MyAdapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.leavemanagerapp.Admin.EditStaff;
import com.example.leavemanagerapp.DataModels.Staff;

import com.example.leavemanagerapp.R;

import java.util.ArrayList;

public class StaffAdapter extends ArrayAdapter<Staff>
{
    ImageView img;

    public StaffAdapter(Context context, ArrayList<Staff> ss)
    {
        super(context,0,ss);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=convertView;
        if(view==null)
        {
            view= LayoutInflater.from(getContext()).inflate(R.layout.staff_listview,parent,false);

        }

        CardView cardStatus=view.findViewById(R.id.cardstatus);
        TextView status=view.findViewById(R.id.status);

        Staff saveStaff=getItem(position);
        int i=position+1;
        TextView name=view.findViewById(R.id.showName);
        name.setText(String.valueOf(i)+". "+saveStaff.getStaff_name().toString());

        status.setText(saveStaff.getStatus());
        if(status.getText().toString().equals("Active"))
        {
            cardStatus.setCardBackgroundColor(Color.parseColor("#4CAF50"));
        }
        else
        {
            cardStatus.setCardBackgroundColor(Color.parseColor("#c30010"));
        }

        TextView editBtn=view.findViewById(R.id.editbtn);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), EditStaff.class);
                intent.putExtra("unique_id",saveStaff.getUnique_id());
                intent.putExtra("name",saveStaff.getStaff_name());
                intent.putExtra("mob",saveStaff.getStaff_no());
                intent.putExtra("id",saveStaff.getStaff_id());
                intent.putExtra("status",saveStaff.getStatus());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            }
        });

        return view;
    }
}
