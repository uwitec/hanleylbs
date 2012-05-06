package cn.xm.hanleylbs.utils;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import cn.xm.hanleylbs.R;

public class ExitUtil {

	public  List<Activity> activityList = new ArrayList<Activity>();
	private static ExitUtil instance;
	private boolean flag;

	private ExitUtil() {
	}

	public static ExitUtil getInstance() {
		if (instance == null) {
			synchronized (ExitUtil.class) {
				if (instance == null) {
					instance = new ExitUtil();
				}
			}
		}
		return instance;
	}
	
	public  void ExitApp(){
		for(int i=0; i<activityList.size(); i++){
			activityList.get(i).finish();
		}
	}
	
	public void addInstance(Activity activity){
		activityList.add(activity);
	}
	
	
    public void eixtDialog(Context context){
    	showDialog(context);
    }
    
    /**
     * 退出整个应用,带提示功能
     * @param context
     * @param m_NotificationManager
     * @return
     */
    public  boolean showDialog(Context context) {   
        AlertDialog.Builder builder = new AlertDialog.Builder(context);   
        
        builder.setTitle(context.getResources().getString(R.string.exit_msg));   
        builder.setPositiveButton(context.getResources().getString(R.string.ok),   
                new DialogInterface.OnClickListener() {   
                    public void onClick(DialogInterface dialog, int whichButton) {
                    	flag = true;
                    	ExitApp();
                    }   
                });   
        builder.setNeutralButton(context.getResources().getString(R.string.cancle),   
                new DialogInterface.OnClickListener() {   
                    public void onClick(DialogInterface dialog, int whichButton) {   
                    	flag = false;
                    }   
                }); 
        builder.show();   
        return flag;
    }  
}
