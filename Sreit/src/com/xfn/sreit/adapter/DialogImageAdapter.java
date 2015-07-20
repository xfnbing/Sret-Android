package com.xfn.sreit.adapter;

import java.util.ArrayList;
import java.util.List;

import com.xfn.sreit.InitImageLoader;
import com.xfn.sreit.view.ScaleImageView;
  
 
import android.app.Activity; 
import android.support.v4.view.PagerAdapter; 
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;  
import android.widget.ImageView.ScaleType;

public class DialogImageAdapter extends PagerAdapter {
	private List<String> list = new ArrayList<String>(); 
	private InitImageLoader imageLoader = null;
	private Activity context ;
	private ClickEvent listener = new ClickEvent(); 
	public DialogImageAdapter(Activity a, List<String> list) {
		this.list = list; 
		this.context = a;
	}

	@Override
	public int getCount() {
		return list == null ? 0 : list.size();
	}
	
	@Override
	public View instantiateItem(ViewGroup container, int position) {
		ScaleImageView photoView = new ScaleImageView(container.getContext());
		if(imageLoader == null)
			imageLoader = new InitImageLoader(container.getContext());
		
		// Now just add PhotoView to ViewPager and return it
		photoView.setScaleType(ScaleType.MATRIX);
		container.addView(photoView, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		//photoView.setOnClickListener(listener);
		imageLoader.displayImage("file:///"+list.get(position).toString(),
				photoView,imageLoader.options2);

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
	class ClickEvent implements View.OnClickListener {
 
		@Override
		public void onClick(View v) {  
			context.finish();
		} 
	} 
}
