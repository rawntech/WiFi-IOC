package com.android.m2m.app.base;

import android.app.Service;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.IBinder;

public class WiFiChatService extends Service{

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		startWifiOnBackGround();
		return super.onStartCommand(intent, flags, startId);
	}

	private void startWifiOnBackGround() {
		WifiManager wifiManager = (WifiManager) this.getSystemService(WIFI_SERVICE);
		if(!wifiManager.isWifiEnabled()){
			wifiManager.setWifiEnabled(true);
		}
	}

}
