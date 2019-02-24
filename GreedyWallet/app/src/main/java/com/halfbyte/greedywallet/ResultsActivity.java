package com.halfbyte.greedywallet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.halfbyte.greedywallet.models.Item;

import java.util.ArrayList;
import java.util.List;

public class ResultsActivity extends AppCompatActivity {

    ArrayAdapter<String> adapter;
    List<String> optimizedResultsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results);

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                optimizedResultsList);
        ListView results = (ListView)findViewById(R.id.OptimizedResultView);
        results.setAdapter(adapter);
        for(Item i: AddManually.optimizedList)
            adapter.add(i.getIsim() + " " + i.getPrice());
    }
}
