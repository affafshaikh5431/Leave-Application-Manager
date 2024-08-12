package com.example.leavemanagerapp.DataModels;

public class LeaveTypes
{
    String unique_id;
    String name;

    public LeaveTypes() {

    }

    public LeaveTypes(String unique_id, String name) {
        this.unique_id = unique_id;
        this.name = name;
    }

    public String getUnique_id() {
        return unique_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
