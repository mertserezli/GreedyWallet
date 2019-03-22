package com.halfbyte.greedywallet.models;

import android.support.annotation.NonNull;

public class Item implements Comparable {
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

    private String discount;

    private String market;

    public void setMarket(long id){
        if(id==0)
            market="Carrefour";
        else if(id==1)
            market="Migros";
    }

    public String getMarket() {
        return market;
    }

    public void setIsim(String isim) {
        Isim = isim;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    @Override
    public String toString(){
        return "Isim:"+this.Isim+"\n" +
                "Price:"+this.price+"\n" +
                "Category:"+this.category ;
    }


    @Override
    public int compareTo(@NonNull Object o) {
        Item i=(Item)o;
        int discountThis=parseDiscount(getDiscount().replaceAll("%","").replaceAll(" ",""));
        int discountAnother=parseDiscount(i.getDiscount().replaceAll("%","").replaceAll(" ",""));

        if(discountThis>discountAnother)
            return -1;
        else if(discountThis==discountAnother)
            return 0;
        else
            return 1;
    }

    public int parseDiscount(String discount){
        String result = "";
        for (int i = 0; i <discount.length() ; i++) {
            if(Character.isDigit(discount.charAt(i)))
                result+=discount.charAt(i)+"";
        }
        return Integer.parseInt(result);
    }
}
