package com.xfn.sreit;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.ImageColumns;
import android.provider.MediaStore.MediaColumns;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

import com.xfn.sreit.adapter.ImageDirAdapter;
import com.xfn.sreit.value.Dir;
import com.xfn.sreit.value.Photo;
 

/**
 * 查看所有含有图片的目录。<br/>
 * <br/>
 * Created by yanglw on 2014/8/17.
 */
public class ImageDirActivity extends ImageBaseActivity implements
		LoaderManager.LoaderCallbacks<Cursor> {
	private ListView mListView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_image_dir);
		LinearLayout ivBack = (LinearLayout) findViewById(R.id.navigation_back);
		ivBack.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				setResult(Activity.RESULT_CANCELED);
				finish();
			}
		});
		mListView = (ListView) findViewById(R.id.listview);

		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Dir dir = (Dir) parent.getItemAtPosition(position);
				if (dir != null) {
					Intent intent = new Intent(getApplicationContext(),
							ImagesActivity.class);
					intent.putExtra(ImagesActivity.ARG_DIR_ID, dir.id);
					intent.putExtra(ImagesActivity.ARG_DIR_NAME, dir.name);
					intent.putExtra(AppApplication.ARG_PHOTO_LIST, checkList);

					startActivityForResult(intent, 1);
				}
			}
		});

		if (savedInstanceState == null) {
			ArrayList<Photo> list = getIntent().getParcelableArrayListExtra(
					AppApplication.ARG_PHOTO_LIST);
			if (list != null) {
				checkList.addAll(list);
			}
		}
 
		//
		getSupportLoaderManager().initLoader(0, null, this);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {
			Intent intent = new Intent();
			intent.putExtra(AppApplication.RES_PHOTO_LIST, checkList);
			setResult(RESULT_OK, intent);
			finish();
			checkList.clear();
		}
	}

	@Override
	public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
		return new CursorLoader(getApplicationContext(),
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[] {
						"count(1) length", ImageColumns.BUCKET_ID,
						ImageColumns.BUCKET_DISPLAY_NAME,
						MediaColumns.DATA }, "1=1) GROUP BY "
						+ ImageColumns.BUCKET_ID + " -- (", null,
				ImageColumns.BUCKET_DISPLAY_NAME + " ASC,"
						+ MediaColumns.DATE_MODIFIED + " DESC");
	}

	@Override
	public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
		if (cursor.getCount() > 0) {
			ArrayList<Dir> list = new ArrayList<Dir>();

			cursor.moveToPosition(-1);
			while (cursor.moveToNext()) {
				int id = cursor.getInt(cursor
						.getColumnIndex(ImageColumns.BUCKET_ID));
				String path = cursor.getString(cursor
						.getColumnIndex(MediaColumns.DATA));
				String dirPath;
				int index = path.lastIndexOf('/');
				if (index > 0) {
					dirPath = path.substring(0, index);
				} else {
					dirPath = path;
				}

				Dir dir = new Dir();
				dir.id = String.valueOf(id);
				dir.name = cursor
						.getString(cursor
								.getColumnIndex(ImageColumns.BUCKET_DISPLAY_NAME));
				dir.text = dirPath;
				dir.imgPath = path;
				dir.length = cursor.getInt(cursor.getColumnIndex("length"));
				list.add(dir);
			}

			ImageDirAdapter adapter = new ImageDirAdapter(
					getApplicationContext(), list);
			mListView.setAdapter(adapter);
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> cursorLoader) {

	}
}
