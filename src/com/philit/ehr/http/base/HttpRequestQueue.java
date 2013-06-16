package com.philit.ehr.http.base;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpRequestQueue {
	private ExecutorService requestQueue;

	public HttpRequestQueue(int queueSize) {
		requestQueue = Executors.newFixedThreadPool(queueSize);
	}

	public void addRequest(Runnable request) {
		requestQueue.execute(request);
	}
}
