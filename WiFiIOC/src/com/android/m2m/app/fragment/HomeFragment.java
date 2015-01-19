package com.android.m2m.app.fragment;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.m2m.app.R;
import com.android.m2m.app.adapter.DeviceListAdapter;
import com.android.m2m.app.asynctask.DownloadAsyncTask;

@SuppressLint("ValidFragment")
public class HomeFragment extends ListFragment{
	ProgressBar scanWiFiProgressBar;
	WifiP2pManager mManager;
	Channel mChannel;
	ProgressDialog progressDialog = null;
	DownloadAsyncTask asyncTask;
	Context context;
	public ArrayList<WifiP2pDevice> deviceList;
	WifiP2pInfo wifiP2pInfo;
	DeviceListAdapter adapter;
	
	public HomeFragment(Context _context,Channel _wifiChannel,WifiP2pManager _wifiManager) {
		context=_context;
		mChannel=_wifiChannel;
		mManager=_wifiManager;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);
		return root;		
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		deviceList = new ArrayList<WifiP2pDevice>();
		setListAdapter(new WiFiPeerListAdapter(getActivity(), R.layout.adapter_devicelist, deviceList));
		mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {

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
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
	}

	private void Initialization(ViewGroup root) {
		deviceList = new ArrayList<WifiP2pDevice>();
		
		
	}

	public void addDevice(ArrayList<WifiP2pDevice> peerList) {
		if(deviceList == null)
			deviceList = new ArrayList<WifiP2pDevice>();
		deviceList.clear();
		deviceList.addAll(peerList);
        ((WiFiPeerListAdapter) getListAdapter()).notifyDataSetChanged();
        if (deviceList.size() == 0) {
            
            return;
        }
	}
	
	private class WiFiPeerListAdapter extends ArrayAdapter<WifiP2pDevice> {

        private List<WifiP2pDevice> items;

        public WiFiPeerListAdapter(Context context, int textViewResourceId,
                List<WifiP2pDevice> objects) {
            super(context, textViewResourceId, objects);
            items = objects;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getActivity().getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.adapter_devicelist, null);
            }
            WifiP2pDevice device = items.get(position);
            if (device != null) {
            	TextView tvDeviceName = (TextView) v.findViewById(R.id.tvDeviceName);
            	TextView tvDeviceMAC = (TextView) v.findViewById(R.id.tvDeviceMAC);
                if (tvDeviceName != null) {
                	tvDeviceName.setText(device.deviceName);
                }
                if (tvDeviceMAC != null) {
                	tvDeviceMAC.setText(device.deviceAddress);
                }
            }
            return v;
        }
    }
	
	
}

