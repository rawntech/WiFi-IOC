package com.android.m2m.app.fragment;


import com.android.m2m.app.R;
import com.android.m2m.app.interfaces.DeviceActionListener;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.WifiP2pManager.ChannelListener;
import android.net.wifi.p2p.WifiP2pManager.ConnectionInfoListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DeviceDetailsFragment extends Activity implements OnClickListener,ChannelListener,
DeviceActionListener,ConnectionInfoListener {

	String deviceName, deviceAddress;
	TextView tvDeviceName, tvAddress,tvDeviceStatus;
	Button btnConnect, btnDisconnect;
	ProgressDialog progressDialog = null;
	WifiP2pInfo info;
	WifiP2pDevice device;
	WifiP2pManager manager;
	Channel channel;
	
	public DeviceDetailsFragment(WifiP2pManager _manager,Channel _channel,WifiP2pInfo _info) {
		manager=_manager;
		channel=_channel;
		info=_info;
	}
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_device_details);

		initViews();

		Bundle extras = getIntent().getExtras();
		if (extras != null) {

			deviceName = extras.getString("DEVICE_NAME");
			deviceAddress = extras.getString("DEVICE_ADDRESS");
			tvDeviceName.setText("Device Name : "+deviceName);
			tvAddress.setText("Device Address : "+deviceAddress);
		}

	}

	private void initViews() {
		tvDeviceName = (TextView) findViewById(R.id.tvDeviceName);
		tvAddress = (TextView) findViewById(R.id.tvAddress);
		tvDeviceStatus = (TextView) findViewById(R.id.tvDeviceStatus);
		btnConnect = (Button) findViewById(R.id.btnConnect);
		btnDisconnect = (Button) findViewById(R.id.btnDisconnect);
		btnDisconnect.setVisibility(View.GONE);
		
		btnConnect.setOnClickListener(this);
		btnDisconnect.setOnClickListener(this);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void onClick(View view) {

		if(view.getId()==R.id.btnConnect){
			
			WifiP2pConfig config = new WifiP2pConfig();
			config.deviceAddress = deviceAddress;
			config.wps.setup = WpsInfo.PBC;
			if (progressDialog != null && progressDialog.isShowing()) {
				progressDialog.dismiss();
			}
			progressDialog = ProgressDialog.show(this,
					"Press back to cancel", "Connecting to :"
							+ deviceAddress, true, true
					);
			this.connect(config);
			
		}else if(view.getId()==R.id.btnDisconnect){
			
		}
	}

	@Override
	public void showDetails(WifiP2pDevice device) {
		// TODO Auto-generated method stub
		
	}
	
	public WifiP2pDevice getDevice() {
		return device;
	}
	
	/**
	 * Update UI for this device.
	 * 
	 * @param device
	 *            WifiP2pDevice object
	 */
	public void updateThisDevice(WifiP2pDevice _device) {
		this.device = _device;
		
		
		deviceName = device.deviceName;
		deviceAddress = device.deviceAddress;
		
		tvDeviceName.setText("Device Name : "+deviceName);
		tvAddress.setText("Device Address : "+deviceAddress);
		tvDeviceStatus.setText(device.status);
	}

	@Override
	public void cancelDisconnect() {
		if (manager != null) {
			//Code for Cancel connect
		}
		
	}

	@Override
	public void connect(WifiP2pConfig config) {
		manager.connect(channel, config, new ActionListener() {

			@Override
			public void onSuccess() {
				tvDeviceStatus.setText("Connected");
				Toast.makeText(DeviceDetailsFragment.this, "Connect succesfully...", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onFailure(int reason) {
				Toast.makeText(DeviceDetailsFragment.this,
						"Connect failed. Retry:" + reason, Toast.LENGTH_SHORT)
						.show();
			}
		});
		
	}

	@Override
	public void disconnect() {
		manager.removeGroup(channel, new ActionListener() {

			@Override
			public void onSuccess() {
				Toast.makeText(DeviceDetailsFragment.this, "Disconnect Succesfull",
						Toast.LENGTH_SHORT).show();

			}

			@Override
			public void onFailure(int reasonCode) {
				Log.d("m2m", "Disconnect failed. Reason :" + reasonCode);
				Toast.makeText(DeviceDetailsFragment.this, "Disconnect failed , Reason : "+reasonCode,
						Toast.LENGTH_SHORT).show();

			}
		});
		
	}

	@Override
	public void onChannelDisconnected() {
	}

	@Override
	public void onConnectionInfoAvailable(final WifiP2pInfo _info) {
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
		}
		info=_info;
		
	}

}
