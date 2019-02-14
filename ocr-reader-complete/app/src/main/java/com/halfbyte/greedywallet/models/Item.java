package com.halfbyte.greedywallet.models;

public class Item {
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    private String key;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    private String category;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    private double price;

    @Override
    public String toString(){
        return "Key:"+this.key+"\n" +
                "Price:"+this.price+"\n" +
                "Category:"+this.category ;
    }
}
