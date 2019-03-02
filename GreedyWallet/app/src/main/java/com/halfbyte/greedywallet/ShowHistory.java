package com.halfbyte.greedywallet;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ShowHistory extends AppCompatActivity {

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.showhistory);

        Context ctxt = this;
        ViewGroup screen = (ViewGroup)findViewById(R.id.mainview);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child("itemsBoughtHistory");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, ArrayList<String>> history = new HashMap<>();

                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String itemName = ds.child("item").child("isim").getValue().toString();
                    String purchaseDate = ds.child("date").getValue().toString();

                    if(history.containsKey(itemName)) {
                        history.get(itemName).add(purchaseDate);
                    }
                    else {
                        history.put(itemName, new ArrayList<String>(Arrays.asList(purchaseDate)));
                    }
                }

                for(String item : history.keySet()) {
                    TextView itemView = new TextView(ctxt);
                    itemView.setText(item);

                    for(String purchaseDate : history.get(item)) {
                        TextView purchaseDateView = new TextView(ctxt);
                        purchaseDateView.setText(purchaseDate);

                        screen.addView(purchaseDateView);
                    }

                    screen.addView(itemView);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }
        });
    }
}
