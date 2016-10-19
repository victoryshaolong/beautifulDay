package com.easemob.chatuidemo.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;

public class BitmapSaveUtils {
	public static String sdDir = Environment.getExternalStorageDirectory()+"/";
	public static String sdDirpanason = sdDir+"alaCampus/";
	
	

	public static String savePicToSdcard(Bitmap bitmap) {
		String filePath="";
//		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		if (bitmap == null) {
			return filePath;
		} else {
			File file = new File(sdDirpanason);
			if (!file.exists()) {
				file.mkdirs();
			}
			filePath =sdDirpanason+System.currentTimeMillis()+".jpg";
			File destFile = new File(filePath);
			OutputStream os = null;
			try {
				os = new FileOutputStream(destFile);
				bitmap.compress(CompressFormat.JPEG, 100, os);
				os.flush();
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
				filePath = "";
			}
		}
		return filePath;
	}

}
