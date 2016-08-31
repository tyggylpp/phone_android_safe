package com.dragon.phone_mobilesafe.utils;

import android.app.ActivityManager;
import android.app.ApplicationErrorReport;
import android.content.Context;

import java.util.List;

/**
 * Created by pc on 2016-08-23.
 */
public class ServiceStatusUtils  {
    public static  boolean getServiceRunning(Context context,String name)
    {
       ActivityManager manager= (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo>list= manager.getRunningServices(100);//返回前100条的开启的服务
        for (ActivityManager.RunningServiceInfo info:list
             ) {
           if(name.equals(info.service.getClassName()))return true;
        }

        return false;
    }
}
