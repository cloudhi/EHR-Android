package com.philit.ehr.http.base;

abstract public class HttpRequestResponseHandler implements
		HttpRequestResponseHandlerInterface {

	public void requestFinishWithResponseString(String responseString) {
		this.finish(responseString);
	}

	public void requestFail(String response) {
		this.fail(response);
	}

}
