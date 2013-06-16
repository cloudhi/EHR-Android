package com.philit.ehr.http.client;

import org.json.JSONObject;

import android.content.Context;

import com.philit.ehr.define.Define;
import com.philit.ehr.http.base.HttpAsyncDownloadFileOperation;
import com.philit.ehr.http.base.HttpRequestQueue;


public class EHRClient extends HttpRequestQueue {

	public static final String BASE_URL = Define.ONLINE_SERVER;
	
	private static final int MAX_CONNECTION_COUNT = 10;
	private Context context;

	public interface ResultHandler {
		public void requestFinish(JSONObject jsonObject);

		public void requestFail(String response);
	};

	/**
	 * 上传文件
	 * 
	 * @param authToken
	 * @param filePath
	 * @param resultHandler
	 */

	public void uploadFile(String authToken, String filePath,
			final ResultHandler resultHandler) {
		if (context != null) {
			NewWeekAPIUploadFile api = new NewWeekAPIUploadFile(BASE_URL,
					authToken, filePath, resultHandler);
			super.addRequest(api);
		}
	}

	/**
	 * 异步下载文件
	 * @param url
	 * @param saveFilePath
	 * @param downloadEventListener
	 */
	
	public void downloadFile(
			String url,
			String saveFilePath,
			HttpAsyncDownloadFileOperation.DownloadEventListener downloadEventListener) {
		if (context != null) {
			HttpAsyncDownloadFileOperation request = new HttpAsyncDownloadFileOperation(
					url, saveFilePath);
			request.setDownloadEventListener(downloadEventListener);
			super.addRequest(request);
		}
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	// *************************
	// WeitianClient singleton (begin)
	// *************************

	public static EHRClient getInstance() {
		if (mInstance == null) {
			synchronized (EHRClient.class) {
				if (mInstance == null) {
					mInstance = new EHRClient();
				}
			}
		}
		return mInstance;
	}

	private EHRClient() {
		super(MAX_CONNECTION_COUNT);
	}

	private static EHRClient mInstance;

	// *************************
	// WeitianClient singleton (end)
	// *************************
}
