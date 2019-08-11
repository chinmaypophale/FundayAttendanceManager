package com.dexterlabs.myapplication;

import java.util.ArrayList;
import java.util.HashMap;

public class Model {

    public String name,school,timestamp,address;
    public String latitude,longitude;
    public String day;

    public Model() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Model(String name, String school, String timestamp, String address, String latitude, String longitude, String day) {
        this.name = name;
        this.school = school;
        this.timestamp = timestamp;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.day = day;
    }




}



