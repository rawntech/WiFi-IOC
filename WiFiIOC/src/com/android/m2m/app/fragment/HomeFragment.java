package com.android.m2m.app.fragment;
import com.android.m2m.app.R;
import com.android.m2m.app.asynctask.DownloadAsyncTask;
import com.android.m2m.app.interfaces.IAsyncTask;

import android.content.Context;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

public class HomeFragment extends ListFragment implements IAsyncTask{
	private WifiP2pManager wifiManager;
	private Channel wifiChannel;
	ProgressBar scanWiFiProgressBar;
	DownloadAsyncTask asyncTask;
	Context context;
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
		
	}

	@Override
	public void hideProgressBar() {
		
	}

	@Override
	public Object doInBackGround() {
		return null;
	}

	@Override
	public void processDataAferDownload(Object object) {
		
	}
}
