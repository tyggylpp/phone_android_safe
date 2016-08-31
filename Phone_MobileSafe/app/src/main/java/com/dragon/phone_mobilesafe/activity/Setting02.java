package com.dragon.phone_mobilesafe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.dragon.phone_mobilesafe.R;
import com.dragon.phone_mobilesafe.view.SettingItemView;

public class Setting02 extends BaseSetUpActivity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting02);
		final SettingItemView siv_Sim=(SettingItemView)(findViewById(R.id.stv_sim));
		siv_Sim.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(siv_Sim.isCheckon())
				{
					siv_Sim.setCheckon(false);
					sp.edit().remove("sim").commit();
				}else {
					sp.edit().putString("sim", getSimSerialString()).commit();
					siv_Sim.setCheckon(true);
				}
			}
		});

		String number = getSimSerialString();
		String sim_Serial=sp.getString("sim", null);
		System.out.println("sim "+sim_Serial);
		if(TextUtils.equals(sim_Serial, number))
		{
			siv_Sim.setCheckon(true);
		}
	}
	private String getSimSerialString() {
		TelephonyManager telephonyManager=(TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		String number = telephonyManager.getSimSerialNumber();
		return number;
	}
	@Override
	public void showPreviousPage() {
		// TODO Auto-generated method stub
		startActivity(new Intent(Setting02.this,Setting01.class));
		finish();
	}
	@Override
	public void showNextPage() {

		String sim=sp.getString("sim", null);
		if(TextUtils.isEmpty(sim))
		{
			Toast.makeText(Setting02.this, "sim卡必须绑定", Toast.LENGTH_SHORT).show();
			return;
		}

		startActivity(new Intent(Setting02.this,Setting03.class));
		finish();
	}
}
