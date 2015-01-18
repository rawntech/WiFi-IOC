package com.android.m2m.app.utils;

import android.net.wifi.p2p.WifiP2pDevice;
import android.util.Log;


public class CommonTasks {
	
	public static String getDeviceStatus(int deviceStatus) {
		Log.d("m2m", "Peer status :" + deviceStatus);
		switch (deviceStatus) {
		case WifiP2pDevice.AVAILABLE:
			return "Available";
		case WifiP2pDevice.INVITED:
			return "Invited";
		case WifiP2pDevice.CONNECTED:
			return "Connected";
		case WifiP2pDevice.FAILED:
			return "Failed";
		case WifiP2pDevice.UNAVAILABLE:
			return "Unavailable";
		default:
			return "Unknown";

		}
	}

}
