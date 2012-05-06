package cn.xm.hanleylbs.activity;

import java.util.ArrayList;

import org.json.JSONObject;

import cn.xm.hanleylbs.R;
import cn.xm.hanleylbs.adapter.ChatAdapter;
import cn.xm.hanleylbs.constants.OperationConstants;
import cn.xm.hanleylbs.domain.ChatEntity;
import cn.xm.hanleylbs.utils.ConnUtil;
import cn.xm.hanleylbs.utils.ExitUtil;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class ChatActivity extends Activity {
    ListView chatlist = null;
    ArrayList<ChatEntity> list = new ArrayList<ChatEntity>();
	private Button btnSend;
	private EditText sendText;
	private ChatAdapter adpater;
	private Bundle bundle;
	private String userId,theUser;
	private String TAG = "ChatActivity";
	private Handler eHandler;
	private long intervalTime = 1000*3;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_chat);
		
		ExitUtil.getInstance().addInstance(this);
		findViewByIds();
		setListeners();
		
		bundle = this.getIntent().getExtras();
		userId = bundle.getString("userId");
    	theUser = bundle.getString("theUser");
    	charUser.setText(theUser);
    	eHandler = new Handler();
    	Thread thread = new Thread(taskReceive);
		thread.start();
	}
    
	private void send(String content){
    	try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("userId", userId);
			jsonObject.put("content", content);
			String requestType = "chatSend";
			ConnUtil.connRemote(jsonObject, requestType);
		} catch (Exception e) {
			Log.e(TAG , e.toString());
		} 
    }
    
    private void receive(){
    	try {
    		JSONObject jsonObject = new JSONObject();
    		jsonObject.put("userId", OperationConstants.user.getUserId());
			String requestType = "chatReceive";
			String responseResult = ConnUtil.connRemote(jsonObject,requestType);
			
			if(null != responseResult && !"".equals(responseResult)){
				ChatEntity receiveMessage=new ChatEntity(theUser,responseResult,R.layout.layout_chat_right);
				list.add(receiveMessage);
				Message msg = receiveHandler.obtainMessage(RECEIVE_UPDATE);
				msg.sendToTarget();
			}
			
			Log.d(TAG, responseResult);
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}
    }
    
    
	private Runnable taskReceive = new Runnable(){


		@Override
		public void run() {
			receive();
			eHandler.postDelayed(taskReceive, intervalTime);
			Log.d(TAG, "Thread-->taskMonitor");
		}
		
	};
	private TextView charUser;
	private ImageButton back;
    
    
    private void findViewByIds(){
    	btnSend = (Button)findViewById(R.id.btn_send);
    	sendText = (EditText)findViewById(R.id.send_text);
    	chatlist = (ListView) findViewById(R.id.chatlist);
    	chatlist.setCacheColorHint(Color.parseColor("#00000000"));
    	charUser = (TextView)findViewById(R.id.chat_user);
    	back = (ImageButton)findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				eHandler.removeCallbacks(taskReceive);
				finish();
			}
			
		});
    }
    
    private void setListeners(){
    	
    	btnSend.setOnClickListener(clickListener);
    }
    
    private OnClickListener clickListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.btn_send:
				String sendContent = sendText.getText().toString();
				if(null != sendContent && !"".equals(sendContent)){
					ChatEntity content=new ChatEntity(OperationConstants.user.getName(),sendContent,R.layout.layout_chat_left);
					list.add(content);
					updateUI();
					sendText.setText("");
					send(sendContent);
				}
				break;
			}
			
		}
    	
    };
	protected final int RECEIVE_UPDATE = 1;
    
    
    private Handler receiveHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch(msg.what){
			case RECEIVE_UPDATE :
				updateUI();
				break;
			}
		}
    	
    };
    
    
    public synchronized void updateUI(){
    	
    	if(null == adpater){
    		adpater = new ChatAdapter(ChatActivity.this,list);
    		chatlist.setAdapter(adpater);
    	}
    	
    	
    	adpater.notifyDataSetChanged();
        
        chatlist.setSelection(chatlist.getAdapter().getCount()-1); 
    }
    
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			eHandler.removeCallbacks(taskReceive);
			this.finish();
            return false;
        }
        return false; 
	}
}