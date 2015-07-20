package com.xfn.sreit;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.xfn.sreit.adapter.LoveAdapter;
import com.xfn.sreit.control.DataBase;
import com.xfn.sreit.tool.Tool;
import com.xfn.sreit.value.FileValue;
import com.xfn.sreit.value.Photo;

import android.support.v7.app.ActionBarActivity;
import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {

	private LoveAdapter adapter;
	private ArrayList<Photo> mList = new ArrayList<Photo>();
	private List<FileValue> fvList = new ArrayList<FileValue>();
	private List<FileValue> beCheckFvList = new ArrayList<FileValue>();
	private ListView listView;
	private FileValue tempFv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		ActionBar bar = this.getActionBar(); 
		//ActionBar.NAVIGATION_MODE_TABS  ActionBar.NAVIGATION_MODE_LIST  ActionBar.NAVIGATION_MODE_STANDARD
//		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
//		ActionBar.NAVIGATION_MODE_LIST 
//		String[] str = { "1", "2", "3", "4", "5" };
//		bar.setTitle("列表");
//		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
//		bar.setListNavigationCallbacks(new ArrayAdapter<String>(
//		MainActivity.this,
//		android.R.layout.simple_spinner_item, str),new DropDownListenser());
		
//		Tab tab1 = bar.newTab();
//		tab1.setText("添加");
//		tab1.setTabListener(tabListener);
//		Tab tab2 = bar.newTab();
//		tab2.setText("选择");
//		tab2.setTabListener(tabListener);
//		Tab tab3 = bar.newTab();
//		tab3.setText("打开");
//		tab3.setTabListener(tabListener);
//		 
//		bar.addTab(tab1,0); 
//		bar.addTab(tab2,1);
//		bar.addTab(tab3,2);
		 
		Button btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mList.clear();
				Intent intent = new Intent(MainActivity.this,
						ImageDirActivity.class);
				intent.putExtra(AppApplication.ARG_PHOTO_LIST, mList);
				startActivityForResult(intent, 101);
			}
		});
		Button btn2 = (Button) findViewById(R.id.button2);
		btn2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (fvList != null && fvList.size() != 0) {
					int count = fvList.size();
					for (int i = 0; i < count; i++) {
						fvList.get(i).setBeCheck(true);
					}
					adapter.notifyDataSetChanged();
				}
			}
		});

		Button btn4 = (Button) findViewById(R.id.button4);
		btn4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				ArrayList<String> list = new ArrayList<String>();
				for (FileValue fv : fvList) {
					String imageP = Tool.getTemPath(fv.getId());
					imageP = imageP + "." + fv.getName();
					Tool.copyFile(fv.getNewPath(), imageP);
					list.add(imageP);
				}

				if (fvList != null && fvList.size() != 0) {
					int count = fvList.size();
					for (int i = 0; i < count; i++) {
						fvList.get(i).setBeCheck(false);
					}
					beCheckFvList.clear();
					adapter.notifyDataSetChanged();
				}
				if (list != null && list.size() > 0) {
					Intent intent = new Intent(MainActivity.this,
							SearchImagesActivity.class);
					intent.putExtra("imageList", list);
					intent.putExtra("firstItem", 0);
					startActivity(intent);

				}
			}
		});
		Button btn3 = (Button) findViewById(R.id.button3);
		btn3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				ArrayList<String> list = new ArrayList<String>();
				if (beCheckFvList != null && beCheckFvList.size() != 0) {
					for (FileValue fv : beCheckFvList) {

						String imageP = Tool.getTemPath(fv.getId());
						imageP = imageP + "." + fv.getName();
						Tool.copyFile(fv.getNewPath(), imageP);
						list.add(imageP);
					}

				}
				if (fvList != null && fvList.size() != 0) {
					int count = fvList.size();
					for (int i = 0; i < count; i++) {
						fvList.get(i).setBeCheck(false);
					}
					beCheckFvList.clear();
					adapter.notifyDataSetChanged();
				}
				if (list != null && list.size() > 0) {
					Intent intent = new Intent(MainActivity.this,
							SearchImagesActivity.class);
					intent.putExtra("imageList", list);
					intent.putExtra("firstItem", 0);
					startActivity(intent);

				}
			}
		});
		adapter = new LoveAdapter(this, fvList, beCheckFvList);
		listView = (ListView) findViewById(R.id.listView1);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position < fvList.size()) {
					FileValue fv = fvList.get(position);

					ArrayList<String> list = new ArrayList<String>();

					String imageP = Tool.getTemPath(fv.getId());

					imageP = imageP + "." + fv.getName();
					list.add(imageP);
					Tool.copyFile(fv.getNewPath(), imageP);
					Intent intent = new Intent(MainActivity.this,
							SearchImagesActivity.class);
					intent.putExtra("imageList", list);
					intent.putExtra("firstItem", 0);
					startActivity(intent);
				}
			}

		});
		new GetFileFromDB().execute();
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long longId) {
				tempFv = fvList.get(position);

				new AlertDialog.Builder(MainActivity.this)
						.setTitle("确认")
						.setMessage("确认删除记录么")
						.setPositiveButton("删除",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										new DelFileFromDB(tempFv.getId())
												.execute();
									}

								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										tempFv = null;
										dialog.dismiss();
									}

								}).show();
				return false;
			}
		}); 
	}
	/**
     * 实现 ActionBar.OnNavigationListener接口
     */
    class DropDownListenser implements OnNavigationListener
    { 

        /* 当选择下拉菜单项的时候，将Activity中的内容置换为对应的Fragment */
        public boolean onNavigationItemSelected(int itemPosition, long itemId)
        { 
        	System.out.println("itemPosition "+itemPosition);
            return true;
        }
    }
	private TabListener tabListener = new TabListener(){

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			 System.out.println("tab  "+tab.getText());
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			 System.out.println("tab  "+tab.getText());
			
		}

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			 System.out.println("tab  "+tab.getText());
			
		}};
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK && requestCode == 101 && data != null) {
			ArrayList<Photo> list = data
					.getParcelableArrayListExtra(AppApplication.RES_PHOTO_LIST);
			mList.clear();
			if (list != null && list.size() > 0) {
				mList.addAll(list);
				new AddFileToDB().execute();
			}

		}
	}

	private class GetFileFromDB extends AsyncTask<Void, Void, String[]> {

		@Override
		protected String[] doInBackground(Void... params) {

			DataBase db = new DataBase(MainActivity.this);
			fvList.clear();
			fvList.addAll(db.searchAllFromDataBases());
			db.closeDB();
			return null;
		}

		@Override
		protected void onPostExecute(String[] result) {
			super.onPostExecute(result);
			adapter.notifyDataSetChanged();
		}
	}

	private class AddFileToDB extends AsyncTask<Void, Void, String[]> {

		@Override
		protected String[] doInBackground(Void... params) {
			if (mList != null && mList.size() != 0) {
				DataBase db = new DataBase(MainActivity.this);
				int id = db.getLastRawId();
				List<FileValue> list = new ArrayList<FileValue>();
				FileValue fv;
				String oldPath;
				String newPath;
				String name;
				for (Photo pt : mList) {
					fv = new FileValue();

					fv.setId(++id);
					oldPath = pt.imgPath;

					newPath = Tool.getNewPath(fv.getId());
					name = Tool.getFileName(oldPath);
					Tool.copyFile(oldPath, newPath);

					fv.setOldPath(oldPath);
					fv.setNewPath(newPath);
					fv.setName(name);

					list.add(fv);
				}
				db.addAllToDataBases(list);
				db.closeDB();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String[] result) {
			super.onPostExecute(result);
			new GetFileFromDB().execute();
		}
	}

	private class DelFileFromDB extends AsyncTask<Void, Void, String[]> {
		int id;

		public DelFileFromDB(int id) {
			this.id = id;
		}

		@Override
		protected String[] doInBackground(Void... params) {

			DataBase db = new DataBase(MainActivity.this);
			db.deleteFromDataBases(id);
			db.closeDB();
			return null;
		}

		@Override
		protected void onPostExecute(String[] result) {
			super.onPostExecute(result);
			new GetFileFromDB().execute();
			if (tempFv != null) {
				File file = new File(tempFv.getNewPath());
				if (file.exists())
					file.delete();
			}
		}
	}
}
