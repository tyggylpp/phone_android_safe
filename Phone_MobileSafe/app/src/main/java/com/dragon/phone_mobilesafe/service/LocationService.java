package com.dragon.phone_mobilesafe.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

public class LocationService extends Service {
	private MyLocationListener listener;
	private LocationManager locationManager;
	private SharedPreferences sp;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		sp = getSharedPreferences("config", MODE_PRIVATE);
		
		
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		//List<String> list=locationManager.getAllProviders();
		
		Criteria criteria=new Criteria();
		criteria.setCostAllowed(true);//允许付费的eg:3g网络
		String proString=locationManager.getBestProvider(criteria, true);
		
		System.out.println(proString);
		listener = new MyLocationListener();
		locationManager.requestLocationUpdates(proString, 0, 0, listener);
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		locationManager.removeUpdates(listener);// 当activity销毁时,停止更新位置, 节省电量
	}
	class MyLocationListener implements LocationListener
	{


		//位置变化
		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub

			System.out.println("j "+location.getLatitude());
			System.out.println("w "+location.getLongitude());
			System.out.println("精确度 "+location.getAccuracy());
			System.out.println("海拔 "+location.getAltitude());

			sp.edit().putString("location","j:"+location.getLatitude()+";w:"+location.getLongitude()).commit();

           stopSelf();//停止服务

		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

		}

	
	}
	

}
