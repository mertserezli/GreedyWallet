package com.halfbyte.greedywallet;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.halfbyte.greedywallet.models.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class DatabaseManager {
    private static DatabaseManager databaseManager = new DatabaseManager();
    public List<Item> items = new CopyOnWriteArrayList<>();

    private DatabaseManager(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("items");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    Item newItem = new Item();
                    HashMap value = (HashMap) data.getValue();
                    newItem.setKey((String) value.get("Isim"));
                    newItem.setCategory((String) value.get("AnaKategori"));
                    newItem.setSubCategory((String) value.get("AltKategori"));
                    String price=((String) value.get("Fiyat"));
                    newItem.setPopularityRank( (Long) value.get("PopularityRank"));
                    if(price.contains(".")){
                        price=price.substring(0,price.indexOf(","));
                        price=price.replace(".","");
                    }
                    newItem.setPrice(Double.parseDouble((price.replaceAll(" ","").replaceAll(",","."))));
                    items.add(newItem);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public static DatabaseManager getInstance(){
            return databaseManager;
    }

    public boolean hasItem(String itemName){
        for(Item i: items){
            if(i.getIsim().equalsIgnoreCase(itemName))
                return true;
        }
        return false;
    }

    public boolean containsItem(String itemName){
        for(Item i: items){
            if(i.getIsim().toLowerCase().contains(itemName.toLowerCase()))
                return true;
        }
        return false;
    }

    public Item findItem(String itemName){
        for(Item i: items){
            if(i.getIsim().equalsIgnoreCase(itemName))
                return i;
        }
        return null;
    }

}
