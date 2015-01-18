package com.android.m2m.app.adapters;

import java.util.List;

import com.android.m2m.app.R;
import com.android.m2m.app.utils.CommonTasks;

import android.content.Context;
import android.net.wifi.p2p.WifiP2pDevice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class WiFiPeerListAdapter extends ArrayAdapter<WifiP2pDevice> {
	private List<WifiP2pDevice> items;
	Context context;

	public WiFiPeerListAdapter(Context _context,
			int textViewResourceId, List<WifiP2pDevice> objects) {
		super(_context,  textViewResourceId, objects);
		items = objects;
		context = _context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view=convertView;
		
		if(view==null){
			LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view=inflater.inflate(R.layout.device_list_item, null);
		}
		WifiP2pDevice device=items.get(position);
		if(device!=null){
			TextView status=(TextView) view.findViewById(R.id.device_details);
			TextView tvDeviceAddress=(TextView)view.findViewById(R.id.tvDeviceAddress);
			TextView tvDeviceName=(TextView)view.findViewById(R.id.tvDeviceName);
			
			if(device.deviceName!=null){
				tvDeviceName.setText(device.deviceName);
			}
			if(device.deviceAddress!=null)
				tvDeviceAddress.setText(device.deviceAddress);
			
				status.setText(CommonTasks.getDeviceStatus(device.status));
			
		}
		return view;
	}

}
