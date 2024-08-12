package com.example.leavemanagerapp.DataModels;

public class NoticeModel
{
    String unique_id;
    String date;
    String message;

    public NoticeModel()
    {
    }

    public NoticeModel(String unique_id, String date, String message) {
        this.unique_id = unique_id;
        this.date = date;
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public String getMessage() {
        return message;
    }
}

