package com.halfbyte.greedywallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.halfbyte.greedywallet.R;
import com.halfbyte.greedywallet.models.Item;

import java.util.HashMap;

public class Main extends AppCompatActivity {
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
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
