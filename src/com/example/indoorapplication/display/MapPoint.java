package com.example.indoorapplication.display;

import android.graphics.Color;

public class MapPoint {
	public static final int COLOR_DEFAULT = Color.BLUE;
	public static final int COLOR_ACTIVE = Color.RED;
	
	private boolean active;
	private float x,y;
	private int color;
	
	public MapPoint(float x, float y) {
		this(x, y, COLOR_DEFAULT);
	}

	public MapPoint(float x, float y, int color) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.active = false;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getColor() {
		return active ? COLOR_ACTIVE : color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	

}
