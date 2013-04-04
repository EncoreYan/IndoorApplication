package com.example.indoorapplication.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.net.wifi.ScanResult;

public class Point {
	private static int nextId = 0;
	
	private int id;
	private Map<String, Integer> accessPoints;
	
	public Point(List<ScanResult> scanResults) {
		id = nextId++;
		
		accessPoints = new HashMap<String, Integer>();
		
		for (ScanResult scanResult : scanResults) {
			accessPoints.put(scanResult.BSSID, scanResult.level);
		}
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
	
	public int getId() {
		return id;
	}
	
	public String toString() {
		return "Point " + id;
	}
}
