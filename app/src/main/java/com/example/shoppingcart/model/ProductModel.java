package com.example.shoppingcart.model;

public class ProductModel {
    public boolean isCouponApplied;
    private String productName;
    private String productSize;
    private int productQuantity;
    private double productPrize;

    private double originalProductPrize;


    public ProductModel(String productName, String productSize, int productQuantity, double productPrize) {
        this.productName = productName;
        this.productSize = productSize;
        this.productQuantity = productQuantity;
        this.productPrize = productPrize;
        this.isCouponApplied = false;
        this.originalProductPrize = productPrize;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductSize() {
        return productSize;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public double getProductPrize() {
        return productPrize;
    }

    public void setProductPrize(double productPrize) {
        this.productPrize = productPrize;
    }

    public boolean isCouponApplied() {
        return isCouponApplied;
    }

    public void setCouponApplied(boolean couponApplied){
        isCouponApplied = couponApplied;
    }

    public double getOriginalProductPrize() {
        return originalProductPrize;
    }

    public void setOriginalProductPrize(double originalProductPrize) {
        this.originalProductPrize = originalProductPrize;
    }
}
