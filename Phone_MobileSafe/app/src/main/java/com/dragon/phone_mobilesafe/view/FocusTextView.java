package com.dragon.phone_mobilesafe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewDebug.ExportedProperty;
import android.widget.TextView;

public class FocusTextView extends TextView {

	//有样式的时候条用此方法
	public FocusTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
//有属性调用此方法
	public FocusTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
//new 对象时调用此方法
	public FocusTextView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	//焦点返回true方便使用marquee属性
	@Override
	@ExportedProperty(category = "focus")
	public boolean isFocused() {
		// TODO Auto-generated method stub
	return true;
	}
	

}
