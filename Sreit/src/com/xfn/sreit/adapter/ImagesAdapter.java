package com.xfn.sreit.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import com.xfn.sreit.InitImageLoader;
import com.xfn.sreit.R;
import com.xfn.sreit.R.id;
import com.xfn.sreit.R.layout;
import com.xfn.sreit.value.Photo;
 
 

/**
 * <br/>
 * <br/>
 * Created by yanglw on 2014/8/17.
 */
public class ImagesAdapter extends MyBaseAdapter<Photo>
{
    private List<Photo> mCheckList;
	private InitImageLoader imageLoader;

    public ImagesAdapter(Context context, List<Photo> list, List<Photo> checkList)
    {
        super(context, list);
        mCheckList = checkList;
		imageLoader = new InitImageLoader(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Holder holder;
        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.i_images, parent, false);
            holder = new Holder();
            holder.mImageView = (ImageView) convertView.findViewById(R.id.iv);
            holder.mCheckImgaeView = (ImageView) convertView.findViewById(R.id.check);

            convertView.setTag(holder);
        }
        else
        {
            holder = (Holder) convertView.getTag();
        }

        Photo photo = mList.get(position);

        if (mCheckList != null && mCheckList.contains(photo))
        {
            holder.mCheckImgaeView.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.mCheckImgaeView.setVisibility(View.INVISIBLE);
        }
        imageLoader.displayImage("file:///" + photo.imgPath,
                                              holder.mImageView,imageLoader.options2);
        return convertView;
    }

    public void setCheck(int postion, View view)
    {
        Photo photo = mList.get(postion);

        boolean checked = mCheckList.contains(photo);

        Holder holder = (Holder) view.getTag();

        if (checked)
        {
            mCheckList.remove(photo);
            holder.mCheckImgaeView.setVisibility(View.INVISIBLE);
        }
        else
        {
            mCheckList.add(photo);
            holder.mCheckImgaeView.setVisibility(View.VISIBLE);
        }
    }


    private class Holder
    {
        public ImageView mImageView, mCheckImgaeView;
    }

}
