package com.dragon.phone_mobilesafe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dragon.phone_mobilesafe.R;

public class Setting03 extends BaseSetUpActivity {
	private EditText etphone;
	@Override

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting03);
		etphone = (EditText)findViewById(R.id.et_phone);
		String phone= sp.getString("phone", "");
		etphone.setText(phone);
		
	}
	public void ContactSelector(View view) {
		 startActivityForResult(new Intent(Setting03.this, ContactActivity.class),1);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode==RESULT_OK)
		{
		  String phone=data.getStringExtra("phone").toString().replaceAll(" ", "").replaceAll("-", "").trim();
		  etphone.setText(phone);
		}
	};

	@Override
	public void showPreviousPage() {
		// TODO Auto-generated method stub
		startActivity(new Intent(this,Setting02.class));
		finish();
	}
	@Override
	public void showNextPage() {
		String phone=etphone.getText().toString().trim();
		phone=phone.replaceAll("-", "").replaceAll(" " , "");
		if(TextUtils.isEmpty(phone))
		{
			Toast.makeText(Setting03.this, "联系人不能为空", Toast.LENGTH_SHORT).show();
			return;
		}
		sp.edit().putString("phone", phone).commit();



		// TODO Auto-generated method stub
		startActivity(new Intent(this,Setting04.class));
		finish();
	}
}
