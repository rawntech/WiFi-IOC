package com.android.m2m.app.activities;

import java.util.ArrayList;
import java.util.List;

import com.android.m2m.app.R;
import com.android.m2m.app.adapter.DeviceListAdapter;
import com.android.m2m.app.base.WiFiChatBase;
import com.android.m2m.app.base.WiFiChatService;
import com.android.m2m.app.base.WiFiIntentHandleListenner;
import com.android.m2m.app.fragment.HomeFragment;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.WifiP2pManager.ChannelListener;
import android.net.wifi.p2p.WifiP2pManager.PeerListListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class HomeActivity extends WiFiChatBase implements OnClickListener,PeerListListener, OnItemClickListener{
	Fragment fragment;
	FragmentManager fragmentManager;
	public WifiP2pManager mManager;
	public Channel mChannel;
	BroadcastReceiver mReceiver;
	private IntentFilter intentFilter;
	Button bScanWifi;
	private WiFiIntentHandleListenner broadcastReceiver=null;
	ListView list;
	@Override
	protected void onCreate(Bundle saveInstance) {
		super.onCreate(saveInstance);
		setContentView(R.layout.fragment_home);
		initalization();
		startService(new Intent(this, WiFiChatService.class));
		
		/*fragment = new HomeFragment(this, mChannel, mManager);
		fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.flMainLayout, fragment).commit();*/
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		//do registration hare (register bradcastlistner)
		broadcastReceiver=new WiFiIntentHandleListenner(this, mManager, mChannel);
		registerReceiver(broadcastReceiver, intentFilter);
		mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {

			@Override
			public void onSuccess() {
				Toast.makeText(HomeActivity.this, "Node Discovery Initiated",
						Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onFailure(int reasonCode) {
				Toast.makeText(HomeActivity.this,
						"Node Discovery Failed : " + reasonCode,
						Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		//unregister listner
		//unregisterReceiver(broadcastReceiver);
	}
	
	private void initalization(){
		mManager = (WifiP2pManager) getSystemService(WIFI_P2P_SERVICE);
		mChannel = mManager.initialize(this, getMainLooper(), null);
		list = (ListView) findViewById(R.id.list);
		list.setOnItemClickListener(this);
		intentFilter = new IntentFilter();
		intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
		intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
		intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
		intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
		//bScanWifi=(Button) findViewById(R.id.bScanWifi);
		//bScanWifi.setOnClickListener(this);
		
		
	}

	@Override
	public void onClick(View arg0) {
		/*if(arg0.getId()==R.id.bScanWifi){
			Toast.makeText(this, "Scan button Clicked", Toast.LENGTH_SHORT).show();
		}*/
		
	}

	@Override
	public void onPeersAvailable(WifiP2pDeviceList peers) {
		try{
			ArrayList<WifiP2pDevice> d = new ArrayList<WifiP2pDevice>();
			d.addAll(peers.getDeviceList());
			DeviceListAdapter adapter = new DeviceListAdapter(this, R.layout.adapter_devicelist, d);
			list.setAdapter(adapter);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		try{
			WifiP2pConfig config = new WifiP2pConfig();
			WifiP2pDevice device = (WifiP2pDevice) list.getItemAtPosition(position);
			config.deviceAddress = device.deviceAddress;
            config.wps.setup = WpsInfo.PBC;
            config.groupOwnerIntent = 0;
            connect(config);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void connect(WifiP2pConfig config) {
    	// perform p2p connect upon users click the connect button. after connection, manager request connection info.
        mManager.connect(mChannel, config, new ActionListener() {
            @Override
            public void onSuccess() {
                // WiFiDirectBroadcastReceiver will notify us. Ignore for now.
            	Toast.makeText(HomeActivity.this, "Connect success..", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int reason) {
                Toast.makeText(HomeActivity.this, "Connect failed. Retry.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
