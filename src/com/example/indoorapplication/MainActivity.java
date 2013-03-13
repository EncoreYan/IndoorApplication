package com.example.indoorapplication;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void openMapTab(View view) {
    	Intent intent = new Intent(this, DisplayMap.class);
    	startActivity(intent);
    	
    }
    
    public void openWifiTab(View view) {
    	Intent intent = new Intent(this, DisplayWifi.class);
    	startActivity(intent);
    }

}
