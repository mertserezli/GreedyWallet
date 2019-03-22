package com.halfbyte.greedywallet;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Comparator;

public class ShowHistory extends AppCompatActivity {

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.showhistory);

        Context ctxt = this;
        ViewGroup screen = (ViewGroup) findViewById(R.id.historyview);

        screen.removeAllViews();

        String[] items = new String[HistoryManager.getInstance().getItems().size()];
        HistoryManager.getInstance().getItems().toArray(items);
        for (String item : items) {
            TextView itemView = new TextView(ctxt);
            itemView.setText(item);

            ArrayList<String> purchaseDates = HistoryManager.getInstance().getItemPurchaseDates(item);
            purchaseDates.sort(Comparator.comparing(String::toString).reversed());
            for (String purchaseDate : purchaseDates) {
                TextView purchaseDateView = new TextView(ctxt);
                purchaseDateView.setText(purchaseDate);

                screen.addView(purchaseDateView);
            }

            screen.addView(itemView);
        }
    }
}