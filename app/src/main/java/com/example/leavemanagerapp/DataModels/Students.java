package com.example.leavemanagerapp.DataModels;

public class Students
{
    String unique_id;
    String student_name;
    String student_no;
    String student_id;
    String className;
    String rollno;
    String student_password;
    String roleid;

    public Students() {
    }

    public Students(String unique_id, String student_name,String student_no, String student_id, String className, String rollno, String student_password, String roleid) {
        this.unique_id = unique_id;
        this.student_name = student_name;
        this.student_no=student_no;
        this.student_id = student_id;
        this.className = className;
        this.rollno = rollno;
        this.student_password = student_password;
        this.roleid = roleid;
    }

    public String getUnique_id() {
        return unique_id;
    }

    public String getStudent_name() {
        return student_name;
    }

    public String getStudent_no(){return student_no;}

    public String getRollno() {
        return rollno;
    }

    public String getStudent_id() {
        return student_id;
    }

    public String getStudent_password() {
        return student_password;
    }

    public String getClassName() {
        return className;
    }

    public String getRoleid() {
        return roleid;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public void setStudent_no(String student_no){this.student_no=student_no;}

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setRollno(String rollno) {
        this.rollno = rollno;
    }

    public void setStudent_password(String student_password) {
        this.student_password = student_password;
    }
}
