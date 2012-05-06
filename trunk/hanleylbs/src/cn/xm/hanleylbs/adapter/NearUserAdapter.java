package cn.xm.hanleylbs.adapter;

import java.util.ArrayList;

import cn.xm.hanleylbs.R;
import cn.xm.hanleylbs.domain.NearUserEntity;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NearUserAdapter extends BaseAdapter{
	private ArrayList<NearUserEntity> list;
	private Context ctx;

	public NearUserAdapter(Context context ,ArrayList<NearUserEntity> list) {
		ctx = context;
		this.list = list;
	}
	
	public boolean areAllItemsEnabled() {
		return false;
	}
	public boolean isEnabled(int arg0) {
		return false;
	}
	public int getCount() {
		return list.size();
	}
	public Object getItem(int position) {
		return list.get(position);
	}
	public long getItemId(int position) {
		return position;
	}
	public int getItemViewType(int position) {
		return position;
	}
	public View getView(int position, View convertView, ViewGroup parent) {
		NearUserEntity entity = list.get(position);
		int itemLayout = entity.getLayoutID();
		
		LinearLayout layout = new LinearLayout(ctx);
		LayoutInflater vi = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		vi.inflate(itemLayout, layout,true);
		
		TextView txvName = (TextView) layout.findViewById(R.id.the_user);
		txvName.setText(entity.getName());
		
		TextView txvAge = (TextView) layout.findViewById(R.id.the_age);
		txvAge.setText(entity.getAge());
		
		TextView txvGender = (TextView) layout.findViewById(R.id.the_gender);
		txvGender.setText(entity.getGender());
		
		TextView txvTel = (TextView) layout.findViewById(R.id.the_tel);
		txvTel.setText(entity.getTel());
		
		TextView txvAddress = (TextView) layout.findViewById(R.id.the_address);
		txvAddress.setText(entity.getAddress());
		
		return layout;
	}
	public int getViewTypeCount() {
		return list.size();
	}
	public boolean hasStableIds() {
		return false;
	}
	public boolean isEmpty() {
		return false;
	}
	public void registerDataSetObserver(DataSetObserver observer) {
	}
	public void unregisterDataSetObserver(DataSetObserver observer) {
	}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}
	
	

}
