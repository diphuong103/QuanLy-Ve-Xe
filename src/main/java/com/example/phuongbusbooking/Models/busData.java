package com.example.phuongbusbooking.Models;

import javafx.scene.chart.PieChart;

import java.sql.Date;

public class busData {

    private int busId;

    private String location;
    private String status;
    private double price;
    private Date day_go;

    public busData(int busId, String location, String status, double price, Date day_go) {
        this.busId = busId;
        this.location = location;
        this.status = status;
        this.price = price;
        this.day_go = day_go;
    }

    public busData() {

    }

    public int getBusId() {
        return busId;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getDay_go() {
        return day_go;
    }

    public void setDay_go(Date day_go) {
        this.day_go = day_go;
    }
}
