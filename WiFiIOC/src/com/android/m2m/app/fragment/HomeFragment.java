package com.android.m2m.app.fragment;
import java.util.ArrayList;

import com.android.m2m.app.R;
import com.android.m2m.app.activities.HomeActivity;
import com.android.m2m.app.adapter.DeviceListAdapter;
import com.android.m2m.app.asynctask.DownloadAsyncTask;
import com.android.m2m.app.interfaces.IAsyncTask;

import android.content.Context;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager.PeerListListener;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

public class HomeFragment extends ListFragment implements IAsyncTask, PeerListListener{
	ProgressBar scanWiFiProgressBar;
	DownloadAsyncTask asyncTask;
	Context context;
	ArrayList<WifiP2pDevice> deviceList;
	WifiP2pInfo wifiP2pInfo;
	DeviceListAdapter adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);
		Initialization(root);
		return root;		
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}

	private void Initialization(ViewGroup root) {
		scanWiFiProgressBar = (ProgressBar) root.findViewById(R.id.scanWiFiProgressBar);
		deviceList = new ArrayList<WifiP2pDevice>();
		((HomeActivity)getActivity()).mManager.requestPeers(((HomeActivity)getActivity()).mChannel, (PeerListListener)this);
	}

	private void LoadPeearsList() {
		if(asyncTask != null)
			asyncTask.cancel(true);
		asyncTask = new DownloadAsyncTask(this);
		asyncTask.execute();
	}

	@Override
	public void showProgressBar() {
		scanWiFiProgressBar.setVisibility(View.VISIBLE);
	}

	@Override
	public void hideProgressBar() {
		scanWiFiProgressBar.setVisibility(View.GONE);
	}

	@Override
	public Object doInBackGround() {
		return null;
	}

	@Override
	public void processDataAferDownload(Object object) {
		adapter = new DeviceListAdapter(getActivity(), R.layout.adapter_devicelist, deviceList);
		setListAdapter(adapter);
	}

	@Override
	public void onPeersAvailable(WifiP2pDeviceList peers) {
		deviceList.clear();
		deviceList.addAll(peers.getDeviceList());
		LoadPeearsList();
	}
	
	public WifiP2pDevice getConnectedPeer(){
    	WifiP2pDevice peer = null;
		for(WifiP2pDevice d : deviceList ){
    		if( d.status == WifiP2pDevice.CONNECTED){
    			peer = d;
    		}
    	}
    	return peer;
    }
}
