package com.android.m2m.app.base;


import com.android.m2m.app.R;
import com.android.m2m.app.activities.HomeActivity;
import com.android.m2m.app.fragment.DeviceDetailsFragment;
import com.android.m2m.app.fragment.HomeFragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.Toast;
import android.net.wifi.p2p.WifiP2pManager.PeerListListener;

public class WiFiIntentHandleListenner extends BroadcastReceiver{
	private WifiP2pManager manager;
	private HomeActivity activity;
	private Channel channel;
	DeviceDetailsFragment deviceDetailsFragment;
	Fragment fragment;
	FragmentManager fragmentManager;
	
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
        	//manager.requestPeers(channel, (PeerListListener)activity.getFragmentManager().)
        	
        	 /*manager.requestPeers(channel, new WifiP2pManager.PeerListListener() {
                 @Override
                 public void onPeersAvailable(WifiP2pDeviceList peers) {
                     Log.d(TAG,String.format("PeerListListener: %d peers available, updating device list", peers.getDeviceList().size()));

                     // DO WHATEVER YOU WANT HERE
                     // YOU CAN GET ACCESS TO ALL THE DEVICES YOU FOUND FROM peers OBJECT

                 }
             });*/
        	
        	if(manager!=null){
        		manager.requestPeers(channel, new WifiP2pManager.PeerListListener() {
					
					@Override
					public void onPeersAvailable(WifiP2pDeviceList peers) {
						
						Intent intent2open = new Intent(activity, HomeActivity.class);
			        	intent2open.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			        	intent2open.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			        	String name = "KEY";
			        	intent2open.putExtra(name, peers);
			        	activity.startActivity(intent2open);
						
						Log.d("m2m", peers.toString());
						Toast.makeText(activity, "Device Found :"+peers.getDeviceList().toString(), Toast.LENGTH_SHORT).show();
					}
				});
        	}
        	
        
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
