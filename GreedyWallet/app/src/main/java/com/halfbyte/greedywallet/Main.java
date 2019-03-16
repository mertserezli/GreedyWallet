package com.halfbyte.greedywallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Main extends AppCompatActivity {
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.main);
        DatabaseManager.getInstance();
        HistoryManager.getInstance();
    }
    public void onButtonClickScanner(View view) {
        Intent intent=new Intent(this,OcrCaptureActivity.class);
        startActivity(intent);
    }
    public void onButtonClickManual(View view) {
        Intent intent=new Intent(this,AddManually.class);
        startActivity(intent);
    }
    public void onButtonClickMaps(View view){
        Intent intent=new Intent(this,CheckInActivity.class);
        startActivity(intent);
    }
    public void onButtonClickHistory(View view) {
        Intent intent=new Intent(this,ShowHistory.class);
        startActivity(intent);
    }
    public void onButtonClickPredictions(View view) {
        Intent intent=new Intent(this,ShowPredictions.class);
        startActivity(intent);
    }
}
