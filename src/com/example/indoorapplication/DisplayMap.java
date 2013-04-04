package com.example.indoorapplication;

import java.util.List;
import java.util.SortedMap;

import android.app.Activity;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.indoorapplication.data.Point;
import com.example.indoorapplication.data.Room;
import com.example.indoorapplication.display.MapPoint;
import com.example.indoorapplication.display.Mapzor;
import com.example.indoorapplication.wifi.WifiScan;
import com.example.indoorapplication.wifi.WifiScanListener;

public class DisplayMap extends Activity {

    ImageView v;
    Mapzor map;
    
    private WifiScan wifi;
	private Room room;
	private Point activePoint = null;
	private Button locateButton;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(new Map(this));
        
        
        setContentView(R.layout.activity_display_map);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        
        locateButton = (Button)findViewById(R.id.locate_button);
        
        wifi = new WifiScan(this);
        room = IndoorPositioning.room;
        
        v = (ImageView)findViewById(R.id.mapView);
        map = new Mapzor(v, room);

    }

    public boolean onTouchEvent(MotionEvent event){
    	MapPoint point = map.createMapPoint(event);
    	
    	
    	
    	if (point != null) {
    		System.out.println("Found map point" + point.getX() + "; " + point.getY());
    		
    		wifi.getWifiData(new TrainListener(point));
    	}
    	
	    return true;
    }
    
    public void locate(View view) {
    	wifi.getWifiData(new LocateListener());
    	
    	locateButton.setText(R.string.loading);
    }
    
    public void reset(View view) {
    	room.reset();
    	
    	map.reDraw();
    }
    
    class TrainListener implements WifiScanListener {
    	private MapPoint point;
    	
    	public TrainListener(MapPoint point) {
    		this.point = point;
    	}
    	
		public void onWifiScanResult(List<ScanResult> scanResults) {
			System.out.println("Scan results");
			room.createPoint(scanResults, this.point);
			
			map.clearTempPoints();
			map.reDraw();
		}
    }
    
    class LocateListener implements WifiScanListener {
		public void onWifiScanResult(List<ScanResult> scanResults) {
			locateButton.setText(R.string.button_locate);
			
			if (activePoint != null) {
				activePoint.setActive(false);
			}
			
			SortedMap<Double, Point> points = room.orderByResults(scanResults);
			
			if (points.size() > 0) {
				activePoint = points.get(points.firstKey());
				activePoint.setActive(true);
			}
			
			map.reDraw();
			
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
