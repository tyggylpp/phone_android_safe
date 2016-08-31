package com.dragon.phone_mobilesafe.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;

import org.apache.http.HttpClientConnection;
import org.json.JSONException;
import org.json.JSONObject;

import com.dragon.phone_mobilesafe.R;
import com.dragon.phone_mobilesafe.utils.StreamUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.pdf.PdfDocument.PageInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

public class SplashActivity extends Activity {
	protected static final int CODE_UPDATE_DIALOG=0;
	protected static final int CODE_URL_ERROR=1;
	protected static final int CODE_NET_ERROR=2;
	protected static final int CODE_JSON_ERROR=3;
	protected static final int CODE_ENTER_HOME=4;//进入主界面
	private String mVersionName;
	private int mVersionCode;
	private String mDesc;
	private String mDownloadUrl;
	Handler handler=new Handler()
	{
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CODE_UPDATE_DIALOG:
				showUpdateDialog();
				break;
			case CODE_URL_ERROR:
				Toast.makeText(SplashActivity.this, "url错误", Toast.LENGTH_SHORT).show();
				enterHome();
				break;
			case CODE_NET_ERROR:
				Toast.makeText(SplashActivity.this, "net错误", Toast.LENGTH_SHORT	).show();
				enterHome();
				break;
			case CODE_JSON_ERROR:
				Toast.makeText(SplashActivity.this, "json错误", Toast.LENGTH_SHORT).show();
				break;
			case CODE_ENTER_HOME:

				enterHome();
				break;
			default:
				break;
			}
		};
	};


	TextView tv_version;
	TextView tv_process;
	private RelativeLayout rlRoot;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		tv_version=(TextView)findViewById(R.id.tv_version);
		tv_process=(TextView)findViewById(R.id.tv_process);
		rlRoot = (RelativeLayout) findViewById(R.id.rl_Root);
		AlphaAnimation animation=new AlphaAnimation(0.3f,1f);
		animation.setDuration(2000);
		rlRoot.startAnimation(animation);

        copyDB("address.db");
		SharedPreferences sp=getSharedPreferences("config", MODE_PRIVATE);
		if(sp.getBoolean("updateAuto", false))
		{
			checkVersion();

		}else {
			handler.sendEmptyMessageDelayed(CODE_ENTER_HOME, 2000);	
		}


	}
	private int getversionCode() {
		int code=-1;
		PackageManager packageManager=getPackageManager();
		try {
			PackageInfo packageInfo=packageManager.getPackageInfo(getPackageName(), 0);
			code=packageInfo.versionCode;
			return code;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return code;
	}
	private void copyDB(String dbName) {
		File file=new File(getFilesDir(), dbName);
		if(!file.exists())
		{
			FileOutputStream out=null;
			InputStream input=null;
			try {
				out=new FileOutputStream(file);
				input= getAssets().open(dbName);
				int len=0;
				byte[] buffer=new byte[1024];


				while ((len=input.read(buffer))!=-1) {
					out.write(buffer, 0, len);	
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				try {
					input.close();
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}
	}
	private String getversionName() {
		String version="";
		PackageManager packageManager=getPackageManager();
		try {
			PackageInfo pageInfo=packageManager.getPackageInfo(getPackageName(), 0);
			int code=pageInfo.versionCode;
			String name=pageInfo.versionName;
			System.out.println( "pageinfo "+code+" "+name);
			return name;

		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return version;
	}
	private void checkVersion() {
		final long startTime=System.currentTimeMillis();
		new Thread(){


			@Override
			public void run() {
				Message message=Message.obtain();
				message.what=CODE_ENTER_HOME;
				HttpURLConnection connection=null;
				try {
					URL url=new URL("http://192.168.56.1/update.json");

					connection=(HttpURLConnection) url.openConnection();
					connection.setReadTimeout(5000);
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(5000);
					connection.connect();
					if(connection.getResponseCode()==200)
					{
						System.out.println("code is 200");
						String content= StreamUtils.readFromStream(connection.getInputStream());
						JSONObject jsonObject=new JSONObject(content);
						mVersionName=jsonObject.getString("versionName");
						mVersionCode=jsonObject.getInt("versionCode");
						mDesc=jsonObject.getString("description");
						mDownloadUrl=jsonObject.getString("downloadUrl");
						System.out.println("mCode "+mVersionCode+" get Code"+getversionCode());
						if(mVersionCode>getversionCode())
						{
							message.what=CODE_UPDATE_DIALOG;

						}else {
							message.what=CODE_ENTER_HOME;
						}
					}



				} catch (MalformedURLException e) {

					message.what=CODE_URL_ERROR;
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("CODE_URL_ERROR");
				}catch (IOException e) {
					message.what=CODE_NET_ERROR;
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println(CODE_NET_ERROR);
				} catch (JSONException e) {
					message.what=CODE_JSON_ERROR;
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("CODE_JSON_ERROR");
				}

				finally{
					long endTime=System.currentTimeMillis();
					if(endTime-startTime<2000)//强制让更新页面显示至少2秒
					{
						try {
							Thread.sleep(2000-(endTime-startTime));
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					handler.sendMessage(message);
					if(connection!=null)connection.disconnect();
				}
			}  
		}.start();
	}
	private void showUpdateDialog() {
		AlertDialog.Builder dialog=new AlertDialog.Builder(this);
		dialog.setTitle("最新版本："+mVersionName);
		dialog.setMessage(mDesc);
		//dialog.setCancelable(false);//不让用户取消体验不好
		dialog.setPositiveButton("立即更新",new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				download();
			}
		});
		dialog.setNegativeButton("以后再说", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				enterHome();
				System.out.println("以后再说");	
			}
		});
		//当用户返回时触发取消事件
		dialog.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				// TODO Auto-generated method stub
				enterHome();
			}
		});
		dialog.show();
	}
	protected void download() {


		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
		{
			tv_process.setVisibility(View.VISIBLE);
			String target=Environment.getExternalStorageDirectory()+"/update.apk";
			System.out.println(target);
			HttpUtils httpUtils=new HttpUtils();
			System.out.println(mDownloadUrl);
			System.out.println(target);
			httpUtils.download(mDownloadUrl, target, new RequestCallBack<File>() {

				@Override
				public void onLoading(long total, long current, boolean isUploading) {
					// TODO Auto-generated method stub
					super.onLoading(total, current, isUploading);
					tv_process.setText("下载进度："+(current/total*100)+" %");
				}


				@Override
				public void onSuccess(ResponseInfo<File> arg0) {
					// TODO Auto-generated method stub
					System.out.println("下载成功");
					//跳转到系统下载页面
					Intent intent=new Intent(Intent.ACTION_VIEW);
					intent.addCategory(Intent.CATEGORY_DEFAULT);
					intent.setDataAndType(Uri.fromFile(arg0.result),"application/vnd.android.package-archive");
					//startActivity(intent);
					startActivityForResult(intent, 0);


				}

				@Override
				public void onFailure(HttpException arg0, String arg1) {
					// TODO Auto-generated method stub
					Toast.makeText(SplashActivity.this, "下载失败", Toast.LENGTH_SHORT).show();

				}
			});


		}else {
			Toast.makeText(SplashActivity.this, "没有找到sdcard", Toast.LENGTH_SHORT).show();	
		}
	}
	private void enterHome() {
		System.out.println("enterHome");

		startActivity(new Intent(this,HomeActivity.class));
		finish();

	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		enterHome();
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
	}


}
