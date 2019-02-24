package com.halfbyte.greedywallet.models;

import java.sql.Date;

public class ItemsBoughtHistory {
    public String date;
    public Item item;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
