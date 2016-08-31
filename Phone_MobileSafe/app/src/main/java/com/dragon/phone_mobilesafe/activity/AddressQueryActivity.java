package com.dragon.phone_mobilesafe.activity;
import com.dragon.phone_mobilesafe.R;
import com.dragon.phone_mobilesafe.db.ado.AddressAdo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.TextureView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class AddressQueryActivity extends Activity {
	private EditText et_number;
	private TextView tv_address;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_numberaddrquery);
		et_number = (EditText)findViewById(R.id.et_number);
		tv_address = (TextView)findViewById(R.id.tv_address);
		et_number.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				tv_address.setText(AddressAdo.getAddress(et_number.getText().toString()));
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public void query(View view) {
		 String phone=et_number.getText().toString();
		 if(TextUtils.isEmpty(phone))vibrate();
         tv_address.setText(AddressAdo.getAddress(phone));
	}
	private void vibrate() {
		Vibrator vibrator=(Vibrator)getSystemService(VIBRATOR_SERVICE);//  android.permission.VIBRATE
		vibrator.vibrate(new long[]{1000,2000,3000}, -1);
		vibrator.cancel();
	}
}
