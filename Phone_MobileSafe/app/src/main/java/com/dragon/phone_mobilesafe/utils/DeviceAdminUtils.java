package com.dragon.phone_mobilesafe.utils;

import com.dragon.phone_mobilesafe.activity.ContactActivity;
import com.dragon.phone_mobilesafe.service.DeviceAdmin;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class DeviceAdminUtils extends ContextWrapper {
	private DevicePolicyManager mDPM;
	private ComponentName componentName;
	public DeviceAdminUtils(Context base) {
		super(base);
		
		active();
	}
	

	public void active() {
		 System.out.println("active");
		mDPM = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE); 
        componentName = new ComponentName(this, DeviceAdmin.class);
       
        if(!mDPM.isAdminActive(componentName))
        {
        	Intent intent=new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        	intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
        	intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,"Additional text explaining why this needs to be added");
        	startActivity(intent);
        	
        }else {
			Toast.makeText(this, "必须激活功能", Toast.LENGTH_SHORT).show();
		}
	}
}
