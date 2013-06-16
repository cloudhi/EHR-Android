package com.philit.ehr.view;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.google.gson.Gson;
import com.philit.ehr.R;
import com.philit.ehr.adapter.BinderAdapter;
import com.philit.ehr.adapter.WeekPagerAdapter;
import com.philit.ehr.db.ArticleData;
import com.philit.ehr.db.Database;
import com.philit.ehr.db.ImageData;
import com.philit.ehr.db.PeriodicalData;
import com.philit.ehr.define.Define;
import com.philit.ehr.http.base.HttpAsyncDownloadFileOperation.DownloadEventListener;
import com.philit.ehr.http.client.EHRClient;
import com.philit.ehr.util.BitmapUtils;
import com.philit.ehr.util.ShowToast;

public class DocumentViewPager extends ViewPager{

	private List<PeriodicalData> list;
	private PeriodicalData periodicalData;
	private List<View> newWeekViews = new ArrayList<View>();
	private List<NewWeekView> newWeekViews_cache = new ArrayList<NewWeekView>(); //缓存页面：0保存当前页面；1保存上一个页面；
	private boolean flagX, flagY;
	private float distanceX =0, distanceY =0;
	private float downX = 0,downY = 0, moveX = 0, moveY = 0, upX = 0, upY = 0;
	private int position, oldPostion;
	private WeekPagerAdapter adapter;
	private NewWeekView viewTemp;
	private List<ArticleData> articleDatas;
	private WeekPageAdapterInitListener listener;
	
	@SuppressLint("HandlerLeak")
	private Handler getViewHandler = new Handler(){
		@Override
		public void handleMessage(final Message msg){
			
			if(msg.what > 0){
				if (viewTemp != null) {
					//viewTemp.setPause(true);
					viewTemp.removeAllView();
				}
				if (msg.what == 1) {
					articleDatas = Database.getInstance().getArticleList(periodicalData);				
				}
				List<BinderView> binderViews = new ArrayList<BinderView>();
				for (int k = 0; k < articleDatas.size(); k++) {
					final ArticleData articleData = articleDatas.get(k);
					final int article_position = k; 
					List<Bitmap> bitmaps = new ArrayList<Bitmap>();
					List<ImageData> imageDatas = Database.getInstance().getImageListByArticle(articleData, ImageData.THUMB_1);
					for (int b = 0; b < imageDatas.size(); b++) {
						String pathString = imageDatas.get(b).getLocalPath();
						try {
							bitmaps.add(BitmapUtils.loadBitmap(getContext(), pathString));
						} catch (OutOfMemoryError e) {
							listener.fail(e.getMessage());
						}
					}
					BinderAdapter binderAdapter = new BinderAdapter(getContext(), articleData.getTitle(), bitmaps);
					BinderView binderView = new BinderView(getContext(), binderAdapter);
					
					binderView.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							/*Intent intent = new Intent(getContext(), ArticleActivity.class);
							Bundle bundle = new Bundle();
							bundle.putInt("periodicalData_id", articleData.getPeriodical_id());
							bundle.putInt("article_position", article_position);
							intent.putExtras(bundle);
							getContext().startActivity(intent);
							((Activity)getContext()).overridePendingTransition(R.anim.push_up_in, R.anim.hold);*/
						}
					});
					binderViews.add(binderView);
				}
				
