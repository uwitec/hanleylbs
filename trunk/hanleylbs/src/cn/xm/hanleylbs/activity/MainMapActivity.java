package cn.xm.hanleylbs.activity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

import cn.xm.hanleylbs.R;
import cn.xm.hanleylbs.utils.ExitUtil;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.GeoPoint;
import com.baidu.mapapi.LocationListener;
import com.baidu.mapapi.MKLocationManager;
import com.baidu.mapapi.MapActivity;
import com.baidu.mapapi.MapController;
import com.baidu.mapapi.MapView;
import com.baidu.mapapi.MyLocationOverlay;

public class MainMapActivity extends MapActivity implements LocationListener {
	private BMapManager mapManager;
	private MKLocationManager mLocationManager = null;
	private MyLocationOverlay myLocationOverlay;

	private MapView mapView;
	private MapController mapController;
	private Button btnNear;
	private String TAG = "MainMapActivity";
	private ImageButton back;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_map);

		ExitUtil.getInstance().addInstance(this);
		mapManager = new BMapManager(getApplication());
		mapManager.init("88751602B4CBC48BE98B91DB255D7EF7BBCF1D74", null);
		super.initMapActivity(mapManager);

		mLocationManager = mapManager.getLocationManager();
		mLocationManager.requestLocationUpdates(this);

		
		 mLocationManager.enableProvider(MKLocationManager.MK_NETWORK_PROVIDER);
		 mLocationManager.enableProvider(MKLocationManager.MK_GPS_PROVIDER);
		 
		 
		mapView = (MapView) findViewById(R.id.map_View);
		mapView.setTraffic(true);
		mapView.setBuiltInZoomControls(true);
		

		GeoPoint point = new GeoPoint((int) (24.529663), (int) (118.183086));

		mapController = mapView.getController();
		mapController.setCenter(point);
		mapController.setZoom(15);
		
		myLocationOverlay = new MyLocationOverlay(this, mapView);
		myLocationOverlay.enableMyLocation();
		myLocationOverlay.enableCompass();
		mapView.getOverlays().add(myLocationOverlay);
		
		findViewByIds();
		setListeners();
	}
	
	
	public void findViewByIds(){
		btnNear = (Button)findViewById(R.id.near_user);
		back = (ImageButton)findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				ExitUtil.getInstance().eixtDialog(MainMapActivity.this);
			}
			
		});
	}
	
	
	public void setListeners(){
		btnNear.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainMapActivity.this,NearUserActivity.class);
				startActivity(intent);
			}
			
		});
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	@Override
	protected void onDestroy() {
		if (mapManager != null) {
			mapManager.destroy();
			mapManager = null;
		}
		mLocationManager = null;
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		if (mapManager != null) {
			mapManager.stop();
		}
		super.onPause();
	}

	@Override
	protected void onResume() {
		if (mapManager != null) {
			mapManager.start();
		}
		super.onResume();
	}

	/**
	 * 根据MyLocationOverlay配置的属性确定是否在地图上显示当前位置
	 */
	@Override
	protected boolean isLocationDisplayed() {
		return myLocationOverlay.isMyLocationEnabled();
	}
	
	/**
	 * 当位置发生变化时触发此方法
	 * 
	 * @param location 当前位置
	 */
	@Override
	public void onLocationChanged(Location location) {
		if (location != null) {
			
			final GeoPoint pt = new GeoPoint((int) (location.getLatitude() * 1000000), (int) (location.getLongitude() * 1000000));
			mapController.setCenter(pt);
		}
	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
				ExitUtil.getInstance().eixtDialog(this);
            return false;
        }
        return false; 
	}
}