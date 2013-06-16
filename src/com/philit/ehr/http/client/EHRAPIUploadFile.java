package com.philit.ehr.http.client;

import java.io.File;

import org.json.JSONObject;

import android.util.Log;

import com.philit.ehr.http.base.HttpRequestJsonResponseHandler;
import com.philit.ehr.http.base.HttpRequestResponseHandler;
import com.philit.ehr.http.client.EHRClient.ResultHandler;

public class EHRAPIUploadFile extends EHRApiBase {

	private static final String UPLOAD_FILE_URL = "/file";
	private static final String PARAM_KEY_AUTH_TOKEN = "auth_token";
	private static final String PARAM_KEY_DATA = "data";
	private static final String ERROR_MSG = "error_msg";

	public EHRAPIUploadFile(String baseUrl, String authToken,
			String filePath, final ResultHandler resultHandler) {
		super(baseUrl + UPLOAD_FILE_URL);

		this.setHttpMethodName(HTTP_METHOD_POST);
		this.putParameter(PARAM_KEY_AUTH_TOKEN, authToken);
		File uploadFile = new File(filePath);
		if (uploadFile.exists() == true) {
			this.putParameter(PARAM_KEY_DATA, uploadFile);
		}

		HttpRequestResponseHandler responseHandler = new HttpRequestJsonResponseHandler() {

			@Override
			public void jsonFinish(JSONObject jsonObject) {
				EHRApiBase.Status responseStatus = EHRAPIUploadFile.super
						.getRequestStatus(jsonObject);
				if (resultHandler != null) {
					if (responseStatus == EHRApiBase.Status.SUCCESS) {
						resultHandler.requestFinish(jsonObject);
					} else {
						resultHandler.requestFail(jsonObject.optString(
								ERROR_MSG, ""));
					}
				}
			}

			@Override
			public void jsonFail(String response) {
				if (resultHandler != null) {
					resultHandler.requestFail(response);
				}
			}

			@Override
			public void finish(String response) {
				Log.i("jie", "upload file response : " + response);
			}

			@Override
			public void fail(String response) {
				if (resultHandler != null) {
					resultHandler.requestFail(response);
				}
			}

		};

		this.setResponseHandler(responseHandler);
	}

}
