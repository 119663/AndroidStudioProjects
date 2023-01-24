package com.evelyne.labs.myapplication.model;

public class BookNow {
    public String capacity, date, location, time;

    public BookNow(String location) {
    }

    public BookNow(String capacity, String date, String location, String time) {
        this.capacity = capacity;
        this.date = date;
        this.time = time;
        this.location=location;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}