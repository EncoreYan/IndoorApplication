package com.example.indoorapplication;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.support.v4.app.NavUtils;

public class DisplayMap extends Activity {

    Canvas c;
    Paint p;
    ImageView v;
    Bitmap bm;
	ArrayList<Point> points = new ArrayList<Point>();
	
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(new Map(this));
        
        
        setContentView(R.layout.activity_display_map);
        //getActionBar().setDisplayHomeAsUpEnabled(true);

    }
    

    public boolean onTouchEvent(MotionEvent event){

        v = (ImageView)findViewById(R.id.mapView);
	    bm = Bitmap.createBitmap(v.getWidth(),v.getHeight(),Config.ARGB_8888);
	    c = new Canvas(bm);
	    p = new Paint();        
	    p.setColor(Color.BLUE);
	    
	    int[] coords = new int[2];
	    v.getLocationOnScreen(coords);
	    
	    float x = event.getRawX()-coords[0]; 
	    float y = event.getRawY()-coords[1];
	    Point currentPoint = new Point(x, y, "BLUE");
	    points.add(currentPoint);
	    
	    for(int i = 0; i < points.size(); i++){
	    	if(points.get(i).getColor().equals("BLUE")){
	    		p.setColor(Color.BLUE);
	    	} else
	    		p.setColor(Color.RED);
	    	
	    	c.drawCircle(points.get(i).getX(), points.get(i).getY(), 10, p);  
	    }
	       
	
	    v.setImageBitmap(bm);
	
	    return true;
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
