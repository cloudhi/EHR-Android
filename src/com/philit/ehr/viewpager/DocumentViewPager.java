package com.philit.ehr.viewpager;

import java.util.ArrayList;
import java.util.List;


import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.philit.ehr.R;
import com.philit.ehr.R.string;
import com.philit.ehr.adapter.BinderAdapter;
import com.philit.ehr.adapter.DocumentPagerAdapter;
import com.philit.ehr.db.Database;
import com.philit.ehr.db.DocumentData;
import com.philit.ehr.define.Define;
import com.philit.ehr.util.BitmapUtils;
import com.philit.ehr.util.FileUtils;
import com.philit.ehr.util.ShowToast;
import com.philit.ehr.view.BinderView;
import com.philit.ehr.view.DocumentView;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

public class DocumentViewPager extends ViewPager{

	private List<DocumentData> list;
	private List<View> documentViews = new ArrayList<View>();
	private int position, oldPostion;
	private DocumentPagerAdapter adapter;
	private DocumentView viewTemp;
	private List<DocumentData> documentDatas;
	private DocumentPageAdapterInitListener listener;
	private int pageSize = 8;
	
	@SuppressLint("HandlerLeak")
	private Handler getViewHandler = new Handler(){
		@Override
		public void handleMessage(final Message msg){
			
			if(msg.what > 0){
				if (viewTemp != null) {
					viewTemp.removeAllView();
				}
				if (msg.what == 1) {
					documentDatas = Database.getInstance().getDocumentList(pageSize, position + 1);
				}
				List<BinderView> binderViews = new ArrayList<BinderView>();
				for (int k = 0; k < documentDatas.size(); k++) {
					final DocumentData documentData = documentDatas.get(k);
					/*List<Bitmap> bitmaps = new ArrayList<Bitmap>();
					bitmaps.add(BitmapUtils.drawableToBitmap(getResources().getDrawable(R.drawable.word)));
					BinderAdapter binderAdapter = new BinderAdapter(getContext(), documentData.getD_Name(), bitmaps);*/
					BinderAdapter binderAdapter = new BinderAdapter(getContext(), documentData);
					BinderView binderView = new BinderView(getContext(), binderAdapter);
					
					binderView.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							String ext = documentData.getD_Url().substring(documentData.getD_Url().lastIndexOf("."));
							if (ext.equals(".doc") || ext.equals(".docx")) {
								getContext().startActivity(FileUtils.getWordFileIntent(documentData.getD_Url()));
							}else if (ext.equals(".ppt") || ext.equals(".pptx")) {
								getContext().startActivity(FileUtils.getPptFileIntent(documentData.getD_Url()));
							}else if (ext.equals(".pdf")) {
								getContext().startActivity(FileUtils.getPdfFileIntent(documentData.getD_Url()));
							}else if (ext.equals(".txt")) {
								getContext().startActivity(FileUtils.getTextFileIntent(documentData.getD_Url(), false));
							}
						}
					});
					binderViews.add(binderView);
				}
				
				viewTemp = (DocumentView) documentViews.get(position);
				viewTemp.setList(binderViews, position + 1);
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
		int pageCount = (int) Math.ceil((double)(Database.getInstance().getAllDocumentCounts() / pageSize));
		for (int i = 0; i < pageCount; i++) {
			DocumentView documentView = new DocumentView(getContext());
			documentViews.add(documentView);
		}
		
		adapter = new DocumentPagerAdapter(documentViews);
		setAdapter(adapter);
		setOnPageChangeListener(new MainOnPageChangeListener());
		listener = new DocumentPageAdapterInitListener() {
			
			@Override
			public void finish() {
				viewTemp.initFlip();
			}
			
			@Override
			public void fail(String msg) {
				ShowToast.show((Activity)getContext(), msg);
			}
		};
		
		if (documentViews.size() > 0) {
			initDocumentView(position, listener);
		}
	}
	
	/**
	 * 页卡改变监听器
	 */
	class MainOnPageChangeListener implements OnPageChangeListener {
		
		@Override
		public void onPageSelected(final int arg0) {
			oldPostion = position;
			position = arg0;
			
			switch (position) {
			case 0:
				((SlidingFragmentActivity)getContext()).getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
				break;
			default:
				((SlidingFragmentActivity)getContext()).getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
				break;
			}
			
			if (viewTemp != null) {
		    	viewTemp.setPause(true);
			}
			
			initDocumentView(position, listener);
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

	public List<DocumentData> getList() {
		return list;
	}

	public void setList(List<DocumentData> list) {
		this.list = list;
		initMainViewPager();
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
	public void initDocumentView(final int index, final DocumentPageAdapterInitListener listener) {
	    
		documentDatas = Database.getInstance().getDocumentList(pageSize, index +1);
		if (documentDatas.size() > 0)
		{
			postDelayed(new Runnable() {
				
				@Override
				public void run() {
					getViewHandler.sendEmptyMessage(2);
				}
			}, 200);  //推迟执行可以让避免在翻页的同时加载大量数据，造成运行缓慢的感觉
		}
	}
	
	public interface DocumentPageAdapterInitListener{
		void finish();
		void fail(String msg);
	}
}
