package cn.xm.hanleylbs.activity;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import cn.xm.hanleylbs.R;
import cn.xm.hanleylbs.constants.OperationConstants;
import cn.xm.hanleylbs.domain.TaskInfo;
import cn.xm.hanleylbs.domain.User;
import cn.xm.hanleylbs.utils.ConnUtil;
import cn.xm.hanleylbs.utils.ExitUtil;
import cn.xm.hanleylbs.activity.MainMapActivity;

public class LoginActivity extends Activity {
	
	private EditText username,pwd;
	private Button btnLogin;
	private String TAG = "LoginActivity";
	private String requestType="login";
	private ProgressDialog dialog;
	private ImageButton back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		ExitUtil.getInstance().addInstance(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_login);
		ExitUtil.getInstance().addInstance(this);
		findViewsByIds();
		setListener();
	}

	private void findViewsByIds(){
		username = (EditText) findViewById(R.id.username);
		pwd = (EditText) findViewById(R.id.password);
		btnLogin = (Button) findViewById(R.id.btn_login);
		back = (ImageButton)findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				ExitUtil.getInstance().eixtDialog(LoginActivity.this);
			}
			
		});
	}
	
	private void setListener(){
		btnLogin.setOnClickListener(clickListener);
	}
	
	
	private void validateLogin(String userName, String password) {
		
		try{
			
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("username", userName);
			jsonObject.put("password", password);
			LoginTask task = new LoginTask();
			TaskInfo lti = new TaskInfo();
			lti.setJsonObj(jsonObject);
			lti.setOpCode(requestType);
			task.execute(lti);
			
		}catch (Exception e) {
			Log.e(TAG  , e.toString());
		}
		
	}
	
	
	private void analyzeResponse(String result) throws Exception{
		
			JSONObject resObj = new JSONObject(result);
			String content = resObj.getString("user");
			JSONObject resJsonObj = new JSONObject(content);
			User user = new User();
			user.setUserId(resJsonObj.getInt("userId"));
			user.setAge(resJsonObj.getInt("age"));
			user.setGender(resJsonObj.getInt("gender"));
			user.setOnline(resJsonObj.getInt("online"));
			user.setTel(resJsonObj.getString("tel"));
			user.setName(resJsonObj.getString("name"));
			user.setAddress(resJsonObj.getString("address"));
			user.setLongitude(resJsonObj.getString("longitude"));
			user.setLatitude(resJsonObj.getString("latitude"));
			user.setMemo(resJsonObj.getString("memo"));
			OperationConstants.user = user;
			
			Log.d(TAG, user.toString());
			
	}
	
	
	private OnClickListener clickListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			switch(v.getId()){
			
			case R.id.btn_login:
				if("".equals(username.getText().toString()) || "".equals(pwd.getText().toString())){
					Toast.makeText(LoginActivity.this, "请完整录入信息!", Toast.LENGTH_SHORT).show();
					return;
				}else{
					validateLogin(username.getText().toString(),pwd.getText().toString());
//					Intent intent = new Intent(LoginActivity.this,MainMapActivity.class);
//					LoginActivity.this.startActivity(intent);
					//FIXME
				}
				break;
			}
		}
		
	};
	
	private class LoginTask extends AsyncTask<TaskInfo,Void,String>{

		private boolean flag = false;

		@Override
		protected String doInBackground(TaskInfo... params) {
			String responseResult = "";
			int length = params.length;
			for(int i=0; i<length; i++){
				try {
					responseResult = ConnUtil.connRemote(params[i].getJsonObj(), params[i].getOpCode());
				} catch (ConnectTimeoutException e) {
					Message msg = tipHandler.obtainMessage(TIP_TIMEOUT);
					msg.sendToTarget();
					Log.e(TAG,e.toString());
					flag = true;
				} catch (ClientProtocolException e) {
					Log.e(TAG,e.toString());
				}catch(SocketTimeoutException e){
					Message msg = tipHandler.obtainMessage(TIP_TIMEOUT);
					msg.sendToTarget();
					Log.e(TAG,e.toString());
					flag = true;
				}catch(IOException e){
					Log.e(TAG,e.toString());
				}
			}
			
			try {
				responseResult = new String(responseResult.getBytes("ISO8859-1"),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				Log.e(TAG, e.toString());
			}
			return responseResult;
		}
		
		
		
		@Override
		protected void onPostExecute(String result) {
			try {
				analyzeResponse(result);
				Intent intent = new Intent(LoginActivity.this,MainMapActivity.class);
				LoginActivity.this.startActivity(intent);
			} catch (Exception e) {
				
				if(!flag){
					Message msg = tipHandler.obtainMessage(TIP_LOGIN_FAILED);
					msg.sendToTarget();
					Log.e(TAG, e.toString());
				}
			}finally{
				if (null != dialog) {
					dialog.dismiss();
				}
			}
		}



		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(LoginActivity.this, "", "我很努力的在登录.");
		}
		
		
	}
	
	
	private final int TIP_TIMEOUT = 1;
	private final int TIP_LOGIN_FAILED = 2;
	
	private Handler tipHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch(msg.what){
			case TIP_TIMEOUT:
				Toast.makeText(LoginActivity.this, "连接超时", Toast.LENGTH_SHORT).show();
				break;
			case TIP_LOGIN_FAILED:
				Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
				break;
			}
			super.handleMessage(msg);
		}
		
	};
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
				ExitUtil.getInstance().eixtDialog(this);
            return false;
        }
        return false; 
	}
	
}
