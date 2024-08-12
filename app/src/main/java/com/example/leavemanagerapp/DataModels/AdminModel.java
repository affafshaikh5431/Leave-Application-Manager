package com.example.leavemanagerapp.DataModels;

public class AdminModel
{
    String roleid;
    String name;
    String password;

    public AdminModel() {
    }

    public AdminModel(String roleid, String name, String password) {
        this.roleid = roleid;
        this.name = name;
        this.password = password;
    }

    public String getRoleid() {
        return roleid;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
