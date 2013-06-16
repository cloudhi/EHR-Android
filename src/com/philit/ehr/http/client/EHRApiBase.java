package com.philit.ehr.http.client;

import org.json.JSONObject;

import android.util.Log;

import com.philit.ehr.http.base.HttpRequestOperation;

public class EHRApiBase extends HttpRequestOperation {

	public enum Status {
		SUCCESS, ERROR,
	}

	private static final String STATUS_CODE_SUCCESS = "success";
	private static final String STATUS_CODE_ERROR = "error";

	private static final String STATUS_KEY = "status";

	public EHRApiBase(String url) {
		super(url);
		Log.i("jiang", "WeitianClient request url : " + url);
	}

	protected Status getRequestStatus(JSONObject jsonObject) {
		String statusString = jsonObject.optString(STATUS_KEY,
				STATUS_CODE_ERROR);
		if (statusString.equals("200")) {
			return Status.SUCCESS;
		}

		return Status.ERROR;
	}
}
