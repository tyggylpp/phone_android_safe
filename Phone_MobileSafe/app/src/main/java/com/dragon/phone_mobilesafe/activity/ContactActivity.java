package com.dragon.phone_mobilesafe.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.dragon.phone_mobilesafe.R;

public class ContactActivity extends Activity {
	private ListView lv_contactListView;
	private ArrayList<HashMap<String, String>> list_contact=new ArrayList<HashMap<String,String>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact);
		list_contact=readContact();
		lv_contactListView = (ListView) findViewById(R.id.lv_contact);
		lv_contactListView.setAdapter(new SimpleAdapter(ContactActivity.this,
				list_contact, R.layout.activity_contact_item, new String[] {
				"name", "phone" }, new int[] { R.id.tv_name,
				R.id.tv_phone }));
		lv_contactListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String phone=list_contact.get(position).get("phone").toString();
				Intent intent=new Intent();
				intent.putExtra("phone", phone);
				setResult(Activity.RESULT_OK, intent);
				finish();

			}
		});

	}
	private ArrayList<HashMap<String, String>> readContact() {
		ArrayList<HashMap<String, String>> list=new ArrayList<HashMap<String,String>>();
		ContentResolver resolver=getContentResolver();
		Cursor cursorId=resolver.query(Uri.parse("content://com.android.contacts/raw_contacts"), new String[]{"contact_id"},
				null, null, null);
		while (cursorId.moveToNext()) {
			HashMap<String, String> map=new HashMap<String, String>();
			String id=cursorId.getString(0).toString();
			Cursor cursor=resolver.query(Uri.parse("content://com.android.contacts/data"), new String[]{"data1","mimetype"}, "raw_contact_id=?", new String[]{id}, null);
			while (cursor.moveToNext()) {
				String data1=cursor.getString(0).toString();
				String mimetype=cursor.getString(1).toString();
				if(mimetype.equals("vnd.android.cursor.item/name"))
				{
					map.put("name", data1);
				}else if (mimetype.equals("vnd.android.cursor.item/phone_v2")) {
					map.put("phone", data1);
				}
			}
			list.add(map);
			cursor.close();
		}
		cursorId.close();
		return list;
	}

}
