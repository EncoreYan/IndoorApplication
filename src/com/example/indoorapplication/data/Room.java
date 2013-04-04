package com.example.indoorapplication.data;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import android.net.wifi.ScanResult;

public class Room {
	private List<Point> points;
	
	public Room() {
		points = new ArrayList<Point>();
	}
	
	public Point createPoint(List<ScanResult> scanResults) {
		Point point = new Point(scanResults);
		
		points.add(point);
		
		return point;
	}
	
	public String findNearest(List<ScanResult> scanResults) {
		String data = "";
		
		Point bestPoint = null;
		double bestDistance = Double.MAX_VALUE;
		
		for (Point point : points) {
			double distance = point.getDistance(scanResults);
			data += point + ": " + distance + ";";
			
			if (distance < bestDistance) {
				bestPoint = point;
				bestDistance = distance;
			}
		}
		
		data += "\n" + bestPoint;
		
		return data;
	}
	
	public SortedMap<Double, Point> orderByResults(List<ScanResult> scanResults) {
		SortedMap<Double, Point> sortedPoints = new TreeMap<Double, Point>();
		
		for (Point point : points) {
			double distance = point.getDistance(scanResults);
			sortedPoints.put(distance, point);
		}
		
		return sortedPoints;
	}
}
