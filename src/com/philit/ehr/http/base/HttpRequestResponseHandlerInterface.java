package com.philit.ehr.http.base;

public interface HttpRequestResponseHandlerInterface {

	public void finish(String response);

	public void fail(String response);

}
