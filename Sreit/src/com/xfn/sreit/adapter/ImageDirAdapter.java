package com.xfn.sreit.adapter;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.xfn.sreit.InitImageLoader;
import com.xfn.sreit.R;
import com.xfn.sreit.R.id;
import com.xfn.sreit.R.layout;
import com.xfn.sreit.value.Dir;
 

/**
 * Created by yanglw on 2014/8/17.
 */
public class ImageDirAdapter extends MyBaseAdapter<Dir>
{
    private InitImageLoader imageLoader;

	public ImageDirAdapter(Context context, List<Dir> list)
    {
        super(context, list); 
		imageLoader = new InitImageLoader(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Holder holder;
        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.i_imagedir, parent, false);
            holder = new Holder();
            holder.mImageView = (ImageView) convertView.findViewById(R.id.iv);
            holder.mTextView = (TextView) convertView.findViewById(R.id.path);
            holder.mNameTextView = (TextView) convertView.findViewById(R.id.name);

            convertView.setTag(holder);
        }
        else
        {
            holder = (Holder) convertView.getTag();
        }

        Dir dir = mList.get(position);
        holder.mTextView.setText(dir.text);
        holder.mNameTextView.setText(Html.fromHtml(dir.name + " <font color=\"#999999\">(" + dir.length + ")</font>"));
        imageLoader.displayImage("file:///" + dir.imgPath,
                                              holder.mImageView,imageLoader.options2);
        return convertView;
    }

    private class Holder
    {
        public TextView mTextView, mNameTextView;
        public ImageView mImageView;
    }

}
