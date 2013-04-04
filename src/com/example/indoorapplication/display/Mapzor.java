package com.example.indoorapplication.display;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.example.indoorapplication.data.Point;
import com.example.indoorapplication.data.Room;


public class Mapzor {
	private ImageView image;
	private List<Point> points;
	private List<MapPoint> tempPoints;
	private int[] imagePos;
	
	public Mapzor(ImageView image, Room room) {
		this.image = image;
		this.points = room.getPoints();
		this.tempPoints = new ArrayList<MapPoint>();
		
		imagePos = new int[2];
	}
	
	public MapPoint createMapPoint(MotionEvent event) {
		image.getLocationOnScreen(imagePos);
		
		float x = event.getX() - imagePos[0]; 
	    float y = event.getY() - imagePos[1];
	    
	    if (x < 0 || x > image.getWidth() || y < 0 || y > image.getHeight()) {
	    	return null;
	    }
	    
		MapPoint point = new MapPoint(x, y);
		
		tempPoints.add(point);
		
		reDraw();
		
		return point;
	}
	
	public void reDraw() {
		Bitmap bitmap = Bitmap.createBitmap(image.getWidth(), image.getHeight(), Config.ARGB_8888);
	    Canvas canvas = new Canvas(bitmap);
		Paint paint = new Paint();
		
		for (Point point : points) {
			MapPoint mapPoint = point.getMapPoint();
			paint.setColor(mapPoint.getColor());
			
			canvas.drawCircle(mapPoint.getX(), mapPoint.getY(), 10, paint);
		}
		
		paint.setColor(Color.GRAY);
		for (MapPoint mapPoint : tempPoints) {
			canvas.drawCircle(mapPoint.getX(), mapPoint.getY(), 10, paint);
		}
		
		image.setImageBitmap(bitmap);
	}
	
	public void clearTempPoints() {
		tempPoints.clear();
	}
}
