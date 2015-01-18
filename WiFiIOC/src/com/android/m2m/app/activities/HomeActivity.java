package com.android.m2m.app.activities;

import java.util.ArrayList;
import java.util.List;

import com.android.m2m.app.R;
import com.android.m2m.app.base.WiFiChatBase;
import com.android.m2m.app.base.WiFiChatService;
import com.android.m2m.app.base.WiFiIntentHandleListenner;
import com.android.m2m.app.fragment.HomeFragment;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.WifiP2pManager.ChannelListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class HomeActivity extends WiFiChatBase implements OnClickListener{
	Fragment fragment;
	FragmentManager fragmentManager;
	WifiP2pManager mManager;
	Channel mChannel;
	BroadcastReceiver mReceiver;
	private IntentFilter intentFilter;
	Button bScanWifi;
	private WiFiIntentHandleListenner broadcastReceiver=null;
	@Override
	protected void onCreate(Bundle saveInstance) {
		super.onCreate(saveInstance);
		setContentView(R.layout.activity_home);
		initalization();
		startService(new Intent(this, WiFiChatService.class));
		
		fragment = new HomeFragment(this,mChannel,mManager);
		fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.flMainLayout, fragment).commit();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		//do registration hare (register bradcastlistner)
		broadcastReceiver=new WiFiIntentHandleListenner(this, mManager, mChannel);
		registerReceiver(broadcastReceiver, intentFilter);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		//unregister listner
		unregisterReceiver(broadcastReceiver);
	}
	
	private void initalization(){
		mManager = (WifiP2pManager) getSystemService(WIFI_P2P_SERVICE);
		mChannel = mManager.initialize(this, getMainLooper(), null);
		intentFilter = new IntentFilter();
		intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
		intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
		intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
		intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
		bScanWifi=(Button) findViewById(R.id.bScanWifi);
		bScanWifi.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		if(arg0.getId()==R.id.bScanWifi){
			Toast.makeText(this, "Scan button Clicked", Toast.LENGTH_SHORT).show();
		}
		
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		List<WifiP2pDevice> peers = new ArrayList<WifiP2pDevice>();
		Bundle bundle = intent.getExtras();
		super.onNewIntent(intent);
		
		
	}
}
