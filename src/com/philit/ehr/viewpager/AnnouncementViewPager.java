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
import com.philit.ehr.adapter.AnnouncementPagerAdapter;
import com.philit.ehr.adapter.BinderAdapter;
import com.philit.ehr.adapter.DocumentPagerAdapter;
import com.philit.ehr.db.AnnouncementData;
import com.philit.ehr.db.Database;
import com.philit.ehr.db.DocumentData;
import com.philit.ehr.define.Define;
import com.philit.ehr.util.BitmapUtils;
import com.philit.ehr.util.FileUtils;
import com.philit.ehr.util.ShowToast;
import com.philit.ehr.view.AnnouncementView;
import com.philit.ehr.view.BinderView;
import com.philit.ehr.view.DocumentView;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

public class AnnouncementViewPager extends ViewPager{

	private List<View> announcementViews = new ArrayList<View>();
	private int position, oldPostion;
	private AnnouncementPagerAdapter adapter;
	private AnnouncementView viewTemp;
	private List<AnnouncementData> announcementDatas;
	private AnouncementPageAdapterInitListener listener;
	
	@SuppressLint("HandlerLeak")
	private Handler getViewHandler = new Handler(){
		@Override
		public void handleMessage(final Message msg){
			
			if(msg.what > 0){
				if (msg.what == 1) {
					announcementDatas = Database.getInstance().getAllAnnouncementList();
				}
				
				viewTemp = (AnnouncementView) announcementViews.get(position);
				listener.finish();
			}else{
				listener.fail("Error");
			}
		}
	};
	
	public AnnouncementViewPager(Context context) {
		super(context);
	}
	
	public AnnouncementViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public void initMainViewPager() {
		for (int i = 0; i < announcementDatas.size(); i++) {
			AnnouncementView announcementView = new AnnouncementView(getContext());
			announcementViews.add(announcementView);
		}
		
		adapter = new AnnouncementPagerAdapter(announcementViews);
		setAdapter(adapter);
		setOnPageChangeListener(new MainOnPageChangeListener());
		listener = new AnouncementPageAdapterInitListener() {
			
			@Override
			public void finish() {
				viewTemp.loadData(announcementDatas.get(position), position);
			}
			
			@Override
			public void fail(String msg) {
				ShowToast.show((Activity)getContext(), msg);
			}
		};
		
		if (announcementViews.size() > 0) {
			initAnnouncementView(position, listener);
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
			
			initAnnouncementView(position, listener);
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

	public List<AnnouncementData> getList() {
		return announcementDatas;
	}

	public void setList(List<AnnouncementData> list) {
		this.announcementDatas = list;
		initMainViewPager();
		setCurrentItem(position);
	}
	
	public void setList(List<AnnouncementData> list, int position) {
		this.position = position;
		setList(list);
	}
	
	@SuppressLint("HandlerLeak")
	public void initAnnouncementView(final int index, final AnouncementPageAdapterInitListener listener) {
		if (announcementDatas.size() == 0) {
			announcementDatas = Database.getInstance().getAllAnnouncementList();
		}
		getViewHandler.sendEmptyMessage(2);
	}
	
	public interface AnouncementPageAdapterInitListener{
		void finish();
		void fail(String msg);
	}
}
