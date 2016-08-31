package com.dragon.phone_mobilesafe.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.telephony.SmsMessage;

import com.dragon.phone_mobilesafe.db.ado.BlackNumberDao;

public class CallSafeService extends Service {

    private BlackNumberDao dao;
    private InnerReciver innerReciver;

    public CallSafeService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        dao = new BlackNumberDao(this);
        innerReciver = new InnerReciver();
        IntentFilter intentFilter=new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        intentFilter.setPriority(Integer.MAX_VALUE);
        registerReceiver(innerReciver,intentFilter);

    }

    @Override
    public void onDestroy() {
       unregisterReceiver(innerReciver);
        super.onDestroy();
    }

    private  class InnerReciver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
          Object[] obj= (Object[]) intent.getExtras().get("pdus");//过长可能截断所以要循环拼接
            for (Object object:obj)
            {
               SmsMessage sms= SmsMessage.createFromPdu((byte[]) object);
                String phone=sms.getOriginatingAddress();
                String content=sms.getMessageBody();
                System.out.println(phone+" "+content);
                String mode= dao.findNumber(phone);
                /*
                * 1 all
                * 2 phone
                *3  sms
                * */
                if(mode.equals("1")||mode.equals("3")) {
                    abortBroadcast();
                    return;
                }
                //过滤 词

                if(content.equals("hello"))abortBroadcast();


            }
        }
    }

}
