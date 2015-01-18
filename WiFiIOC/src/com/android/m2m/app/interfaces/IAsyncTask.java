package com.android.m2m.app.interfaces;

public interface IAsyncTask {
	public void showProgressBar();
	public void hideProgressBar();
	public Object doInBackGround();
	public void processDataAferDownload(Object object);
}
