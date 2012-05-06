package cn.xm.hanleylbs.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import cn.xm.hanleylbs.R;
import cn.xm.hanleylbs.utils.ExitUtil;

public class HanleylbsActivity extends Activity {
	private String TAG;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ExitUtil.getInstance().addInstance(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.main);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		ExitUtil.getInstance().addInstance(this);
        try{
            setContentView(R.layout.main);
            final Timer timer = new Timer();
            TimerTask task = new TimerTask() {
    			@Override
    			public void run() {
    		        Intent intent = new Intent();
    				intent.setClass(HanleylbsActivity.this, LoginActivity.class);
    				startActivity(intent);
    				timer.cancel();
    			}
    		};
    		timer.schedule(task, 2000);
        }catch(Exception e){
        	Log.e(TAG, e.toString());
        }
		
	}
}