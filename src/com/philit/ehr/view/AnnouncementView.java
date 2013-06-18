package com.philit.ehr.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;

import android.R.bool;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.philit.ehr.R;
import com.philit.ehr.db.AnnouncementData;
import com.philit.ehr.db.DocumentData;
import com.philit.ehr.db.PeriodicalData;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

public class AnnouncementView extends LinearLayout{

	private AnnouncementData announcementData;
	private BinderLayout binderLayout;
	private View mainView;
	private int index;
	private Context context;
	private TextView showMenuTv, A_Title, A_DateTime, A_publish_by, A_Content;
	private ImageView right_angle;
	private boolean isLoad;
	
	public AnnouncementView(Context context) {
		super(context);
		this.context = context;
		announcementData = new AnnouncementData();
		initView();
	}
	
	/**
	 * Default constructor.
	 */
	public AnnouncementView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initView();
	}
	
	@SuppressWarnings("deprecation")
	public void initView() {
		mainView = inflate(getContext(), R.layout.announcement_content, null);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		addView(mainView, params);
		
		showMenuTv = (TextView) findViewById(R.id.showMenuTv);
		showMenuTv.setText(R.string.announcement_detail);
		showMenuTv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				((SlidingFragmentActivity)getContext()).getSlidingMenu().showMenu();
			}
		});
		
		right_angle = (ImageView) findViewById(R.id.right_angle);
		right_angle.setImageResource(R.drawable.announcement_right_angle);
		
		A_Title = (TextView) mainView.findViewById(R.id.A_Title);
		A_DateTime = (TextView) mainView.findViewById(R.id.A_DateTime);
		A_publish_by = (TextView) mainView.findViewById(R.id.A_publish_by);
		A_Content = (TextView) mainView.findViewById(R.id.A_Content);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			return false;
		}
		return super.onTouchEvent(event);
	}

	public void removeAllView() {
		binderLayout.removeAllView();
	}

	public void loadData(AnnouncementData announcementData, int pageIndex) {
		if (!isLoad) {
			this.announcementData = announcementData;
			A_Title.setText("  "+ announcementData.getA_Title());
			Date date = new Date(announcementData.getA_DateTime());
			int year = date.getYear() + 1900;
			int month = date.getMonth() + 1;
			int day = date.getDate();
			A_DateTime.setText(year + "-" + month + "-" + day);
			A_publish_by.setText(announcementData.getA_ResponsibilityUserID() + "");
			A_Content.setText(announcementData.getA_Content());
			isLoad = true;
		}
	}
}
