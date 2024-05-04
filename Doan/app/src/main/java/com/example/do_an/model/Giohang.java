package com.example.do_an.model;

import java.io.Serializable;

public class Giohang implements Serializable {
    private String namePro;
    private String option;
    private int price;
    private int soLuong;
    private byte[] image;
    private int idProduct;
    private int idOptions;
    private boolean selected;

    public Giohang(String namePro, String option, int price, int soLuong, byte[] image, int idProduct, int idOptions) {
        this.namePro = namePro;
        this.option = option;
        this.price = price;
        this.soLuong = soLuong;
        this.image = image;
        this.idProduct = idProduct;
        this.idOptions = idOptions;
        this.selected=false;
    }

    public Giohang(String namePro, String option, int price, int soLuong, byte[] image) {
        this.namePro = namePro;
        this.option = option;
        this.price = price;
        this.soLuong = soLuong;
        this.image = image;
    }

    public Giohang() {
    }

    public String getNamePro() {
        return namePro;
    }

    public void setNamePro(String namePro) {
        this.namePro = namePro;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public int getIdOptions() {
        return idOptions;
    }

    public void setIdOptions(int idOptions) {
        this.idOptions = idOptions;
    }
    // Các phương thức isSelected() và setSelected() để đọc và ghi giá trị của trường selected
    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}