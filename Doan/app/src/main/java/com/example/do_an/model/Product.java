package com.example.do_an.model;

import java.io.Serializable;

public class Product implements Serializable {
    private int productId;
    private String productName;
    private String status;
    private byte[] image;
    private String categoryName;
    private String decriptionPro;

    public Product() {
    }

    public Product(int productId, String productName, String status, byte[] image, String categoryName) {
        this.productId = productId;
        this.productName = productName;
        this.status = status;
        this.image = image;
        this.categoryName = categoryName;
    }

    public Product(int productId, String productName, String status, byte[] image, String categoryName, String decriptionPro) {
        this.productId = productId;
        this.productName = productName;
        this.status = status;
        this.image = image;
        this.categoryName = categoryName;
        this.decriptionPro = decriptionPro;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDecriptionPro() {
        return decriptionPro;
    }

    public void setDecriptionPro(String decriptionPro) {
        this.decriptionPro = decriptionPro;
    }
}
