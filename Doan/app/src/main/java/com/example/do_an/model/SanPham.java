package com.example.do_an.model;

import android.graphics.Bitmap;

import java.io.Serializable;

public class SanPham implements Serializable {
    private String NamePro;
    private String Status;
    private String Mota;
    private byte[] Hinh;
    private Integer IDPro;
    private Integer Loai;

    public String getMota() {
        return Mota;
    }

    public void setMota(String mota) {
        Mota = mota;
    }

    public byte[] getHinh() {
        return Hinh;
    }

    public void setHinh(byte[] hinh) {
        this.Hinh = hinh;
    }

    public Integer getIDPro() {
        return IDPro;
    }

    public void setIDPro(Integer IDPro) {
        this.IDPro = IDPro;
    }

    public Integer getLoai() {
        return Loai;
    }

    public void setLoai(Integer loai) {
        Loai = loai;
    }

    public SanPham(String namePro, String status, String mota, byte[] hinh, Integer IDPro, Integer loai) {
        NamePro = namePro;
        Status = status;
        Mota = mota;
        this.Hinh = hinh;
        this.IDPro = IDPro;
        Loai = loai;
    }

    public SanPham() {
    }

    public SanPham(int IDPro, String namePro, String mota, int loai, String status) {

        NamePro = namePro;
        Status = status;
       this.IDPro=IDPro;
       this.Mota=mota;
       this.Loai=loai;
    }

    public String getNamePro() {
        return NamePro;
    }

    public void setNamePro(String namePro) {
        NamePro = namePro;
    }



    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }



    public String toString() {
        return IDPro.toString()+". "+NamePro+" - "+Status;
    }
}
