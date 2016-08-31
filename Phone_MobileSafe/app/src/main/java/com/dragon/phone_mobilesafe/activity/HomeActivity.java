package com.dragon.phone_mobilesafe.activity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dragon.phone_mobilesafe.R;
import com.dragon.phone_mobilesafe.utils.MD5Utilis;
/*
TextView 的
ellipsize="marquee" 属性要获取焦点才有用
可以自定义获取焦点的TextView 或者使用属性android:focusableInTouchMode="true"和android:focusable="true"
 */

public class HomeActivity extends Activity {

	private GridView mGridView;
	private String[] mItems=new String[]{"手机防盗", "通讯卫士", "软件管理", "进程管理",
			"流量统计", "手机杀毒", "缓存清理", "高级工具", "设置中心"};
	private int[] mPics=new int[]{
			R.drawable.home_safe,
			R.drawable.home_callmsgsafe, R.drawable.home_apps,
			R.drawable.home_taskmanager, R.drawable.home_netmanager,
			R.drawable.home_trojan, R.drawable.home_sysoptimize,
			R.drawable.home_tools, R.drawable.home_settings
	};
	private SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		sp=getSharedPreferences("config", MODE_PRIVATE);
		mGridView=(GridView) findViewById(R.id.gv_item);
		mGridView.setAdapter(new AdapterGrid());
		mGridView.setOnItemClickListener(new OnItemClickListener() {//item 监听

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if(position==0)
				{
					showPassWordDialog();
				}else if(position==1)
				{
					startActivity(new Intent(HomeActivity.this,CallSafeActivity2.class));
				}
				else if(position==2)
				{
					startActivity(new Intent(HomeActivity.this,AppManagerActivity.class));
				}
				else if (position==7) {
					startActivity(new Intent(HomeActivity.this,AToolActivity.class));
				}
				else if(position==8)
				{
					startActivity(new Intent(HomeActivity.this,SettingActivity.class));
				}
			}
		});
	}
	private void showPassWordDialog() {
		String pwd=sp.getString("password", null);

		/*pwd==null||pwd.length()==0*/
		if(TextUtils.isEmpty(pwd))
		{

			showPassWordSetDialog();  
		}else {
			showPassWordInputDialog();
		}
	}
	private void showPassWordInputDialog() {
		
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		final AlertDialog dialog=builder.create();
		View view=View.inflate(HomeActivity.this, R.layout.dialog_password_input, null);
		dialog.setView(view,0,0,0,0);
		final EditText etPassWord=(EditText)view.findViewById(R.id.et_password);
		Button btn_ok=(Button) view.findViewById(R.id.btn_OK);
		Button btn_cancel=(Button)view.findViewById(R.id.btn_Cancel);
		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		btn_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String pwd=etPassWord.getText().toString().trim();
				if(TextUtils.isEmpty(pwd))
				{
					Toast.makeText(HomeActivity.this, "输入的密码不能为空", Toast.LENGTH_LONG).show();	
				}else {
					String savepwd=  sp.getString("password", null);
					if(TextUtils.equals(savepwd, MD5Utilis.encode(pwd)))
					{
						dialog.dismiss();
						startActivity(new Intent(HomeActivity.this,LostFindActivity.class));
						//Toast.makeText(HomeActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
					}else {
						Toast.makeText(HomeActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
		dialog.show();

	}
	/**
	 * 弹窗设置密码
	 */
	private void showPassWordSetDialog() {
		
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		final AlertDialog dialog=builder.create();
		View view=View.inflate(this, R.layout.dialog_password, null);

		dialog.setView(view, 0, 0, 0, 0);

		final EditText te_password=(EditText)view.findViewById(R.id.et_password);
		final EditText te_passwordtwo=(EditText) view.findViewById(R.id.et_password_second);
		Button btn_ok=(Button) view.findViewById(R.id.btn_OK);
		Button btn_cancel=(Button) view.findViewById(R.id.btn_Cancel);
		btn_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});


		btn_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String p=te_password.getText().toString().trim();
				String ps=te_passwordtwo.getText().toString().trim();
				
				if(TextUtils.isEmpty(p)||TextUtils.isEmpty(ps))
				{
					Toast.makeText(HomeActivity.this, "输入内容不能为空", Toast.LENGTH_LONG).show();

				}else {
					if(TextUtils.equals(p, ps))
					{
						sp.edit().putString("password", MD5Utilis.encode(p)).commit();
						dialog.dismiss();
					}else {
						Toast.makeText(HomeActivity.this, "两次密码不一致", Toast.LENGTH_LONG).show();
					}
				}
			}
		});

		dialog.show();	
	}
	class AdapterGrid extends BaseAdapter
	{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mItems.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mItems[position];
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View view=mGridView.inflate(HomeActivity.this, R.layout.activity_listitem, null);
			ImageView iv=(ImageView) view.findViewById(R.id.iv_item);
			TextView tv=(TextView) view.findViewById(R.id.tv_item);
			iv.setImageResource(mPics[position]);
			tv.setText(mItems[position]);
			return view;
		}

	}

}
