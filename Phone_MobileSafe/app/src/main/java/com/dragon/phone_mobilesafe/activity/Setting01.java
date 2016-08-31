package com.dragon.phone_mobilesafe.activity;

import com.dragon.phone_mobilesafe.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Setting01 extends BaseSetUpActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting01);
	}
	
	@Override
	public void showPreviousPage() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void showNextPage() {
		// TODO Auto-generated method stub
		startActivity(new Intent(Setting01.this,Setting02.class));
		finish();
	}
	
}
