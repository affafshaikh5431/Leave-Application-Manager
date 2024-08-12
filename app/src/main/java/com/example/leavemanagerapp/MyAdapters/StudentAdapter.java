package com.example.leavemanagerapp.MyAdapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.leavemanagerapp.Admin.EditStaff;
import com.example.leavemanagerapp.Admin.EditStudent;
import com.example.leavemanagerapp.DataModels.Classes;
import com.example.leavemanagerapp.DataModels.Students;
import com.example.leavemanagerapp.R;

import java.util.ArrayList;

public class StudentAdapter  extends ArrayAdapter<Students>
{
    public StudentAdapter(Context context, ArrayList<Students> c)

    {
        super(context,0,c);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=convertView;
        if(view==null)
        {
            view= LayoutInflater.from(getContext()).inflate(R.layout.student_list,parent,false);
        }
        Students stud=getItem(position);
        int i=position+1;
        TextView showClassName=view.findViewById(R.id.showClassName);
        TextView showStudName=view.findViewById(R.id.showStudName);
        TextView editBtn=view.findViewById(R.id.editbtn);
        showStudName.setText(String.valueOf(i)+". "+stud.getStudent_name());
        showClassName.setText(stud.getClassName());

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), EditStudent.class);
                intent.putExtra("unique_id",stud.getUnique_id());

                intent.putExtra("name",stud.getStudent_name());
                intent.putExtra("mob",stud.getStudent_no());
                intent.putExtra("id",stud.getStudent_id());
                intent.putExtra("class",stud.getClassName());
                intent.putExtra("rollno",stud.getRollno());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            }
        });



        return view;
    }
}


