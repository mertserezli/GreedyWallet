package com.halfbyte.greedywallet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.halfbyte.greedywallet.models.Item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ShowPromotions extends AppCompatActivity {

    Context ctx;
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.showpromotions);

        ctx=getApplicationContext();

        ListView promotionList=(ListView) findViewById(R.id.promotionList);


        List<Item> arrayOfItems=DatabaseManager.getInstance().items;

        ArrayList<Item> result=new ArrayList<Item>();
        ArrayList<String> discount=new ArrayList<>();
        for (int i = 0; i <result.size() ; i++) {
            if(!discount.contains(arrayOfItems.get(i).getDiscount()))
                discount.add(arrayOfItems.get(i).getDiscount());
            result.add(arrayOfItems.get(i));
        }
        Log.d("Array boyutu:",result.size()+"");
        for (int i = 0; i <discount.size() ; i++) {
            Log.d("eleman",discount.get(i));
        }
        //Collections.sort(result);
        ItemAdapter adapter=new ItemAdapter(this,result);
        promotionList.setAdapter(adapter);

        Button getPromotionButton = (Button)findViewById(R.id.findPromotionsButton);

        getPromotionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText nameInput = (EditText)findViewById(R.id.inputItemName);
                EditText minDiscountInput =(EditText) findViewById(R.id.minDiscountInput);
                EditText maxDiscountInput = (EditText)findViewById(R.id.maxDiscountInput);

                String text= nameInput.getText().toString().toLowerCase();
                int min=0,max=100;
                if(minDiscountInput.getText().length()>0)
                    min= Integer.parseInt(minDiscountInput.getText().toString());
                if(maxDiscountInput.getText().length()>0)
                    max= Integer.parseInt(maxDiscountInput.getText().toString());

                ArrayList<Item> searchResult=getPromotion(text,min,max);
                ItemAdapter searchAdapter=new ItemAdapter(ctx,searchResult);
                promotionList.setAdapter(searchAdapter);
            }
        });
        //https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
    }


    public ArrayList<Item> getPromotion(String text,int min,int max){

        List<Item> arrayOfItems=DatabaseManager.getInstance().items;

        ArrayList<Item> result=new ArrayList<Item>();
        for (int i = 0; i <arrayOfItems.size() ; i++) {
            Item item = arrayOfItems.get(i);
            int discount= Integer.parseInt(item.getDiscount().replaceAll("%","").replaceAll(" ","").replaceAll("\n",""));
            if(!text.equals("")){
                if(!item.getIsim().toLowerCase().contains(text))
                    continue;
            }
            if(!(discount>=min && discount<=max))
                continue;
            result.add(item);
        }
        Collections.sort(result);
        return result;
    }
}
