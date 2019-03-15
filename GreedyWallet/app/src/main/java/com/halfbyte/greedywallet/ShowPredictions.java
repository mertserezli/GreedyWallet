package com.halfbyte.greedywallet;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Map;

public class ShowPredictions extends AppCompatActivity {

    public void onCreate(Bundle bundle){
        super.onCreate(bundle);

        setContentView(R.layout.showpredictions);

        Context ctxt = this;
        ViewGroup screen = (ViewGroup) findViewById(R.id.predictionsview);

        screen.removeAllViews();

        SharedPreferences sharedPreferences = ctxt.getSharedPreferences("predictions", Context.MODE_PRIVATE);
        Map<String, ?> predictions = sharedPreferences.getAll();
        for(String item : predictions.keySet())
        {
            TextView itemView = new TextView(ctxt);
            itemView.setText(item);
            TextView purchaseDateView = new TextView(ctxt);
            String purchaseDate = (String) predictions.get(item);
            purchaseDateView.setText(purchaseDate);

            screen.addView(purchaseDateView);
            screen.addView(itemView);
        }
    }
}
