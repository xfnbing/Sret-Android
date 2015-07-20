package com.xfn.sreit.control;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList; 
import java.util.List;

import com.xfn.sreit.AppApplication;
import com.xfn.sreit.R;
import com.xfn.sreit.value.FileValue;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase; 
import android.util.Log;

public class DataBase {
	private SQLiteDatabase db = null;
	private Context context = null;
	// 数据库文件名
	private final static String DB_FILE_NAME = "filepage.db";
	// 文件的路径
	private final static String URL = AppApplication.getInstance()
			.getFilesDir().getPath();
	private File dbFile = null;

	public DataBase(Context context) {
		this.context = context;
		// 打开数据库
		if (copyDBToCache()) {
			dbFile = new File(URL, DB_FILE_NAME);
			db = SQLiteDatabase.openOrCreateDatabase(dbFile, null);
		}
	}

	// 关闭数据库
	public void close() {
		if (db != null && db.isOpen()) {
			db.close();
		}
		if (dbFile != null) {
			dbFile.exists();
		}
		System.gc();
	}

	private boolean copyDBToCache() {
		try {
			// 判断程序内存中是否有拷贝后的文件
			if (!(new File(URL, DB_FILE_NAME)).exists()) {
				InputStream is = context.getResources().openRawResource(
						R.raw.filepage);
				FileOutputStream fos = context.openFileOutput(DB_FILE_NAME,
						Context.MODE_WORLD_READABLE);
				// 一次拷贝的缓冲大小1M
				byte[] buffer = new byte[1024 * 1024];
				int count = 0;
				// 循环拷贝数据库文件
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
				}
				fos.close();
				is.close();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public void addToDataBases(FileValue fv) {
		if (db != null && db.isOpen()) {
			db.execSQL("insert into page values("+fv.getId()+",'" + fv.getOldPath() + ",'"
					+ fv.getNewPath() + "','" + fv.getName() + "');");

		}
		
	}

	public void addAllToDataBases(List<FileValue> list) {
		if (db != null && db.isOpen()) {
			if (list != null) {
				for (FileValue fv : list){
					String sql = "insert into page values("+fv.getId()+",'" + fv.getOldPath() + "','"
							+ fv.getNewPath() + "','" + fv.getName() + "');";
					db.execSQL(sql);
					System.out.println(sql);
				}
			}

		}
		
	}

	public List<FileValue> searchAllFromDataBases() {
		List<FileValue> list = new ArrayList<FileValue>();
		FileValue map = null;
		if (db != null && db.isOpen()) {
			Cursor cs = db.rawQuery("select * from page ", null);
			// 判断查询结构是否返回数据
			if (cs != null && cs.getCount() != 0) {
				while (cs.moveToNext()) {
					map = new FileValue();
					map.setId(cs.getInt(cs.getColumnIndex("_id")));
					map.setOldPath(cs.getString(cs
							.getColumnIndex("old_path")));
					map.setNewPath(cs.getString(cs.getColumnIndex("new_path")));
					map.setName(cs.getString(cs.getColumnIndex("name")) );
					list.add(map);
				}
			}
		}
		Log.i("list.size", list.size() + "");
		
		return list;
	}

	public void deleteFromDataBases(int _id) {
		if (db != null && db.isOpen()) { 
			db.delete("page", "_id = ?",
					new String[] { String.valueOf(_id) });
		}
		
	}

	public int getLastRawId() {
		 int id = 0;
		if (db != null && db.isOpen()) {
			Cursor cs = db.rawQuery("select * from page order by _id desc", null);
			// 判断查询结构是否返回数据
			if (cs != null && cs.getCount() != 0) {
				while (cs.moveToNext()) { 
					id = cs.getInt(cs.getColumnIndex("_id")); 
					break;
				}
			}
		}
		Log.i("id",id + "");
		
		return id;
	}
	public void closeDB(){
		this.close();
	}
}
