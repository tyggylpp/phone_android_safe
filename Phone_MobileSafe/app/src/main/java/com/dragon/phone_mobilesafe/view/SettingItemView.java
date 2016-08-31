package com.dragon.phone_mobilesafe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dragon.phone_mobilesafe.R;

public class SettingItemView extends RelativeLayout {
	final private String NAMESPACE="http://schemas.android.com/apk/res/com.dragon.phone_mobilesafe";
	private TextView tv_title;
	private TextView tv_desc;

	//private CheckBox checkBox;
	private String desc_title;
	private String desc_on;
	private String desc_off;
	
	private CheckBox check_Status;
	//private String desc_off;
	public SettingItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		initView();
	}

	public SettingItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		desc_title=attrs.getAttributeValue(NAMESPACE, "title");
		desc_on=attrs.getAttributeValue(NAMESPACE, "desc_on");
		desc_off=attrs.getAttributeValue(NAMESPACE, "desc_off");
		initView();

	}

	public SettingItemView(Context context) {
		super(context);
		initView();
		// TODO Auto-generated constructor stub
	}
	private void initView() {
		View.inflate(getContext(), R.layout.view_setting_item, this);
		tv_title=(TextView)findViewById(R.id.tv_title);
		tv_desc=(TextView) findViewById(R.id.tv_desc);
		check_Status=(CheckBox) findViewById(R.id.cb_status);
		setTitle(desc_title);
	}

	public  void setTitle(String string) {
		this.desc_title=string;
		tv_title.setText(string);
	}
	
	private void setDesc(String desc) {
		tv_desc.setText(desc);
	}
    public boolean isCheckon() {
		return check_Status.isChecked();
	}
	
	public void setCheckon(boolean check) {		
		check_Status.setChecked(check);
		if(check)setDesc(desc_on);
		else setDesc(desc_off);
		
	}
	





}
