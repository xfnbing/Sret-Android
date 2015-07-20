package com.xfn.sreit.adapter;
 
import java.util.List;
 
import android.content.Context;  
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;  
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView; 
 
import com.xfn.sreit.R;
import com.xfn.sreit.value.FileValue;

public class LoveAdapter extends BaseAdapter {
	 
	private Context context;
	private FileValue map; 
	private List<FileValue> maps;
	private List<FileValue> beCheck; 
	private MyAdapterHolder holder = new MyAdapterHolder();
	 
	
	public LoveAdapter(Context context, List<FileValue> list, List<FileValue> check) {
		this.context = context;
		this.maps = list; 
		this.beCheck = check; 
	}

	@Override
	public int getCount() {
		return maps.size();
	}

	@Override
	public Object getItem(int position) {
		return maps.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		map = maps.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.adapter_item_love, null); 
		}  
		holder.cb = (CheckBox) convertView.findViewById(R.id.checkBox1);
		holder.iv = (ImageView) convertView.findViewById(R.id.iv);
		holder.tvName = (TextView) convertView.findViewById(R.id.name);
		holder.tvPath = (TextView) convertView.findViewById(R.id.path);  
		 if(map.isBeCheck()){
			 holder.cb.setChecked(false);
			 holder.cb.setVisibility(View.VISIBLE);
			 holder.cb.setOnCheckedChangeListener(change);
			 holder.cb.setTag(position);
		 }else{
			 holder.cb.setVisibility(View.GONE);
		 }
		holder.tvName.setText(map.getName());
		holder.tvPath.setText(map.getOldPath()+"\n\n"+map.getNewPath()); 
		return convertView;
	}
	Change change = new Change();
	class Change implements OnCheckedChangeListener{

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) { 
			int tag = (Integer) buttonView.getTag();
			System.out.println("tag   "+tag);
			if(isChecked){
				beCheck.add(maps.get(tag));
			}else{
				beCheck.remove(maps.get(tag));
			}
		}
		
	}
	class MyAdapterHolder { 
		TextView tvName;
		TextView tvPath;
		ImageView iv;
		CheckBox cb;
	}
	 
}
