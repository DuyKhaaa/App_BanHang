package com.example.do_an.model;

import java.io.Serializable;

public class Customer implements Serializable{
    private int iDCus;
    private String name;
    private String email;
    private int phone;

    public Customer() {
    }

    public Customer(int iDCus, String name, String email, int phone) {
        this.iDCus = iDCus;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public int getiDCus() {
        return iDCus;
    }

    public void setiDCus(int iDCus) {
        this.iDCus = iDCus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }
}
