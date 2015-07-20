package com.xfn.sreit.adapter;

import android.content.Context; 
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

import com.xfn.sreit.R;
import com.xfn.sreit.R.layout;
  

public class ImageFiletersAdapter extends BaseAdapter {
	List<Integer> list;
	Context context;

	public ImageFiletersAdapter(Context context, List<Integer> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		if (convertView == null) {
			imageView = (ImageView) LayoutInflater.from(context).inflate(
					R.layout.i_main, parent, false);
		} else {
			imageView = (ImageView) convertView;
		}
		imageView.setImageResource(list.get(position));
		return imageView;
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return getView(position, null, null).getId();
	}
}
