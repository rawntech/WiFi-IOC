package com.android.m2m.app.base;

import com.android.m2m.app.R;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class WiFiChatBase extends FragmentActivity{
	ActionBar actionBar;
	@Override
	protected void onCreate(Bundle saveInstance) {
		setTheme(R.style.WiFiChat);
		super.onCreate(saveInstance);
		createActionBarMenu();
	}

	private void createActionBarMenu() {
		actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowTitleEnabled(true);
	}
}
