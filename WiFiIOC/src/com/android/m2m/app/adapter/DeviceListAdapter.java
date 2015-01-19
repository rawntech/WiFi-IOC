package com.android.m2m.app.adapter;

import java.util.ArrayList;

import com.android.m2m.app.R;

import android.content.Context;
import android.net.wifi.p2p.WifiP2pDevice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DeviceListAdapter extends BaseAdapter{
	
	Context context;
	ArrayList<WifiP2pDevice> wifiP2pDeviceList;
	WifiP2pDevice wifiP2pDevice;

	public DeviceListAdapter(Context context, int textViewResourceId,
			ArrayList<WifiP2pDevice> objects) {
		this.context = context;
		wifiP2pDeviceList = objects;
	}
	
	@Override
	public int getCount() {
		return wifiP2pDeviceList.size();
	}
	
	@Override
	public WifiP2pDevice getItem(int position) {
		return wifiP2pDeviceList.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		return wifiP2pDeviceList.get(position).hashCode();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View deviceListView = convertView;
		final ViewHolder holder;
		try{
			wifiP2pDevice = wifiP2pDeviceList.get(position);
			if(convertView == null){
				LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
				deviceListView = inflater.inflate(R.layout.adapter_devicelist, null);
				holder = new ViewHolder();
				holder.tvDeviceName = (TextView) deviceListView.findViewById(R.id.tvDeviceName);
				holder.tvDeviceMAC = (TextView) deviceListView.findViewById(R.id.tvDeviceMAC);
			}else{
				holder = (ViewHolder) deviceListView.getTag();
			}
			holder.tvDeviceName.setText(wifiP2pDevice.deviceName);
			holder.tvDeviceMAC.setText(wifiP2pDevice.deviceAddress);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return deviceListView;
	}
	
	public class ViewHolder{
		TextView tvDeviceName;
		TextView tvDeviceMAC;
	}

}
