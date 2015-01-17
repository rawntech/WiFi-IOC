package com.android.m2m.app.fragment;
import java.util.ArrayList;
import java.util.List;

import com.android.m2m.app.R;
import com.android.m2m.app.activities.HomeActivity;
import com.android.m2m.app.adapter.DeviceListAdapter;
import com.android.m2m.app.asynctask.DownloadAsyncTask;
import com.android.m2m.app.interfaces.IAsyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;
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
import android.widget.Toast;

public class HomeFragment extends ListFragment implements IAsyncTask,PeerListListener{
	private WifiP2pManager wifiManager;
	private Channel wifiChannel;
	ProgressBar scanWiFiProgressBar;
	ProgressDialog progressDialog = null;
	DownloadAsyncTask asyncTask;
	private List<WifiP2pDevice> peers = new ArrayList<WifiP2pDevice>();
	Context context;
	/*public HomeFragment(Context _context,Channel _wifiChannel,WifiP2pManager _wifiManager) {
		context=_context;
		wifiChannel=_wifiChannel;
		wifiManager=_wifiManager;
	}*/
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
		
		wifiManager.discoverPeers(wifiChannel, new WifiP2pManager.ActionListener() {

			@Override
			public void onSuccess() {
				Toast.makeText(getActivity(), "Node Discovery Initiated",
						Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onFailure(int reasonCode) {
				Toast.makeText(getActivity(),
						"Node Discovery Failed : " + reasonCode,
						Toast.LENGTH_SHORT).show();
			}
		});
		
		return null;
	}

	@Override
	public void processDataAferDownload(Object object) {
		adapter = new DeviceListAdapter(getActivity(), R.layout.adapter_devicelist, deviceList);
		setListAdapter(adapter);
	}
	
	public void setDeviceToAdapter(ArrayList<WifiP2pDevice> devices){
		
	}

	@Override
	public void onPeersAvailable(WifiP2pDeviceList peers) {
		// TODO Auto-generated method stub
		
	}
	
	
}

