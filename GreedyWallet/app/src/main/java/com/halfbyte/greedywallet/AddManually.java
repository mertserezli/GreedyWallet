package com.halfbyte.greedywallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.halfbyte.greedywallet.models.Item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class AddManually extends AppCompatActivity {

    ArrayList<String> categoriesInList = new ArrayList<>();
    ArrayAdapter<String> adapter;
    public static List<Item> optimizedList = new ArrayList<>();

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.addmanually);

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                categoriesInList);
        ListView list = (ListView)findViewById(R.id.categoryList);
        list.setAdapter(adapter);

        final Button addToListButton = (Button) findViewById(R.id.addToList);
        addToListButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText input = (EditText)findViewById(R.id.itemNameInput);
                final String itemName = input.getText().toString();
                if (DatabaseManager.getInstance().containsItem(itemName)){
                    adapter.add(itemName);
                }
            }
        });

        final Intent resultIntent = new Intent(this, ResultsActivity.class);
        final Button getOptimizedListButton = (Button) findViewById(R.id.getOptimizedItemList);
        getOptimizedListButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ArrayList<String> groupList = new ArrayList<String>();
                for (int i = 0; i < adapter.getCount(); i++)
                    groupList.add( adapter.getItem(i) );
                EditText budgetInput = (EditText)findViewById(R.id.budgetInput);
                String budgetString = budgetInput.getText().toString();
                double budget = parseDouble(budgetString) == 0 ? Integer.MAX_VALUE: parseDouble(budgetString);
                try {
                    optimizedList = Optimizer.optimizer( groupList,budget );
                }catch (Exception e){
                    optimizedList = Collections.EMPTY_LIST;
                }
                startActivity( resultIntent );
            }
        });
    }

    double parseDouble(String strNumber) {
        if (strNumber != null && strNumber.length() > 0) {
            try {
                return Double.parseDouble(strNumber);
            } catch(Exception e) {
                return -1;   // or some value to mark this field is wrong. or make a function validates field first ...
            }
        }
        else return 0;
    }
}
