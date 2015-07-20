package com.xfn.sreit;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import com.xfn.sreit.value.Photo;
 

/**
 * 图片预览。
 */
public class PreviewActivity extends ImageBaseActivity {
	private ViewPager mViewPager;
	private ArrayList<Photo> mCancelList;

	private boolean mIsCheck;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_pre_view);
		LinearLayout ivBack = (LinearLayout) findViewById(R.id.navigation_back);
		ivBack.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				setResult(Activity.RESULT_CANCELED);
				finish();
			}
		});
		Button btn = (Button) findViewById(R.id.sure);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				setResult(RESULT_OK);
				finish();
			}
		});
		mViewPager = (ViewPager) findViewById(R.id.viewpager);

		SamplePagerAdapter mAdapter = new SamplePagerAdapter(checkList);
		mViewPager.setAdapter(mAdapter);
		mViewPager
				.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
					@Override
					public void onPageScrolled(int position,
							float positionOffset, int positionOffsetPixels) {

					}

					@Override
					public void onPageSelected(int position) {
						refreshMenuItem();
					}

					@Override
					public void onPageScrollStateChanged(int state) {

					}
				});

		mCancelList = new ArrayList<Photo>();

		refreshMenuItem();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.preview, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.add) {
			Photo photo = checkList.get(mViewPager.getCurrentItem());
			if (mIsCheck) {
				mCancelList.add(photo);
			} else {
				mCancelList.remove(photo);
			}

			mIsCheck = !mIsCheck;

			if (Build.VERSION.SDK_INT >= 11) {
				invalidateOptionsMenu();
			}

			setResult(AppApplication.RESULT_CHANGE);
			return true;
		}
		if (id == R.id.num) {
			setResult(RESULT_OK);
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	 

	@Override
	public void finish() {
		if (!mCancelList.isEmpty()) {
			for (Photo photo : mCancelList) {
				checkList.remove(photo);
			}
		}

		super.finish();
	}

	/** 刷新ActionBar图标 */
	private void refreshMenuItem() {
		Photo photo = checkList.get(mViewPager.getCurrentItem());
		mIsCheck = !mCancelList.contains(photo);
		// if (Build.VERSION.SDK_INT >= 11)
		// {
		// invalidateOptionsMenu();
		// }
	}

	static class SamplePagerAdapter extends PagerAdapter {
		private List<Photo> mList;
		private InitImageLoader imageLoader = null;

		public SamplePagerAdapter(List<Photo> list) {
			this.mList = list;
		}

		@Override
		public int getCount() {
			return mList == null ? 0 : mList.size();
		}
		
		@Override
		public View instantiateItem(ViewGroup container, int position) {
			ImageView photoView = new ImageView(container.getContext());
			if(imageLoader == null)
				imageLoader = new InitImageLoader(container.getContext());
			imageLoader.displayImage(
					"file:///" + mList.get(position).imgPath, photoView,imageLoader.options2);

			// Now just add PhotoView to ViewPager and return it
			container.addView(photoView, LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);

			return photoView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}
	}
}
