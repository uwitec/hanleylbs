package cn.xm.hanleylbs.adapter;

import java.util.ArrayList;

import cn.xm.hanleylbs.R;
import cn.xm.hanleylbs.domain.ChatEntity;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChatAdapter extends BaseAdapter{
	private ArrayList<ChatEntity> list;
	private Context ctx;

	public ChatAdapter(Context context ,ArrayList<ChatEntity> list) {
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
		ChatEntity entity = list.get(position);
		int itemLayout = entity.getLayoutID();
		
		LinearLayout layout = new LinearLayout(ctx);
		LayoutInflater vi = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		vi.inflate(itemLayout, layout,true);
		
		TextView txvName = (TextView) layout.findViewById(R.id.txvName);
		txvName.setText(entity.getName());
		
		TextView txvText = (TextView) layout.findViewById(R.id.txvInfo);
		txvText.setText(entity.getInfo());
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
