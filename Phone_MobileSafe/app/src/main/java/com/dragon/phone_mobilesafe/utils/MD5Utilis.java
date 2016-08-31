package com.dragon.phone_mobilesafe.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utilis {
	static public String encode(String string)
	{

		try {
			MessageDigest messageDigest=MessageDigest.getInstance("md5");
			byte[]bytes=messageDigest.digest(string.getBytes());
			StringBuffer sb=new StringBuffer();
			for (byte b : bytes) {
				int i=b&0xff;
				String hexString=Integer.toHexString(i);
				if(hexString.length()<2)
				{
					hexString="0"+hexString;
				}
				sb.append(hexString);
			}

			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "";
	}
}
