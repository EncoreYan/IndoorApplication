package com.example.indoorapplication.wifi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.net.wifi.ScanResult;

public class AccessPoints {
	private Map<String, AccessPoint> accessPoints;
	private int updateCount; 
	
	public AccessPoints() {
		accessPoints = new HashMap<String, AccessPoint>();
		updateCount = 0;
	}
	
	public void update(List<ScanResult> scanResults) {
		// clear APs
		for (AccessPoint accessPoint: accessPoints.values()) {
		    accessPoint.setInactive();
		}
		
		for (ScanResult scanResult : scanResults) {
			AccessPoint accessPoint = accessPoints.get(scanResult.BSSID);
			
			if (accessPoint == null) {
				accessPoint = new AccessPoint();
				accessPoints.put(scanResult.BSSID, accessPoint);
			}
			
			accessPoint.updateScanResult(scanResult);
		}
	}
	
	public List<AccessPoint> getAccessPointList() {
		return new ArrayList<AccessPoint>(accessPoints.values());
	}
	
	public AccessPoint getAccessPoint(String BSSID) {
		return accessPoints.get(BSSID);
	}
	
	public String toString() {
		String result = "Update " + updateCount + ":\n";
		List<AccessPoint> accessPoints = getAccessPointList();
		
		Collections.sort(accessPoints, new AccessPointComparator());
		
		for (AccessPoint accessPoint : accessPoints) {
			result += accessPoint.SSID + " (" + accessPoint.BSSID + ") RSSI: ";
			
			if (accessPoint.isInRange()) {
				result += accessPoint.level;
			} else {
				result += "Out of range";
			}
			result += "\n";
		}
		
		updateCount++;
		
		return result;
	}
}

