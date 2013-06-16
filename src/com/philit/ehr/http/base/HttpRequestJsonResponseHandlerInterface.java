package com.philit.ehr.http.base;

import org.json.JSONObject;

public interface HttpRequestJsonResponseHandlerInterface {

	public void jsonFinish(JSONObject jsonObject);

	public void jsonFail(String response);

}
