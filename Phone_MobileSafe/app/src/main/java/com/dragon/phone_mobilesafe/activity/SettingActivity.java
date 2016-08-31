package com.dragon.phone_mobilesafe.activity;

import com.dragon.phone_mobilesafe.R;
import com.dragon.phone_mobilesafe.service.CallSafeService;
import com.dragon.phone_mobilesafe.utils.ServiceStatusUtils;
import com.dragon.phone_mobilesafe.view.SettingItemView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class SettingActivity extends Activity {
	private SharedPreferences sp;
	private SettingItemView item_update;
	private SettingItemView item_black;


	//checkbox 设置为不可勾选要设置三个属性
	//	android:clickable="false"
	//    android:focusable="false"
	//    android:focusableInTouchMode="false"
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_setting);
		sp=getSharedPreferences("config",MODE_PRIVATE);
		initUpdate();
		initBlackUpdate();

	}

	private void initBlackUpdate() {

		item_black=(SettingItemView) findViewById(R.id.stv_blacknumber);
		//item_black.setCheckon(updateAuto);
       boolean serviceRuning= ServiceStatusUtils.getServiceRunning(this,getPackageName()+".service.CallSafeService");
       boolean serviceUpdate=sp.getBoolean("updateblack",false);



	   item_black.setCheckon(serviceRuning||serviceUpdate);
		item_black.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(item_black.isCheckon())
				{
					item_black.setCheckon(false);
					stopService(new Intent(SettingActivity.this, CallSafeService.class));
				}
				else {
					item_black.setCheckon(true);
					startService(new Intent(SettingActivity.this, CallSafeService.class));

				}

				sp.edit().putBoolean("updateblack",item_black.isCheckon()).commit();
			}
		});


	}

	private void	initUpdate()
   {
	   boolean updateAuto = sp.getBoolean("updateAuto", true);
	   item_update=(SettingItemView) findViewById(R.id.stv_update);
	   item_update.setCheckon(updateAuto);

	   item_update.setOnClickListener(new OnClickListener() {

		   @Override
		   public void onClick(View v) {
			   // TODO Auto-generated method stub
			   if(item_update.isCheckon())
			   {
				   item_update.setCheckon(false);
				   sp.edit().putBoolean("updateAuto", false).commit();
				   //item_update.setDesc("自动更新设置已关闭");
			   }
			   else {
				   item_update.setCheckon(true);
				   sp.edit().putBoolean("updateAuto", true).commit();
				   //item_update.setDesc("自动更新设置已开启");
			   }
		   }
	   });

   }

}
