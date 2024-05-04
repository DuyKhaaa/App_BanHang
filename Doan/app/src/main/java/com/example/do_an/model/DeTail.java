package com.example.do_an.model;

import java.io.Serializable;

public class DeTail implements Serializable {
    private int maDon1;
    private int maCT;
    private int maSP;
    private int maOptions;
    private int soLuong;
    private int tongTien;

    public DeTail() {
    }

    public DeTail(int maDon1, int maCT, int maSP, int maOptions, int soLuong, int tongTien) {
        this.maDon1 = maDon1;
        this.maCT = maCT;
        this.maSP = maSP;
        this.maOptions = maOptions;
        this.soLuong = soLuong;
        this.tongTien = tongTien;
    }

    public int getMaDon1() {
        return maDon1;
    }

    public void setMaDon1(int maDon1) {
        this.maDon1 = maDon1;
    }

    public int getMaCT() {
        return maCT;
    }

    public void setMaCT(int maCT) {
        this.maCT = maCT;
    }

    public int getMaSP() {
        return maSP;
    }

    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }

    public int getMaOptions() {
        return maOptions;
    }

    public void setMaOptions(int maOptions) {
        this.maOptions = maOptions;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }
}
