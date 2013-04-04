package com.example.indoorapplication.display;

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
	private int[] imagePos;
	
	public Mapzor(ImageView image, Room room) {
		this.image = image;
		this.points = room.getPoints();
		
		imagePos = new int[2];
	    image.getLocationOnScreen(imagePos);
	}
	
	public MapPoint createMapPoint(MotionEvent event) {
		float x = event.getRawX() - imagePos[0]; 
	    float y = event.getRawY() - imagePos[1];
	    
	    if (x < 0 || x > image.getWidth() || y < 0 || y > image.getHeight()) {
	    	return null;
	    }
	    
		MapPoint point = new MapPoint(x, y, Color.BLUE);
		
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
		
		image.setImageBitmap(bitmap);
	}
}
