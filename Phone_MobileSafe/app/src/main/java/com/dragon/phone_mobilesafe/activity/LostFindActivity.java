package com.dragon.phone_mobilesafe.activity;

import android.R.bool;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dragon.phone_mobilesafe.R;

public class LostFindActivity extends Activity {
	private SharedPreferences sp;
	private TextView tvPhone;
	private ImageView ivProtect;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		sp=getSharedPreferences("config", MODE_PRIVATE);
		boolean configed=sp.getBoolean("configd", false);
		if(configed)
		{
			
			setContentView(R.layout.activity_lostfind);
			tvPhone = (TextView)findViewById(R.id.tv_safePhone);
			String safePhone=sp.getString("phone", "");
			tvPhone.setText(safePhone);
			
			ivProtect=(ImageView)findViewById(R.id.iv_lock);
			boolean protect=sp.getBoolean("protect", false);
			if(protect)
			{
				ivProtect.setImageResource(R.drawable.lock);
			}else {
				ivProtect.setImageResource(R.drawable.unlock);
			}
			
		}else {
			reSetup();
		}
	}
	private void reSetup() {//设置向导
		startActivity(new Intent(this,Setting01.class));
		finish();
	}
	//重新进入设置向导
	public void reEnter(View view) {
		startActivity(new Intent(this,Setting01.class));
		finish();
		
	}
}
