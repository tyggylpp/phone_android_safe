package com.dragon.phone_mobilesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

public class BootCompleteReceiver extends BroadcastReceiver {

	private SharedPreferences sp;
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		sp=context.getSharedPreferences("config", context.MODE_PRIVATE);
		boolean protect=sp.getBoolean("protect", false);
		if(protect)
		{
			String sim=sp.getString("sim", null);
			TelephonyManager phone=(TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			String sim_number=phone.getSimSerialNumber()+"110";
			System.out.println(sim + " "+sim_number);
	          if(TextUtils.equals(sim_number, sim))
	          {
	        	  System.out.println("绑定的SIM卡和本机一致 ");
	          }else {
	        	  System.out.println("绑定的SIM卡和本机不一致 ");
	        	  //向安全号码发送短信
	        	  String phoneString=sp.getString("phone", "");
	        	  SmsManager smsManager=SmsManager.getDefault();
	        	  smsManager.sendTextMessage(phoneString, null, "sim card is changed", null,null);
			}
		}
		
	}

}
