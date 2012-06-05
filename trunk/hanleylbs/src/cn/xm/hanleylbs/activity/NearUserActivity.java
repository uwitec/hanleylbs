package cn.xm.hanleylbs.activity;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import cn.xm.hanleylbs.R;
import cn.xm.hanleylbs.constants.OperationConstants;
import cn.xm.hanleylbs.domain.TaskInfo;
import cn.xm.hanleylbs.utils.ConnUtil;
import cn.xm.hanleylbs.utils.ExitUtil;

public class NearUserActivity extends Activity {

	private ListView listViewUser;
	private ImageButton back;
	private SimpleAdapter simapleAdapter;
	private List<Map<String, String>> data = new ArrayList<Map<String, String>>();
	private String requestType = "queryAllUser";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.layout_nearuser);
		ExitUtil.getInstance().addInstance(this);
		findViewsByids();
		setListeners();
		init();
	}
	
	public void init(){
		NearUserTask nut = new NearUserTask();
		TaskInfo ti = new TaskInfo();
		ti.setOpCode(requestType);
		nut.execute(ti);
	}
	
	
	public void setListeners(){
		listViewUser.setOnItemClickListener(itemClickListener);
	}

	public void findViewsByids() {
		listViewUser = (ListView) this.findViewById(R.id.list_view);
		listViewUser.setCacheColorHint(Color.parseColor("#00000000"));
		listViewUser.setDivider(new ColorDrawable(Color.parseColor("#A9A9A9")));
		listViewUser.setDividerHeight(2);
		
		
		back = (ImageButton)findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				finish();
			}
			
		});
		
	}
	
	
	public void updateUI() {
		if (null == simapleAdapter) {
			simapleAdapter = new SimpleAdapter(this, data, R.layout.item_nearuser,
					new String[] 
							{ "the_user", 
							  "the_gender",
							  "the_age", 
							  "the_tel", 
							  "the_address" }, 
						new int[] {
							R.id.the_user,
							R.id.the_gender,
							R.id.the_age, 
							R.id.the_tel,
							R.id.the_address });
			
			listViewUser.setAdapter(simapleAdapter);
		}
		simapleAdapter.notifyDataSetChanged();
	}
	
	private OnItemClickListener itemClickListener = new OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> parent, View v, int position,
				long id) {
			Map<String, String> m = data.get(position);
			String userId = m.get("userid");
			String theUser = m.get("the_user");
			
			Intent intent = new Intent(NearUserActivity.this,ChatActivity.class);
			Bundle b = new Bundle();
			b.putString("userId", userId);
			b.putString("theUser", theUser);
			intent.putExtras(b);
			startActivity(intent);
		}
		
	};
	
	
	
	
	private void analyzeResponse(String result) throws Exception{
		
		JSONObject resObj = new JSONObject(result);
		JSONArray jsonArray = resObj.getJSONArray("userList");
		int length = jsonArray.length();
		
		HashMap<String,String> map = null;
		
		for(int i=0; i<length; i++){
			
			map = new HashMap<String,String>();
			
			String everyUser = jsonArray.getString(i);
			JSONObject resJsonObj = new JSONObject(everyUser);
			
			if(resJsonObj.getInt("userId")==OperationConstants.user.getUserId()){
				continue;
			}
			
			map.put("userid", resJsonObj.getString("userId"));
			map.put("the_age", "年龄:"+resJsonObj.getString("age"));
			
			if(resJsonObj.getInt("gender") == 0){
				map.put("the_gender", "性别:女");
			}else if(resJsonObj.getInt("gender") == 1){
				map.put("the_gender", "性别:男");
			}else{
				map.put("the_gender", "人妖  啊");
			}
			
			map.put("online", resJsonObj.getString("online"));
			map.put("the_tel", "电话:"+resJsonObj.getString("tel"));
			map.put("the_user", ""+resJsonObj.getString("name"));
			map.put("the_address", "当前地址:"+resJsonObj.getString("address"));
			map.put("longitude", resJsonObj.getString("longitude"));
			map.put("latitude", resJsonObj.getString("latitude"));
			map.put("memo", resJsonObj.getString("memo"));
			
			data.add(map);
			
		}
		
}
	
	
	
	
	public ProgressDialog dialog;
	public String TAG = "NearUserActivity";
	
	
	private class NearUserTask extends AsyncTask<TaskInfo,Void,String>{


		@Override
		protected String doInBackground(TaskInfo... params) {
			String responseResult = "";
			int length = params.length;
			for(int i=0; i<length; i++){
				try {
					responseResult = ConnUtil.connRemote(params[i].getOpCode());
				} catch (ConnectTimeoutException e) {
					Log.e(TAG,e.toString());
				} catch (ClientProtocolException e) {
					Log.e(TAG,e.toString());
				}catch(SocketTimeoutException e){
					Log.e(TAG,e.toString());
				}catch(IOException e){
					Log.e(TAG,e.toString());
				}
			}
			
			return responseResult;
		}
		
		
		
		@Override
		protected void onPostExecute(String result) {
			try {
				analyzeResponse(result);
				updateUI();
			} catch (Exception e) {
				Log.e(TAG, e.toString());
			}finally{
				if (null != dialog) {
					dialog.dismiss();
				}
			}
		}



		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(NearUserActivity.this, "", "为你查找附近的人.");
		}
		
		
	}
	
	
}
