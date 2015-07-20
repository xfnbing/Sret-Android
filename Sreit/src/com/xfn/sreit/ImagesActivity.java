package com.xfn.sreit;
 
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.ImageColumns;
import android.provider.MediaStore.MediaColumns;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.AdapterView; 
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import com.xfn.sreit.adapter.ImagesAdapter;
import com.xfn.sreit.value.Photo;
 
 

/**
 * 浏览某一个目录下的所有图片。<br/>
 * <br/>
 * Created by yanglw on 2014/8/17.
 */
public class ImagesActivity extends ImageBaseActivity implements
		LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener {
	public static final String ARG_DIR_ID = "com.sportstar.chooseimages.DIR_ID";
	public static final String ARG_DIR_NAME = "com.sportstar.chooseimages.DIR_NAME";
	private GridView mGridView;
	//private Button preview, num;
	private ImagesAdapter mAdapter;

	private String mDirId;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_images); 
		LinearLayout ivBack = (LinearLayout) findViewById(R.id.navigation_back);
		ivBack.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				setResult(Activity.RESULT_CANCELED);
				finish();
			}
		});
		((Button) findViewById(R.id.preview)).setOnClickListener(this);
		((Button) findViewById(R.id.sure)).setOnClickListener(this);
		mGridView = (GridView) findViewById(R.id.gridview);
		mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Photo photo = (Photo) parent.getItemAtPosition(position);
				if (photo == null) {
					return;
				}

				if (!checkList.contains(photo)) {
					if (checkList.size() >= AppApplication.MAX_SIZE) {
						Toast.makeText(
								getApplicationContext(),
								getString(R.string.tip_max_size,
										AppApplication.MAX_SIZE),
								Toast.LENGTH_SHORT).show();
						return;
					}
				}
				mAdapter.setCheck(position, view);

				setBtnEnable(!checkList.isEmpty());
			}
		}); 

		Intent intent = getIntent();
		mDirId = intent.getStringExtra(ARG_DIR_ID);
		setTitle(intent.getStringExtra(ARG_DIR_NAME));
		// TODO
		getSupportLoaderManager().initLoader(0, null, this);

		setBtnEnable(!checkList.isEmpty());
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {
			setResult(RESULT_OK);
			finish();
			return;
		}
		if (resultCode == AppApplication.RESULT_CHANGE) {
			mAdapter.notifyDataSetChanged();
			setBtnEnable(!checkList.isEmpty());
		}
	}

	@Override
	public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
		return new CursorLoader(
				getApplicationContext(),
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				new String[] { MediaColumns.DATA // 图片地址
				}, mDirId == null ? null : ImageColumns.BUCKET_ID
						+ "=" + mDirId, null,
				MediaColumns.DATE_MODIFIED + " DESC");
	}

	@Override
	public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
		if (cursor.getCount() > 0) {
			ArrayList<Photo> list = new ArrayList<Photo>();

			cursor.moveToPosition(-1);
			while (cursor.moveToNext()) {
				Photo photo = new Photo();

				photo.imgPath = cursor.getString(cursor
						.getColumnIndex(MediaColumns.DATA));
				list.add(photo);
			}

			mAdapter = new ImagesAdapter(getApplicationContext(), list,
					checkList);
			mGridView.setAdapter(mAdapter);
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> cursorLoader) {

	}

	private void setBtnEnable(boolean enable) {
		if (Build.VERSION.SDK_INT >= 11) {
			invalidateOptionsMenu();
		}
	}

	@Override
	public void onClick(View v) {  
		int id = v.getId();
		if (id == R.id.preview) {
			startActivityForResult(new Intent(getApplicationContext(),
					PreviewActivity.class), 1);
			return;
		}
		if (id == R.id.sure) {
			setResult(Activity.RESULT_OK);
			this.finish();
			return;
		}
	} 
}
