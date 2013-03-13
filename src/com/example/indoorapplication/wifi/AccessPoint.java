package com.example.indoorapplication.wifi;

import android.net.wifi.ScanResult;

/**
 * Represents single access point with signal etc.
 *
 */
public class AccessPoint {
	private static final int MIN_RSSI = Integer.MIN_VALUE;
	
	String SSID;
	public String BSSID;
	public int level;
	ScanResult lastScanResult;
	
	public void updateScanResult(ScanResult scanResult) {
		SSID = scanResult.SSID;
		BSSID = scanResult.BSSID;
		level = scanResult.level;
		lastScanResult = scanResult;
	}
	
	public boolean isInRange() {
		return level != MIN_RSSI;
	}
	
	public void setInactive() {
		level = MIN_RSSI;
	}
	
}
