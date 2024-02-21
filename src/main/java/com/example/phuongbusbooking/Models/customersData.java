package com.example.phuongbusbooking.Models;

import java.sql.Date;

public class customersData {
    private int Stt;
    private String Id;
    private Integer Ticket;
    private String FullName;
    private String PhoneNumber;
    private Integer busId;
    private String location;
    private Date Day_go;
    private double income;


    public customersData() {

    }

    // Constructor có tham số để thiết lập các trường dữ liệu
    public customersData(int stt, String ID, Integer ticket, String fullName, String phoneNumber,
                         Integer busId, String location, Date day_go, double income) {
        this.Stt = stt;
        this.Id = ID;
        this.Ticket = ticket;
        this.FullName = fullName;
        this.PhoneNumber = phoneNumber;
        this.busId = busId;
        this.location = location;
        this.Day_go = day_go;
        this.income = income;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public int getStt() {
        return Stt;
    }

    public void setStt(int stt) {
        Stt = stt;
    }


    public Integer getTicket() {
        return Ticket;
    }

    public void setTicket(Integer ticket) {
        Ticket = ticket;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public Integer getBusId() {
        return busId;
    }

    public void setBusId(Integer busId) {
        this.busId = busId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDay_go() {
        return Day_go;
    }

    public void setDay_go(Date day_go) {
        Day_go = day_go;
    }
}


