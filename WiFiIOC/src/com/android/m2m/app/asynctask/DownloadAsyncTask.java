package com.android.m2m.app.asynctask;

import com.android.m2m.app.interfaces.IAsyncTask;

import android.os.AsyncTask;

public class DownloadAsyncTask extends AsyncTask<Void, Void, Object>{
	IAsyncTask asyncTask;
	
	public DownloadAsyncTask(IAsyncTask activity) {
		asyncTask = activity;
	}
	
	@Override
	protected void onPreExecute() {
		if(asyncTask != null)
			asyncTask.showProgressBar();
	}

	@Override
	protected Object doInBackground(Void... params) {
		try{
			if(asyncTask != null)
				return asyncTask.doInBackGround();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(Object data) {
		if(asyncTask != null){
			asyncTask.hideProgressBar();
			asyncTask.processDataAferDownload();
		}
	}

}
