package com.greedywallet.hp.predictor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.time.ZonedDateTime;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void predict(View view) {
        Button button = (Button) view;
        ViewGroup screen = (ViewGroup) button.getParent();
        screen.removeViews(0,screen.getChildCount()-1);

        ZonedDateTime[] past = new ZonedDateTime[5];
        past[0] = ZonedDateTime.now().minusDays(45);
        past[1] = ZonedDateTime.now().minusDays(40);
        past[2] = ZonedDateTime.now().minusDays(40);
        past[3] = ZonedDateTime.now().minusDays(20);
        past[4] = ZonedDateTime.now().minusDays(5);
        ZonedDateTime prediction = Predictor.predict(past);
        ZonedDateTime now = ZonedDateTime.now();

        //ImageView image = new ImageView(this);
        //image.setImageDrawable(getDrawable(R.mipmap.ic_launcher));

        TextView itemName = new TextView(this);
        itemName.setText("Predicted Item");

        TextView predictedDate = new TextView(this);
        predictedDate.setText(prediction.getDayOfMonth() + "/" + prediction.getMonthValue() + "/"  + prediction.getYear());

        if (prediction.getDayOfMonth() == now.getDayOfMonth()
                && prediction.getMonthValue() == now.getMonthValue()
                && prediction.getYear() == now.getYear())
        {
            predictedDate.setText("TODAY");
        }

        screen.addView(predictedDate,0);
        screen.addView(itemName,0);
        //screen.addView(image,0);
    }
}
