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
            int mult = 1;
            for (int i = 0; i < purchaseDates.size(); i++) {
                if(i < purchaseDates.size()-1)
                {
                    if(purchaseDates.get(i).equals(purchaseDates.get(i+1)))
                    {
                        mult++;
                        continue;
                    }
                }
                String purchaseDate = purchaseDates.get(i);
                if(mult > 1)
                {
                    purchaseDate = purchaseDate + " x" + mult;
                    mult = 1;
                }
                TextView purchaseDateView = new TextView(ctxt);
                purchaseDateView.setText(purchaseDate);

                screen.addView(purchaseDateView);
            }

            screen.addView(itemView);
        }
    }
}