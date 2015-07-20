package com.xfn.sreit;
 
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;
 

public class AppApplication extends Application {

	private static AppApplication mInstance = null; 

	/** 表示通过Intent传递到下一个Activity的图片列表 */
	public static final String ARG_PHOTO_LIST = "com.sportstar.chooseimages.PHOTO_LIST";
	/** 表示通过Intent传递到上一个Activity的图片列表 */
	public static final String RES_PHOTO_LIST = "com.sportstar.chooseimages.PHOTO_LIST";

	/** 表示选择的图片发生了变化 */
	public static final int RESULT_CHANGE = 10010;

	/** 最多能够选择的图片个数 */
	public static int MAX_SIZE = 9;
	public static ImageLoader imageLoader = null;

	// public static float density = 0f;
	// private String token =
	// "7hdp30kdzrcFE24ONk3U7rKhavjxtmp7zEqG4OGTnT4ne5q3EkKLFTDfePEvCKl2ldHtaqeY6yWBYiuq7+MG4Q==";

	@Override
	public void onCreate() {
		super.onCreate(); 
		mInstance = this;
		initImageLoader(this); 
 

	}
 
	public static AppApplication getInstance() {

		return mInstance;
	}

	public static void initImageLoader(Context context) {

		int maxMemory = (int) Runtime.getRuntime().maxMemory();
		int cacheSize = maxMemory / 4;
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).memoryCacheExtraOptions(320, 400).threadPoolSize(3)
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.diskCacheSize(cacheSize) // 设置图片缓存大小为程序最大可用内存的1/4
				.tasksProcessingOrder(QueueProcessingType.LIFO).build();
		// .writeDebugLogs()

		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
		imageLoader = ImageLoader.getInstance(); 
	}

	@Override
	public void onTerminate() { 
		super.onTerminate();
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		imageLoader.clearMemoryCache();
		imageLoader.clearDiskCache(); 
		System.gc();
	} 
}