package com.example.indoorapplication;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.indoorapplication.data.Point;
import com.example.indoorapplication.data.Room;
import com.example.indoorapplication.wifi.WifiScan;
import com.example.indoorapplication.wifi.WifiScanListener;

public class DisplayWifi extends Activity implements WifiScanListener {
	private TextView wifiInfo;
	private TextView status;
	
	private WifiScan wifi;
	private Room room;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_wifi);
        
        wifi = new WifiScan(this);
        wifiInfo = (TextView) findViewById(R.id.wifi_info);
        status = (TextView) findViewById(R.id.wifi_status);
        room = IndoorPositioning.room;
		
		refreshAccessPoints();
    }
    
    public void refreshAccessPoints(View view) {
    	refreshAccessPoints();
    }
    
    public void refreshAccessPoints() {
		wifi.getWifiData(this);
		
		wifiInfo.setText(R.string.loading);
	}
    
    public void setTrainingPoint(View view) {
		wifi.getWifiData(new TrainListener());
    	
    	status.setText(R.string.loading);
    }
    
    public void locate(View view) {
    	wifi.getWifiData(new LocateListener());
    	
    	status.setText(R.string.loading);
    }
    
    public void onWifiScanResult(List<ScanResult> scanResults) {
    	String result = "";
    	
		for (ScanResult scanResult : scanResults) {
			result += scanResult.SSID + " (" + scanResult.BSSID + ") RSSI: " + scanResult.level;
			
			result += "\n";
		}
		
		wifiInfo.setText(result);
	}

    class LocateListener implements WifiScanListener {
		public void onWifiScanResult(List<ScanResult> scanResults) {
			String data = "";
			
			Iterator<Map.Entry<Double, Point>> it = room.orderByResults(scanResults).entrySet().iterator();
			
			while (it.hasNext()) {
				Map.Entry<Double, Point> entry = it.next();
				double level = Math.round(entry.getKey() * 100) / 100.0; 
				data += entry.getValue() + ": " + level + "\n";
			}
			
			status.setText(data);
		}
    }
    
    class TrainListener implements WifiScanListener {
		public void onWifiScanResult(List<ScanResult> scanResults) {
			Point point = room.createPoint(scanResults);
			
			status.setText("Point saved: " + point);
		}
    }
    
    @Override
	protected void onPause() {
    	wifi.unregisterReceiver();
        super.onPause();
    }

	@Override
    protected void onResume() {
		wifi.registerReceiver();
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
