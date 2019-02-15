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

    private String subCategory;

    private int sale;

    private int popularity;

    @Override
    public String toString(){
        return "Key:"+this.key+"\n" +
                "Price:"+this.price+"\n" +
                "Category:"+this.category+"\n" +
                "subCategoty:"+this.subCategory+"\n" +
                "sale:"+this.sale+"\n" +
                "popularity:"+this.popularity ;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public int getSale() {
        return sale;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }
}
