package com.example.leavemanagerapp.DataModels;

public class LeaveRequestModel
{


    String unique_id;
    String name;
    String cls;
    String leaveType;
    String reason;
    String fromDate;
    String toDate;
    String status;

    public LeaveRequestModel() {
    }

    public LeaveRequestModel(String unique_id, String name, String cls, String leaveType, String reason, String fromDate, String toDate, String status) {
        this.unique_id = unique_id;
        this.name = name;
        this.cls = cls;
        this.leaveType = leaveType;
        this.reason = reason;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.status = status;
    }

    public String getUnique_id() {
        return unique_id;
    }

    public void setUnique_id(String unique_id) {
        this.unique_id = unique_id;
    }

    public String getName() {
        return name;
    }

    public String getCls() {
        return cls;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public String getReason() {
        return reason;
    }

    public String getFromDate() {
        return fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
