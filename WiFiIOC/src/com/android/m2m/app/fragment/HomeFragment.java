package com.android.m2m.app.fragment;
import com.android.m2m.app.R;

import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HomeFragment extends ListFragment{
	private WifiP2pManager wifiManager;
	private Channel wifiChannel;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);
		setWifiList();
		return root;		
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}

	private void setWifiList() {
		
	}
}
