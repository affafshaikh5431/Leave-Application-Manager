package com.example.leavemanagerapp.MyAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.leavemanagerapp.DataModels.Classes;
import com.example.leavemanagerapp.R;

import java.util.ArrayList;

public class ClassAdapter  extends ArrayAdapter<Classes>
{
    public ClassAdapter(Context context, ArrayList<Classes> c)

    {
        super(context,0,c);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=convertView;
        if(view==null)
        {
            view= LayoutInflater.from(getContext()).inflate(R.layout.class_list,parent,false);

        }
        Classes cls=getItem(position);
        TextView showClassName=view.findViewById(R.id.showClsName);
        int i=position+1;
        showClassName.setText(String.valueOf(i)+". "+cls.getClassName());
        TextView editClsbtn=view.findViewById(R.id.editbtn);
        return view;
    }
}
