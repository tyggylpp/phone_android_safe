package com.dragon.phone_mobilesafe.activity;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.dragon.phone_mobilesafe.R;
import com.dragon.phone_mobilesafe.service.DeviceAdmin;

public class Setting04 extends BaseSetUpActivity {

	private DevicePolicyManager mDPM;
	private ComponentName componentName;
	private CheckBox cb_protect;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting04);
		
		mDPM = (DevicePolicyManager)getSystemService(Context.DEVICE_POLICY_SERVICE);
		componentName= new ComponentName(this, DeviceAdmin.class);
		
		cb_protect = (CheckBox) findViewById(R.id.cb_protect);
		cb_protect.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked)
				{
					cb_protect.setChecked(true);
					cb_protect.setText("防盗保护已经开启");
				}else {
					cb_protect.setChecked(false);
					cb_protect.setText("防盗保护没有开启");
				}
			}
		});
		boolean protect=sp.getBoolean("protect", false);
		if(protect)
		{
			cb_protect.setChecked(true);
			cb_protect.setText("防盗保护已经开启");
		}else {
			cb_protect.setChecked(false);
			cb_protect.setText("防盗保护没有开启");
		}
	}

	@Override
	public void showPreviousPage() {
		// TODO Auto-generated method stub
		startActivity(new Intent(this,Setting03.class));
		finish();
	}
	@Override
	public void showNextPage() {
		
		if(!mDPM.isAdminActive(componentName))
		{
			Intent intent=new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
			intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,componentName);
			intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,"Additional text explaining why this needs to be added");
			startActivityForResult(intent, 0);
			
		}
		 else{
           setUpFinish();        				
		}
		
		
		
	}
	private void setUpFinish() {
		if(cb_protect.isChecked())
			sp.edit().putBoolean("protect", true).commit();
		else 		sp.edit().putBoolean("protect", false).commit();

		// TODO Auto-generated method stub
		sp.edit().putBoolean("configd", true).commit();
		startActivity(new Intent(this,LostFindActivity.class));
		finish();
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    
		if(requestCode==0&& resultCode==Activity.RESULT_OK)
		{
			setUpFinish();
		}
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	  
}