				viewTemp = (NewWeekView) newWeekViews.get(position);
				viewTemp.setList(binderViews);
				listener.finish();
			}else{
				listener.fail("Error");
			}
		}
	};
	
	public DocumentViewPager(Context context) {
		super(context);
	}
	
	public DocumentViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public void initMainViewPager() {
		for (int i = 0; i < list.size(); i++) {
			NewWeekView newWeekView = new NewWeekView(getContext());
			newWeekViews.add(newWeekView);
		}
		adapter = new WeekPagerAdapter(newWeekViews);
		setAdapter(adapter);
		setOnPageChangeListener(new MainOnPageChangeListener());
		listener = new WeekPageAdapterInitListener() {
			
			@Override
			public void finish() {
				//adapter = new WeekPagerAdapter(newWeekViews);
				//setAdapter(adapter);
				viewTemp.initFlip();
				viewTemp.initTitle();
			}
			
			@Override
			public void fail(String msg) {
				//viewTemp.initTitle();
				ShowToast.show((Activity)getContext(), msg);
			}
		};
		
		if (list.size() > 0) {
			initNewWeekView(position, listener);
		}
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			downX = event.getX();
			downY = event.getY();
			flagX = false;
			flagY = false;
			break;

		case MotionEvent.ACTION_MOVE:
			moveX = event.getX();
			moveY = event.getY();
			distanceX = Math.abs(moveX - downX);
			distanceY = Math.abs(moveY - downY);
			if(Define.DEBUG){
				Log.d("MainViewPager-onInterceptTouchEvent-ACTION_MOVE", "distanceX"+distanceX);
				Log.d("MainViewPager-onInterceptTouchEvent-ACTION_MOVE", "distanceY"+distanceY);
			}
			if (distanceX > 0 && !flagY) {
				flagX = true;
			}else if (distanceY > 100) {
				flagY = true;
			}
			break;
			
		case MotionEvent.ACTION_UP:
			
			break;
		}
		
		if (flagX) {
			return super.onInterceptTouchEvent(event);	
		}
		if (flagY) {
			return false;
		}
		
		return super.onInterceptTouchEvent(event);
	}
	
	/**
	 * 页卡改变监听器
	 */
	class MainOnPageChangeListener implements OnPageChangeListener {
		
		@Override
		public void onPageSelected(final int arg0) {
			oldPostion = position;
			position = arg0;
			
			if (viewTemp != null) {
		    	viewTemp.removeTitle();
		    	viewTemp.setPause(true);
			}
			
			initNewWeekView(position, listener);
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			if(Define.DEBUG)
				Log.d("onPageScrollStateChanged",arg0+"");
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			
		}
	}

	public List<PeriodicalData> getList() {
		return list;
	}

	public void setList(List<PeriodicalData> list) {
		this.list = list;
		initMainViewPager();
	}
	
	public void changeCache(NewWeekView newWeekView, boolean isNew) {
		if (isNew) {
			for (int i = 0; i < newWeekViews_cache.size(); i++) {
			}
		}
	}
	
	public void onPause() {
		if (viewTemp != null) {
			viewTemp.setPause(true);
		}
	}

	public void onResume() {
		if (viewTemp != null) {
			viewTemp.setPause(false);
		}
	}
	
	@SuppressLint("HandlerLeak")
	public void initNewWeekView(final int index, final WeekPageAdapterInitListener listener) {
	    periodicalData = list.get(index);
	    
		articleDatas = Database.getInstance().getArticleList(periodicalData);
		if (articleDatas.size() > 0)
		{
			postDelayed(new Runnable() {
				
				@Override
				public void run() {
					getViewHandler.sendEmptyMessage(2);
				}
			}, 100);  //推迟执行可以让避免在翻页的同时加载大量数据，造成运行缓慢的感觉
		}else{
			EHRClient.getInstance().listArticle2(periodicalData.getPeriodical_id(), new EHRClient.ResultHandler() {
				
				@Override
				public void requestFinish(JSONObject jsonObject) {
					
					try {
						final JSONArray result = jsonObject.getJSONArray("result");
						if (result != null) {
							Gson gson = new Gson();
							for (int i = 0; i < result.length(); i++) {
								final int result_positon = i;
								String url = Define.ONLINE_SERVER + "/picture/get_picture?picture_id=%s&picture_thumb=%s";
								ArticleData articleData = gson.fromJson(result.getJSONObject(i).toString(), ArticleData.class);
								ArticleData localArticleData = Database.getInstance().getArticleById(articleData.getArticle_id());
								//本地没数据
								if (localArticleData == null) {
									Database.getInstance().createArticle(articleData);
									final String[] picture_id = result.getJSONObject(i).getString("picture_id").split(",");
									for (int j = 0; j < picture_id.length; j++) {
										final int picture_position = j;
										//只下载缩略图
										final ImageData imageData1 = new ImageData(0, Integer.parseInt(picture_id[j]), ImageData.THUMB_1, "", articleData);
										EHRClient.getInstance().downloadFile(String.format(url, picture_id[j], imageData1.getThumb()), imageData1.getLocalPath(), 
												new DownloadEventListener() {
											
											@Override
											public void downloadFinish() {
												Database.getInstance().createImage(imageData1);
												//等待所有图片下载完成
												if (result_positon == result.length() - 1 && picture_position == picture_id.length -1) {
													getViewHandler.sendEmptyMessage(1);
												}
											}
											
											@Override
											public void downloadFail() {
												getViewHandler.sendEmptyMessage(0);
											}
										});
									}
								}else {//否则判断版本
									if (articleData.getVersion() > localArticleData.getVersion()) {
										Database.getInstance().updateArticle(articleData);
										final String[] picture_id = result.getJSONObject(i).getString("picture_id").split(",");
										for (int j = 0; j < picture_id.length; j++) {
											final int picture_position = j;
											//只下载缩略图
											final ImageData imageData1 = new ImageData(0, Integer.parseInt(picture_id[j]), ImageData.THUMB_1, "", articleData);
											EHRClient.getInstance().downloadFile(String.format(url, picture_id[j], imageData1.getThumb()), imageData1.getLocalPath(), 
													new DownloadEventListener() {
												
												@Override
												public void downloadFinish() {
													Database.getInstance().createImage(imageData1);
													//等待所有图片下载完成
													if (result_positon == result.length() - 1 && picture_position == picture_id.length - 1) {
														getViewHandler.sendEmptyMessage(1);
													}
												}
												
												@Override
												public void downloadFail() {
													getViewHandler.sendEmptyMessage(0);
												}
											});
										}
									}else {
										if (result_positon == result.length() - 1) {
											getViewHandler.sendEmptyMessage(1);
										}
									}
								}
							}
						}
					} catch (JSONException e) {
						e.printStackTrace();
						ShowToast.show((Activity)getContext(), e.getMessage());
					}
				}
				
				@Override
				public void requestFail(String response) {
					ShowToast.show((Activity)getContext(), response);
				}
			});
		}
	}
	
	public interface WeekPageAdapterInitListener{
		void finish();
		void fail(String msg);
	}
}
