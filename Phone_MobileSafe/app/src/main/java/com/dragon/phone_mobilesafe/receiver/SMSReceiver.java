package com.dragon.phone_mobilesafe.receiver;

import com.dragon.phone_mobilesafe.R;
import com.dragon.phone_mobilesafe.R.raw;
import com.dragon.phone_mobilesafe.service.LocationService;
import com.dragon.phone_mobilesafe.utils.DeviceAdminUtils;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.telephony.SmsMessage;

public class SMSReceiver extends BroadcastReceiver {

	private DevicePolicyManager mdpM;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		mdpM = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);

		Object[] objects = (Object[]) intent.getExtras().get("pdus");
		String body="";
		String addr="";
		for (Object object : objects) {
			SmsMessage smsManage=SmsMessage.createFromPdu((byte[]) object);
			addr=smsManage.getOriginatingAddress();
			body=body+smsManage.getMessageBody();
			if("#*alarm*#".equals(body))
			{
				System.out.println("abortbroadcast");

				MediaPlayer player=MediaPlayer.create(context, R.raw.ylzs);
                player.setVolume(1f, 1f);
                player.setLooping(true);
                player.start();
				abortBroadcast();
			}else if ("#*location*#".equals(body)) {
				context.startService(new Intent(context, LocationService.class));
				SharedPreferences sp=context.getSharedPreferences("config",context.MODE_PRIVATE);
			   String location=	sp.getString("location", "");
			   System.out.println(location);
			   abortBroadcast();
			}else if("#*wipedata*#".equals(body))
			{
				
				System.out.println("wipedata");
				mdpM.wipeData(0);
				abortBroadcast();
			}else if ("#*lockscreen*#".equals(body)) {
				System.out.println("lockscreen");
				
				mdpM.lockNow();
				abortBroadcast();
			}

		}


	}

}
