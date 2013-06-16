package com.philit.ehr.http.base;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class HttpRequestOperation implements Runnable {

	public static final String HTTP_METHOD_POST = "post";
	public static final String HTTP_METHOD_GET = "get";
	public static final String DEFAULT_HTTP_METHOD = HTTP_METHOD_GET;

	private String url;
	private String httpMethodName;
	private Map<String, Object> parametersMap;
	
	private HttpRequestResponseHandler responseHandler;
	
	public HttpRequestOperation(String url) {
		this.setUrl(url);
		this.setHttpMethodName(DEFAULT_HTTP_METHOD);
		this.setParametersMap(new HashMap<String, Object>());
	}

	public void putParameter(String key, Object value) {
		parametersMap.put(key, value);
	}

	private MultipartEntity getPostFormEntity(){
		
		MultipartEntity multipartEntity = new MultipartEntity();
		if (parametersMap != null) {
			for (Map.Entry<String, Object> entry : parametersMap.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				if (value instanceof File) {
					multipartEntity.addPart(key, new FileBody((File) value));
				}else if (value != null) {
					try {
						multipartEntity.addPart(key, new StringBody(value.toString(), Charset .forName(org.apache.http.protocol.HTTP.UTF_8)));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return multipartEntity;
	}
	
	private String getGetFormEntity() {
		String paramString = "?";
		if (parametersMap != null) {
			for (Map.Entry<String, Object> entry : parametersMap.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				paramString = paramString +key+"="+value+"&";
			}
		}
		return paramString;
	}

	@Override
	public void run() {
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpUriRequest httpRequest;
			if (httpMethodName.equals(HTTP_METHOD_POST)) {
				HttpPost httpPost = new HttpPost(url);
				httpPost.setEntity(getPostFormEntity());
				httpRequest = httpPost;
			} else if (httpMethodName.equals(HTTP_METHOD_GET)) {
				HttpGet httpGet = new HttpGet(url+getGetFormEntity());
				httpRequest = httpGet;
			} else {
				HttpGet httpGet = new HttpGet(url+getGetFormEntity());
				httpRequest = httpGet;
			}

			httpRequest.addHeader("Accept-Encoding", "utf-8");
			httpRequest.addHeader("Accept-Language", "en-us");

			HttpResponse response = httpClient.execute(httpRequest);
			String responseString = EntityUtils.toString(response.getEntity());

			if (responseHandler != null) {
				responseHandler.requestFinishWithResponseString(responseString);
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
			if (responseHandler != null) {
				responseHandler.fail(e.getMessage());
			}
		} catch (IOException e) {
			e.printStackTrace();
			if (responseHandler != null) {
				responseHandler.fail(e.getMessage());
			}
		}
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHttpMethodName() {
		return httpMethodName;
	}

	public void setHttpMethodName(String httpMethodName) {
		this.httpMethodName = httpMethodName;
	}

	public Map<String, Object> getParametersMap() {
		return parametersMap;
	}

	public void setParametersMap(Map<String, Object> parametersMap) {
		this.parametersMap = parametersMap;
	}

	public HttpRequestResponseHandler getResponseHandler() {
		return responseHandler;
	}

	public void setResponseHandler(HttpRequestResponseHandler responseHandler) {
		this.responseHandler = responseHandler;
	}

}
