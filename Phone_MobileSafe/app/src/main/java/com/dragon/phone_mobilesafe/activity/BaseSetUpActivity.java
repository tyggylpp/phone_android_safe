package com.dragon.phone_mobilesafe.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.View;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.widget.Toast;

public abstract class BaseSetUpActivity extends Activity {
	private GestureDetector gDetector;
	public SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		sp= getSharedPreferences("config", MODE_PRIVATE);
		gDetector = new GestureDetector(this,new SimpleOnGestureListener()
		{
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2,
					float velocityX, float velocityY) {
				// TODO Auto-generated method stub
				//纵向滑动过大
				if (e2.getRawY() - e1.getRawY() > 100) {

					Toast.makeText(BaseSetUpActivity.this, "纵向滑动过大", Toast.LENGTH_SHORT)
					.show();
					return true;
				}
				if (Math.abs(velocityX) > 200) {
					Toast.makeText(BaseSetUpActivity.this, "滑动速度过慢", Toast.LENGTH_SHORT)
					.show();
					return true;
				}
				if (e2.getRawX() - e1.getRawX() > 100) {
					showNextPage();
					return true;
				}
				if (e1.getRawX() - e2.getRawX() > 100) {
					showPreviousPage();
					return true;
				}

				return super.onFling(e1, e2, velocityX, velocityY);
			}
		});
	}
	public abstract void showPreviousPage();
	public	abstract void showNextPage();
	public void Next(View view) {
		showNextPage();
	}

	public void Previous(View view) {
		showPreviousPage();
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		gDetector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}
}
