package com.example.leavemanagerapp.MyAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.leavemanagerapp.DataModels.ClassTeacher;
import com.example.leavemanagerapp.DataModels.Classes;
import com.example.leavemanagerapp.R;

import java.util.ArrayList;

public class ClassTeacherAdapter  extends ArrayAdapter<ClassTeacher>
{
    public ClassTeacherAdapter(Context context, ArrayList<ClassTeacher> c)

    {
        super(context,0,c);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=convertView;
        if(view==null)
        {
            view= LayoutInflater.from(getContext()).inflate(R.layout.classteacher_list,parent,false);

        }
        ClassTeacher cls=getItem(position);
        TextView clsname=view.findViewById(R.id.clsName);
        int i=position+1;
        clsname.setText(String.valueOf(i)+". "+cls.getClassname());
        TextView staffname=view.findViewById(R.id.staffname);
        staffname.setText(cls.getStaffname());
        return view;
    }
}
