package com.android.m2m.app.fragment;
import java.util.ArrayList;
import java.util.List;

import com.android.m2m.app.R;
import com.android.m2m.app.adapters.WiFiPeerListAdapter;
import com.android.m2m.app.asynctask.DownloadAsyncTask;
import com.android.m2m.app.interfaces.IAsyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;
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
	WiFiPeerListAdapter adapter=null;
	public HomeFragment(Context _context,Channel _wifiChannel,WifiP2pManager _wifiManager) {
		context=_context;
		wifiChannel=_wifiChannel;
		wifiManager=_wifiManager;
	}
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
		LoadPeearsList();
	}

	private void LoadPeearsList() {
		if(asyncTask != null)
			asyncTask.cancel(true);
		asyncTask = new DownloadAsyncTask(this);
		asyncTask.execute();
	}

	@Override
	public void showProgressBar() {
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
		}
		progressDialog = ProgressDialog.show(getActivity(),
				"Press back to cancel", "finding nodes", true, true,
				new DialogInterface.OnCancelListener() {

					@Override
					public void onCancel(DialogInterface dialog) {

					}
				});
	}

	@Override
	public void hideProgressBar() {
		progressDialog.cancel();
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
		
	}
	
	public void setDeviceToAdapter(ArrayList<WifiP2pDevice> devices){
		
	}
	
	public void updateUI(WifiP2pDeviceList peerList){
		peers.clear();
		peers.addAll(peerList.getDeviceList());
		adapter=new WiFiPeerListAdapter(getActivity(), R.layout.device_list_item, peers);
		
	}
	
	@Override
	public void onPeersAvailable(WifiP2pDeviceList peerList) {
		peers.clear();
		peers.addAll(peerList.getDeviceList());
		adapter=new WiFiPeerListAdapter(getActivity(), R.layout.device_list_item, peers);
		
	}
}
