package com.android.m2m.app.base;


import com.android.m2m.app.activities.HomeActivity;
import com.android.m2m.app.fragment.DeviceDetailsFragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;

public class WiFiIntentHandleListenner extends BroadcastReceiver{
	private WifiP2pManager manager;
	private HomeActivity activity;
	private Channel channel;
	DeviceDetailsFragment deviceDetailsFragment;
	
	public WiFiIntentHandleListenner(HomeActivity _activity,WifiP2pManager _manager,Channel _channel) {
		activity=_activity;
		channel=_channel;
		manager=_manager;
	}

	@Override
	public void onReceive(Context context, Intent intent) {

		String action=intent.getAction();
		if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {

            // UI update to indicate wifi p2p status.
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                // Wifi Direct mode is enabled
                //activity.setIsWifiP2pEnabled(true);
            } else {
                /*activity.setIsWifiP2pEnabled(false);
                activity.resetData();*/

            }
        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
        	
             /*request available peers from the wifi p2p manager. This is an
             asynchronous call and the calling activity is notified with a
             callback on PeerListListener.onPeersAvailable()*/
           /* if (manager != null) {
                manager.requestPeers(channel, (PeerListListener) activity.getFragmentManager()
                        .findFragmentById(R.id.frag_list));*/
            }
        else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {

            if (manager == null) {
                return;
            }

            NetworkInfo networkInfo = (NetworkInfo) intent
                    .getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);

            if (networkInfo.isConnected()) {

                // we are connected with the other device, request connection
                // info to find group owner IP

               /* deviceDetailsFragment = (DeviceDetailsFragment) activity
                        .getFragmentManager().findFragmentById(R.id.fragment_device_details);*/
                
                
                manager.requestConnectionInfo(channel, deviceDetailsFragment);
            } else {
                // It's a disconnect.Call reset the list
                
            }
        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
        	/*when two device attached WIFI_P2P_NETWORK_CHANGED is broadcasted
        	Update this device using 
        	intent.getParcelableExtra(
                    WifiP2pManager.EXTRA_WIFI_P2P_DEVICE)*/            

        }
        
		
	}

}
