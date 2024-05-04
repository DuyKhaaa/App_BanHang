package com.example.do_an.model;

import java.io.Serializable;

public class KhachHang implements Serializable {
    private int IDCus;
    private String Name;
    private String Pass;
    private String Email;
    private int Phone;

    public KhachHang() {
    }

    public KhachHang(int IDCus, String name, String pass, String email, int phone) {
        this.IDCus = IDCus;
        Name = name;
        Pass = pass;
        Email = email;
        Phone = phone;
    }

    public int getIDCus() {
        return IDCus;
    }

    public void setIDCus(int IDCus) {
        this.IDCus = IDCus;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String pass) {
        Pass = pass;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public int getPhone() {
        return Phone;
    }

    public void setPhone(int phone) {
        Phone = phone;
    }

    @Override
    public String toString() {
        return
                Name + ' ' +
                        Email ;
    }
}