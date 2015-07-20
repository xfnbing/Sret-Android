package com.xfn.sreit;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import java.util.ArrayList;

import com.xfn.sreit.value.Photo;
 

/**
 * 图片选择Activity的基类。<br/>
 * <br/>
 * Created by yanglw on 2014/8/17.
 */
public class ImageBaseActivity extends ActionBarActivity {
	protected static ArrayList<Photo> checkList = new ArrayList<Photo>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getSupportActionBar() != null) {
			//getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			getSupportActionBar().hide();
		}
	}
}
