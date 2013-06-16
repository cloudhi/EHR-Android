package com.philit.ehr.http.base;

import org.json.JSONException;
import org.json.JSONObject;

abstract public class HttpRequestJsonResponseHandler extends
		HttpRequestResponseHandler implements
		HttpRequestJsonResponseHandlerInterface {

	public void requestFinishWithResponseString(String responseString) {
		super.requestFinishWithResponseString(responseString);
		try {
			JSONObject jsonObject = new JSONObject(responseString);
			this.jsonFinish(jsonObject);
		} catch (JSONException e) {
			e.printStackTrace();
			this.jsonFail(e.getMessage());
		}
	}

	public void requestFail(String response) {
		super.requestFail(response);
		this.fail(response);
	}

}
