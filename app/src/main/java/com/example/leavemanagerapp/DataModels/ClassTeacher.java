package com.example.leavemanagerapp.DataModels;

public class ClassTeacher
{
    String unique_id;
    String classname;
    String staffname;

    public ClassTeacher()
    {
    }

    public ClassTeacher(String unique_id, String classname, String staffname) {
        this.unique_id = unique_id;
        this.classname = classname;
        this.staffname = staffname;
    }

    public ClassTeacher(String classname, String staffname) {
        this.classname = classname;
        this.staffname = staffname;
    }

    public void setStaffname(String staffname) {
        this.staffname = staffname;
    }

    public  void setClassname(String classname){this.classname=classname;}

    public void setUnique_id(String unique_id) {
        this.unique_id = unique_id;
    }

    public String getUnique_id() {
        return unique_id;
    }

    public String getClassname() {
        return classname;
    }

    public String getStaffname() {
        return staffname;
    }
}
