package com.halfbyte.greedywallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.halfbyte.greedywallet.OcrCaptureActivity;
import com.halfbyte.greedywallet.R;
import com.halfbyte.greedywallet.models.Item;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.concurrent.Semaphore;

public class Main extends AppCompatActivity {
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("items");
        //final Semaphore semaphore = new Semaphore(0);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        Item newItem = new Item();
                        HashMap value = (HashMap) data.getValue();
                        newItem.setKey(data.getKey());
                        newItem.setCategory((String) value.get("category"));
                        newItem.setPrice((Double) value.get("price"));
                        OcrDetectorProcessor.items.add(newItem);
                        OcrDetectorProcessor.itemsName.add(newItem.getKey());
                        System.out.println(newItem);
                    }
                }
                //semaphore.release();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
               // semaphore.release();
            }
        });

        setContentView(R.layout.main);
    }
    public void onButtonClickScanner(View view) {

        Intent intent=new Intent(this,OcrCaptureActivity.class);
        startActivity(intent);
    }
    public void onButtonClickManual(View view) {
        Intent intent=new Intent(this,AddManually.class);
        startActivity(intent);
    }
}
