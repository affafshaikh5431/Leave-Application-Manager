package com.example.leavemanagerapp.DataModels;

public class Staff
{

    String unique_id;
    String staff_name;
    String staff_no;
    String staff_id;
    String password;
    String status;
    String roleid;

    public Staff() {
    }

    public Staff( String unique_id,String staff_name,String staff_no, String staff_id, String password, String status,String roleid) {
        this.unique_id=unique_id;
        this.staff_name = staff_name;
        this.staff_no=staff_no;
        this.staff_id = staff_id;
        this.password = password;
        this.status = status;
        this.roleid=roleid;
    }

    public Staff(String staff_name, String staff_id, String status) {
        this.staff_name = staff_name;
        this.staff_no=staff_no;
        this.staff_id = staff_id;
        this.status = status;
    }

    public void setStaff_name(String staff_name) {
        this.staff_name = staff_name;
    }

    public String getStaff_no() { return staff_no; }

    public void setStaff_no(String staff_no){this.staff_no=staff_no;}

    public void setStaff_id(String staff_id) {
        this.staff_id = staff_id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUnique_id() {
        return unique_id;
    }

    public String getStaff_name() {
        return staff_name;
    }

    public String getStaff_id() {
        return staff_id;
    }

    public String getPassword() {
        return password;
    }

    public String getStatus() {
        return status;
    }

    public String getRoleid() {return roleid; }
}
