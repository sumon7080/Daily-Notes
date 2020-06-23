package com.example.dailynote;

import java.io.Serializable;
import java.util.Calendar;

public class Model implements Serializable {



    long id;
    String name;
    String details;
    String date;
    String time;


    public Model() {
    }

    public Model(String name, String details, String date, String time) {
        this.name = name;
        this.details = details;
        this.date = date;
        this.time = time;
    }

    public Model(int id, String name, String details, String date, String time) {
        this.id = id;
        this.name = name;
        this.details = details;
        this.date = date;
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDate() {


        return date;
    }

    public void setDate(String date) {

        this.date = date;
    }

    public String getTime() {


        return time;
    }

    private String pad(int i) {
        if(i<10)
            return  "0"+i;
        return String.valueOf(i);
    }

    public void setTime(String time) {
        this.time = time;
    }
}
