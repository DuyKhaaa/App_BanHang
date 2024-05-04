package com.example.do_an.model;

import java.io.Serializable;

public class Options implements Serializable {
    private int idOpt;
    private int ProductID;
    private String option;
    private int price;

    public Options() {
    }

    public Options(int idOpt, int productID, String option, int price) {
        this.idOpt = idOpt;
        ProductID = productID;
        this.option = option;
        this.price = price;
    }

    public int getIdOpt() {
        return idOpt;
    }

    public void setIdOpt(int idOpt) {
        this.idOpt = idOpt;
    }

    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int productID) {
        ProductID = productID;
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
}
