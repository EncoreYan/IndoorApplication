package com.example.indoorapplication.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.net.wifi.ScanResult;

import com.example.indoorapplication.display.MapPoint;

public class Point {
	private static int nextId = 0;
	
	private int id;
	private MapPoint mapPoint;
	private Map<String, Integer> accessPoints;
	
	public Point(List<ScanResult> scanResults) {
		this(scanResults, null);
	}
	
	public Point(List<ScanResult> scanResults, MapPoint mapPoint) {
		id = nextId++;
		
		accessPoints = new HashMap<String, Integer>();
		
		for (ScanResult scanResult : scanResults) {
			accessPoints.put(scanResult.BSSID, scanResult.level);
		}
		
		this.mapPoint = mapPoint;
	}
	
	public double getDistance(List<ScanResult> scanResults) {
		double distance = 0;
		int count = 0;
		
		for (ScanResult scanResult : scanResults) {
			Integer level = accessPoints.get(scanResult.BSSID);
			
			if (level != null) {
				distance += Math.pow(level - scanResult.level, 2);
				
				count++;
			}
		}
		
		if (count == 0) {
			distance = Double.MAX_VALUE;
		} else {
			distance /= count;
		}
		
		return distance;
	}
	
	public void setActive(boolean active) {
		mapPoint.setActive(active);
	}
	
	public int getId() {
		return id;
	}
	
	public String toString() {
		return "Point " + id;
	}
	
	public MapPoint getMapPoint() {
		return mapPoint;
	}
}
