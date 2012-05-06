package cn.xm.hanleylbs.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import cn.xm.hanleylbs.R;

public class TipsUtil {
	
	
	private static TipsUtil instance;
	
	private TipsUtil(){
		
	}
	
	public static TipsUtil getInstance() {
		if(instance==null){
			synchronized (TipsUtil.class) {
				if(instance == null){
					instance = new TipsUtil();
				}
			}
		}
		return instance;
	}
	

	/**
	 * 自定义Toast
	 * 无网络提示
	 * @param context
	 */
	public void netWaringToast(Context context){
		Toast toast = Toast.makeText(context, context.getResources().getString(R.string.no_net),
				Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		LinearLayout lay = (LinearLayout) toast.getView();
		lay.getBackground().setAlpha(150);
		ImageView img = new ImageView(context);
		img.setImageResource(R.drawable.warning);
		lay.addView(img, 0);
		toast.show();
	}
	
}
