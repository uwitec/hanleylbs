package cn.xm.hanleylbs.utils;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import cn.xm.hanleylbs.constants.LBSConstants;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class ConnUtil {

	private static final String TAG = "ConnUtil";

	public static String connRemote(JSONObject jsonObject, String requestType)
			throws ClientProtocolException, IOException,
			ConnectTimeoutException, SocketTimeoutException {

		String responseValue = "";
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams,LBSConstants.CONNECTION_TIMEOUT);
		HttpConnectionParams.setSoTimeout(httpParams, LBSConstants.SO_TIMEOUT);
		HttpClient httpClient = new DefaultHttpClient(httpParams);

		HttpPost httpPost = new HttpPost(LBSConstants.URL + requestType);
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
		nameValuePair.add(new BasicNameValuePair(LBSConstants.PARAM, jsonObject.toString()));
		httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair,HTTP.UTF_8));
		httpPost.setParams(httpParams);
		HttpResponse response = httpClient.execute(httpPost);
		responseValue = EntityUtils.toString(response.getEntity(),"UTF-8");
		
		return responseValue;

	}
	
	
	
	public static String connRemote(String requestType)
			throws ClientProtocolException, IOException,
			ConnectTimeoutException, SocketTimeoutException {

		String responseValue = "";
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams,LBSConstants.CONNECTION_TIMEOUT);
		HttpConnectionParams.setSoTimeout(httpParams, LBSConstants.SO_TIMEOUT);
		HttpClient httpClient = new DefaultHttpClient(httpParams);

		HttpPost httpPost = new HttpPost(LBSConstants.URL + requestType);
		
		httpPost.setEntity(new StringEntity("",HTTP.UTF_8));
		httpPost.setParams(httpParams);
		HttpResponse response = httpClient.execute(httpPost);
		responseValue = EntityUtils.toString(response.getEntity(),"UTF-8");
		return responseValue;

	}
	
	

	public static String connRemote(JSONObject jsonObject, String OPCode,
			List<NameValuePair> nameValuePair) {
		String retSrc = "";
		try {
			HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams,
					LBSConstants.CONNECTION_TIMEOUT);
			HttpConnectionParams.setSoTimeout(httpParams,
					LBSConstants.SO_TIMEOUT);
			HttpClient httpClient = new DefaultHttpClient(httpParams);

			HttpPost httpPost = new HttpPost(LBSConstants.URL);
			nameValuePair.add(new BasicNameValuePair(LBSConstants.ACTION_TYPE,
					OPCode));
			nameValuePair.add(new BasicNameValuePair(LBSConstants.PARAM,
					jsonObject.toString()));
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
			httpPost.setParams(httpParams);
			HttpResponse response = httpClient.execute(httpPost);
			retSrc = EntityUtils.toString(response.getEntity(),"UTF-8");
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}

		return retSrc;
	}

	public static boolean hasInternet(Context context) {
		boolean netflag = false;

		if (null != context) {
			ConnectivityManager manager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo info = manager.getActiveNetworkInfo();
			if (info != null && info.isConnected()) {
				if (info.isAvailable()) {
					netflag = true;
				}
			} else {
				netflag = false;
			}
		}
		return netflag;
	}

}
