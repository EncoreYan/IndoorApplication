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
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.example.indoorapplication.data.Point;
import com.example.indoorapplication.data.Room;
import com.example.indoorapplication.display.MapPoint;
import com.example.indoorapplication.display.Mapzor;
import com.example.indoorapplication.wifi.WifiScan;
import com.example.indoorapplication.wifi.WifiScanListener;

public class DisplayMap extends Activity {
	SeekBar seekbar;
    ImageView v;
    Mapzor map;
    TextView sigmaText;
    TextView log;
    double sigma = 6;
    double minSigma = 0.1;
    double maxSigma = 10;
    List<ScanResult> lastScanResults = null;
    int mode = 0;
    
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
        
        log = (TextView)findViewById(R.id.textView3);
        
        wifi = new WifiScan(this);
        room = IndoorPositioning.room;
        
        v = (ImageView)findViewById(R.id.mapView);
        map = new Mapzor(v, room);
        
        sigmaText = (TextView)findViewById(R.id.sigma);
        sigmaText.setText("sigma=6");
        seekbar = (SeekBar)findViewById(R.id.seekBar1);
        
        int seekbarWidth = (int)(maxSigma / minSigma);
        
        seekbar.setMax(seekbarWidth);
        seekbar.setProgress((int)((sigma - minSigma) / (maxSigma - minSigma) * seekbarWidth));
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

			public void onProgressChanged(SeekBar bar, int progress, boolean arg2) {
				sigma = (double)progress / bar.getMax() * (maxSigma - minSigma) + minSigma;
				// TODO Auto-generated method stub
				sigmaText.setText("sigma=" + (double)Math.round(sigma*10)/10);
				
				calculatePosition();
				
			}

			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}

			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}
        	
        });


    }
    

    public boolean onTouchEvent(MotionEvent event){
    	MapPoint point = map.createMapPoint(event);
    	
    	
    	
    	if (point != null) {
    		System.out.println("Found map point" + point.getX() + "; " + point.getY());
    		
    		wifi.getWifiData(new TrainListener(point));
    	}
    	
	    return true;
    }
    
    public void onToggleClicked(View view) {
        boolean on = ((Switch) view).isChecked();
        
        if (on) {
        	mode = 0;
        } else {
        	mode = 1;
        }
        
        calculatePosition();
    }
    
    public void locate(View view) {
    	wifi.getWifiData(new LocateListener());
    	
    	locateButton.setText(R.string.loading);
    }
    
    public void reset(View view) {
    	room.reset();
    	
    	map.reDraw();
    }
    
    private void calculatePosition() {
    	SortedMap<Double, Point> points;
    	
    	if (mode == 0) {
    		points = room.orderByResults(lastScanResults);
    	} else {
    		points = room.orderByProbability(lastScanResults, sigma);
    	}
    	
    	log.setText(points.toString().replace(',', '\n'));
    	
    	if (activePoint != null) {
			activePoint.setActive(false);
		}
		
		room.clearActivePoints();
		
		if (points.size() > 0) {
			activePoint = points.get(points.firstKey());
			activePoint.setActive(true);
		}
		
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
			lastScanResults = scanResults;
			
			locateButton.setText(R.string.button_locate);
			
			calculatePosition();
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
