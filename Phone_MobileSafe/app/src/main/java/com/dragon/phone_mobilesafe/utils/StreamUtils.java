package com.dragon.phone_mobilesafe.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
//将inputStream 转换成String
public class StreamUtils {

	public static String readFromStream (InputStream in) throws IOException {
		ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
		byte [] bytes=new byte[1024];//1KB 
		int len=0;
		while ((len=in.read(bytes))!=-1) {
			outputStream.write(bytes, 0, len);
		}
		in.close();
	    outputStream.close();
	    
		return outputStream.toString();
	}
}
