package com.dragon.phone_mobilesafe.activity;

import com.dragon.phone_mobilesafe.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AToolActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_atool);
	}
	public void numberAddQuery(View view) {
		startActivity(new Intent(this, AddressQueryActivity.class));
	}
}
