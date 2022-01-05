package com.example.warkopproject.model;

import java.io.Serializable;

public class History implements Serializable {
    String Date, key;

    public History(){

    }

    public String getDate() {
        return Date;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setDate(String date) {
        Date = date;
    }

    public History(String date) {
        Date = date;
    }
}
