package com.xfn.sreit.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.xfn.sreit.AppApplication;
import com.xfn.sreit.R;

import android.os.Environment;

public class Tool {

	/**
	 * 复制单个文件
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf.txt
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf.txt
	 * @return boolean
	 */
	public static void copyFile(String oldPath, String newPath) {
		System.out.println(" oldPath " + oldPath + "  newPath " + newPath);
		try {
			int byteread = 0;
			File oldfile = new File(oldPath);
			File newfile = new File(newPath);
			if (oldfile.exists()) { // 文件存在时
				if (!newfile.exists())
					newfile.createNewFile();
				InputStream inStream = new FileInputStream(oldfile); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newfile);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
				fs.close();
			}
		} catch (Exception e) {
			System.out.println("复制单个文件操作出错");
			e.printStackTrace();

		}
	}
	public static String getNewPath(int id){ 
		String path = Environment.getExternalStorageDirectory() + "/"+AppApplication.getInstance().getString(R.string.app_name)+"/";
	  
			File f = new File(path); 
			if (!f.exists())
				f.mkdir();
			path += "things/";
			f = new File(path);
			if (!f.exists())
				f.mkdir();
			path += new SimpleDateFormat("yyyyMMddHHmmss",
					Locale.getDefault()).format(new Date())+id;  
		return path;
	}

	public static String getFileName(String path){ 
		if(path.indexOf(".") != -1)
			path = path.substring(path.indexOf(".")+1,path.length());
		else 
			path = "jpg";
		return path;
	}

	public static String getTemPath(int id){ 
		String path = Environment.getExternalStorageDirectory() + "/"+AppApplication.getInstance().getString(R.string.app_name)+"/";
	  
			File f = new File(path); 
			if (!f.exists())
				f.mkdir();
			path += "temps/";
			f = new File(path);
			if (!f.exists())
				f.mkdir();
			path += new SimpleDateFormat("yyyyMMddHHmmss",
					Locale.getDefault()).format(new Date())+id;  
		return path;
	}

}
