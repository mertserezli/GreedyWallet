package com.halfbyte.greedywallet;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.halfbyte.greedywallet.models.Item;

import java.util.ArrayList;

public class ItemAdapter extends ArrayAdapter<Item> {

    public ItemAdapter(@NonNull Context context, @NonNull ArrayList<Item> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Item item= getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_layout, parent, false);
        }

        TextView itemDiscount = (TextView) convertView.findViewById(R.id.discount);
        TextView itemName = (TextView) convertView.findViewById(R.id.urunName);
        TextView itemMarket =(TextView) convertView.findViewById(R.id.urunMarket);

        itemDiscount.setText(item.getDiscount());
        itemName.setText(item.getIsim());
        itemMarket.setText(item.getMarket());

        return convertView;
    }
}
