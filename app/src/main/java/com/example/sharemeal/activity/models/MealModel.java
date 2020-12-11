package com.example.sharemeal.activity.models;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;
import java.util.Date;

public class MealModel implements Serializable {
    private String name, status, data, pickHour, userid,byWho;
    //   @ServerTimestamp
    //private Date date;

    public MealModel() {
    }

    public MealModel(String name, String data, String pickHour, String status,String userid,String byWho ) {
        this.name = name;
        this.status = status;
        this.data = data;
        this.pickHour = pickHour;
        this.userid=userid;
        this.byWho=byWho;
    }

    public String getByWho() {
        return byWho;
    }

    public void setByWho(String byWho) {
        this.byWho = byWho;
    }

    public void changeStatus(String text){
        status=text;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getPickHour() {
        return pickHour;
    }

    public void setPickHour(String pickHour) {
        this.pickHour = pickHour;
    }
}
