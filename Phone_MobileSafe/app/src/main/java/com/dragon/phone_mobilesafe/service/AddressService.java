package com.dragon.phone_mobilesafe.service;

import com.dragon.phone_mobilesafe.db.ado.AddressAdo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class AddressService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		System.out.println(" AddressService is listen");
		Log.d("LISTEN", "onCreate: AddressService is listen");
		TelephonyManager tm=(TelephonyManager)getSystemService(TELEPHONY_SERVICE);
		tm.listen(new MyListener(), PhoneStateListener.LISTEN_CALL_STATE);//监听来电状态

	}
	class MyListener extends PhoneStateListener
	{
	@Override
	public void onCallStateChanged(int state, String incomingNumber) {
		// TODO Auto-generated method stub
		
		switch (state) {
		case TelephonyManager.CALL_STATE_RINGING://响铃 
			System.out.println("call _state ring......");
			Toast.makeText(AddressService.this,AddressAdo.getAddress(incomingNumber) , Toast.LENGTH_LONG).show();
			break;

		default:
			break;
		}
		super.onCallStateChanged(state, incomingNumber);
	}	
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
