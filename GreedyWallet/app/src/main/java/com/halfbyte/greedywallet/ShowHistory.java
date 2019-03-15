package com.halfbyte.greedywallet;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.TextView;

public class ShowHistory extends AppCompatActivity {

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.showhistory);

        Context ctxt = this;
        ViewGroup screen = (ViewGroup) findViewById(R.id.historyview);

        for (String item : HistoryManager.getInstance().getItems()) {
            TextView itemView = new TextView(ctxt);
            itemView.setText(item);

            for (String purchaseDate : HistoryManager.getInstance().getItemPurchaseDates(item)) {
                TextView purchaseDateView = new TextView(ctxt);
                purchaseDateView.setText(purchaseDate);

                screen.addView(purchaseDateView);
            }

            screen.addView(itemView);
        }
    }
}