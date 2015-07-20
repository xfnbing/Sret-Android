package com.xfn.sreit;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
 
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;


public class InitImageLoader {

	private DisplayImageOptions options;
	public DisplayImageOptions options2;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

	public InitImageLoader(Context context,int Rounded) {
		init(Rounded);
	}
	
	public InitImageLoader(Context context){
		init(0);
	}
	private void init(int Rounded){
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.ic_launcher)
		.showImageForEmptyUri(R.drawable.ic_launcher)
		.showImageOnFail(R.drawable.ic_launcher)
		.cacheOnDisk(true)
		.considerExifParams(true)
		.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
		.bitmapConfig(Bitmap.Config.RGB_565) 
		.displayer(new RoundedBitmapDisplayer(Rounded))
		.build();  

		options2 = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.ic_launcher)
		.showImageForEmptyUri(R.drawable.ic_launcher)
		.showImageOnFail(R.drawable.ic_launcher)
		.cacheOnDisk(true)
		.considerExifParams(true)
		.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
		.bitmapConfig(Bitmap.Config.RGB_565) 
		.build();
	}
	public void displayImage(String path,ImageView v){
		AppApplication.imageLoader.displayImage( path,  v, options, animateFirstListener);
		AppApplication.imageLoader.clearMemoryCache();
	}

	private static class AnimateFirstDisplayListener implements ImageLoadingListener {

		static final List<String> displayedImages = Collections
				.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}

		@Override
		public void onLoadingCancelled(String arg0, View arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
			// TODO Auto-generated method stub
			return;
		}

		@Override
		public void onLoadingStarted(String arg0, View arg1) {
			// TODO Auto-generated method stub
			
		}
	}
	public void displayImage(String path,
			ImageView v, DisplayImageOptions options2) {
		AppApplication.imageLoader.displayImage(path, v, options2, animateFirstListener);
		
	} 
}
