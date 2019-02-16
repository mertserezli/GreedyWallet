package com.halfbyte.greedywallet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.halfbyte.greedywallet.models.Item;

import java.util.ArrayList;
import java.util.HashMap;

public class AddManually extends AppCompatActivity {

    static ArrayList<Item> items=new ArrayList<>();
    ArrayList<String> categoriesInList = new ArrayList<>();
    ArrayAdapter<String> adapter;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.addmanually);

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                categoriesInList);
        ListView list = (ListView)findViewById(R.id.categoryList);
        list.setAdapter(adapter);

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
                    String price=((String) value.get("Fiyat"));
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

        final Button addToListButton = (Button) findViewById(R.id.addToList);
        addToListButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText input = (EditText)findViewById(R.id.itemNameInput);
                final String groupName = input.getText().toString();
                System.out.println(items);
                if (categoryExists(groupName)){
                    adapter.add(groupName);
                }
            }
        });

        final Button getOptimizedListButton = (Button) findViewById(R.id.getOptimizedItemList);
        getOptimizedListButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                
            }
        });
    }

    private boolean categoryExists(String categoryName){
        for(Item i: items){
            if(i.getCategory().equals(categoryName)){
                System.err.println("it is true");
                return true;
            }
        }
        return false;
    }

}
