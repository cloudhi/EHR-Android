package com.philit.ehr.http.base;

public class HttpAsyncDownloadFileOperation implements Runnable {

	private String url;
	private String saveFilePath;

	/*
	 * 下载事件监听
	 */

	public interface DownloadEventListener {
		void downloadFinish();

		void downloadFail();
	}

	private DownloadEventListener downloadEventListener;

	public DownloadEventListener getDownloadEventListener() {
		return downloadEventListener;
	}

	public void setDownloadEventListener(
			DownloadEventListener downloadEventListener) {
		this.downloadEventListener = downloadEventListener;
	}

	/**
	 * 
	 * @param url
	 * @param saveFilePath
	 */
	public HttpAsyncDownloadFileOperation(String url, String saveFilePath) {
		super();

		this.url = url;
		this.saveFilePath = saveFilePath;
	}

	@Override
	public void run() {
		int result = HttpDownloader.getInstance().downFile(url, saveFilePath);

		if (downloadEventListener != null) {
			if (result == -1) {
				downloadEventListener.downloadFail();
			} else {
				downloadEventListener.downloadFinish();
			}
		}
	}

}
