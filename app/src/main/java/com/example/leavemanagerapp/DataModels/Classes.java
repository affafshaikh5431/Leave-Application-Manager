package com.example.leavemanagerapp.DataModels;

public class Classes
{
    String unique_id;
    String className;

    public Classes() {

    }

    public Classes(String unique_id,String className)
    {
        this.unique_id=unique_id;
        this.className = className;
    }

    public  String getUnique_id(){return unique_id;}
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
