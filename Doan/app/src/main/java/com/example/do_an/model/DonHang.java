package com.example.do_an.model;

import java.io.Serializable;

public class DonHang implements Serializable {
    private int maDon;
    private String date;
    private int idCus;
    private String address;





    public DonHang(int maDon, String date, int idCus, String address) {
        this.maDon = maDon;
        this.date = date;
        this.idCus = idCus;
        this.address = address;
    }

    public DonHang() {
    }

    public int getMaDon() {
        return maDon;
    }

    public void setMaDon(int maDon) {
        this.maDon = maDon;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getIdCus() {
        return idCus;
    }

    public void setIdCus(int idCus) {
        this.idCus = idCus;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }



    @Override
    public String toString() {
        return + maDon +" - "+ date+"- "+ address ;
    }
}

