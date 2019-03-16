package com.halfbyte.greedywallet;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class HistoryManager {
    private static HistoryManager historyManager = new HistoryManager();
    private Map<String, ArrayList<String>> history = new ConcurrentHashMap<>();

    private HistoryManager(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child("itemsBoughtHistory");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String itemName = ds.child("item").child("isim").getValue().toString();
                    String purchaseDate = ds.child("date").getValue().toString();

                    if (history.containsKey(itemName)) {
                        history.get(itemName).add(purchaseDate);
                    } else {
                        history.put(itemName, new ArrayList<String>(Arrays.asList(purchaseDate)));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public static HistoryManager getInstance(){
        return historyManager;
    }

    public Set<String> getItems(){return history.keySet();}

    public ArrayList<String> getItemPurchaseDates(String item){return history.get(item);}
}
