package com.example.indoorapplication;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.indoorapplication.wifi.AccessPoints;

public class DisplayWifi extends Activity {
	private TextView wifiInfo;
	WifiManager wifi;
	AccessPoints accessPoints;
	WifiScanResultReceiver receiver;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_wifi);
        
        
        wifiInfo = (TextView) findViewById(R.id.wifi_info);
        
		accessPoints = new AccessPoints();
		
		initWifiInfo();
    }
    
    public void refreshAccessPoints(View view) {
		wifi.startScan();
	}

    private void initWifiInfo() {
    	wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		
		receiver = new WifiScanResultReceiver();
    	
		registerReceiver(receiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
		wifi.startScan();
	}
    
    public void updateWifiData() {
    	accessPoints.update(wifi.getScanResults());
    	
    	wifiInfo.setText(accessPoints.toString());
    }

    class WifiScanResultReceiver extends BroadcastReceiver {
    	@Override
    	public void onReceive(Context context, Intent intent) {
    		updateWifiData();
    	}
    }
    
    @Override
	protected void onPause() {
        unregisterReceiver(receiver);
        super.onPause();
    }

	@Override
    protected void onResume() {
        registerReceiver(receiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        super.onResume();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_display_map, menu);
        return true;
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
