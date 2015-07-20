package com.xfn.sreit;

import java.io.File;
import java.util.List;

import com.xfn.sreit.adapter.DialogImageAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * 图片浏览页
 */
public class SearchImagesActivity extends Activity {

	TextView tvCurrentItem;
	int touchcount = 0;
	long lastTouchTime = 0;
	List<String> list;
	int firstItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置全屏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.dialog_pre_view);
		list = this.getIntent().getStringArrayListExtra("imageList");
		firstItem = this.getIntent().getIntExtra("firstItem", 0);
		initView();
	}

	/**
	 * 创建对话框
	 */
	public void initView() {
		// 设置显示在中间
		ViewPager mViewPager = (ViewPager) findViewById(R.id.viewpager);
		tvCurrentItem = (TextView) findViewById(R.id.textView1);
		DialogImageAdapter adapter = new DialogImageAdapter(this, list);
		mViewPager.setAdapter(adapter);
		mViewPager.setCurrentItem(firstItem);
		tvCurrentItem.setText((firstItem + 1) + "/" + list.size());
		mViewPager
				.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
					@Override
					public void onPageScrolled(int position,
							float positionOffset, int positionOffsetPixels) {
						tvCurrentItem.setText((position + 1) + "/"
								+ list.size());
					}

					@Override
					public void onPageSelected(int position) {
					}

					@Override
					public void onPageScrollStateChanged(int state) {

					}
				});
	}

	@Override
	public void finish() {
		super.finish();
		if (list != null) {
			File file;
			for (String str : list) {
				file = new File(str);
				if (file.exists())
					file.delete();
			}
		}
	}

}
