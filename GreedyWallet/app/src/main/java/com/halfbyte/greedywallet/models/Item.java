package com.halfbyte.greedywallet.models;

public class Item {
    public String getIsim() {
        return Isim;
    }

    public void setKey(String key) {
        this.Isim = key;
    }

    private String Isim;

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

    public double getPopularityRank() {
        return popularityRank;
    }

    public void setPopularityRank(double popularityRank) {
        this.popularityRank = popularityRank;
    }

    private double popularityRank;

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    private String subCategory;

    @Override
    public String toString(){
        return "Isim:"+this.Isim+"\n" +
                "Price:"+this.price+"\n" +
                "Category:"+this.category ;
    }
}
