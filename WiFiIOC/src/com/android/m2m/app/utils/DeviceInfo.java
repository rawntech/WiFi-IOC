package com.android.m2m.app.utils;

import com.google.gson.annotations.SerializedName;

public class DeviceInfo {
	@SerializedName("device_name")
	public String deviceName;
	@SerializedName("address")
	public String deviceAddress;
	@SerializedName("device_Status")
	public String deviceStatus;

}
