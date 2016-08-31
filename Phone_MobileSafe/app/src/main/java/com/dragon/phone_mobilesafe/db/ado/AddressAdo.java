package com.dragon.phone_mobilesafe.db.ado;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class AddressAdo {
	private static final String PATH="data/data/com.dragon.phone_mobilesafe/files/address.db";//由于无法直接访问assert，所以要把文件拷贝到此目录下
	public static String getAddress (String number) {
		String address="未知号码";
		//正则表达式匹配:手机号码 1 +(3,4,5,6,7,8) +9位数字
		SQLiteDatabase db=SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READONLY);
		Cursor cursor=null;
		if(number.matches("^1[3-8]\\d{9}$"))
		{


			cursor=db.rawQuery("select location from data2 where id=(select outkey from data1 where id=?)", 
					new String[]{number.substring(0, 7)});
			if(cursor.moveToNext())
			{
				address=cursor.getString(0);

			}
			cursor.close();
			

		}else if(number.matches("^\\d+$")) {//匹配数值

			switch (number.length()) {
			case 3:
				address="报警电话";
				break;
			case 4:
				address="模拟器";
				break;
			case 5:
				address="客服电话";
				break;
			case 7:
			case 8:
				address="本地电话";
				break;
			default:
				if(number.startsWith("0")&&number.length()>=10)
				{
					cursor=db.rawQuery("select location from data2 where area =?",new String[]{number.substring(1, 4)});
					if(cursor.moveToNext())
					{
						address=cursor.getString(0);
						
					}else {
						cursor.close();	
						cursor=db.rawQuery("select location from data2 where area =?",new String[]{number.substring(1, 3)});
						if(cursor.moveToNext())
						{
							address=cursor.getString(0);
						}
						cursor.close();
					}
					
				}
				break;
			}
		}
		db.close();
		return address;

	}
}
