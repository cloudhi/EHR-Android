package com.philit.ehr.view;

import java.util.Date;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.philit.ehr.R;
import com.philit.ehr.db.AnnouncementData;
import com.philit.ehr.db.EducationData;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

public class EducationView extends LinearLayout{

	private EducationData educationData;
	private BinderLayout binderLayout;
	private View mainView;
	private int index;
	private Context context;
	private TextView showMenuTv, A_Theme, A_DateTime, A_Location, A_Object, A_Crowd, A_Form, A_Organizers, A_Duration, A_Missionary, A_Partners, A_Number;
	private ImageView right_angle;
	private boolean isLoad;
	
	public EducationView(Context context) {
		super(context);
		this.context = context;
		educationData = new EducationData();
		initView();
	}
	
	/**
	 * Default constructor.
	 */
	public EducationView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initView();
	}
	
	@SuppressWarnings("deprecation")
	public void initView() {
		mainView = inflate(getContext(), R.layout.education_content, null);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		addView(mainView, params);
		
		showMenuTv = (TextView) findViewById(R.id.showMenuTv);
		showMenuTv.setText(R.string.education_detail);
		showMenuTv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				((SlidingFragmentActivity)getContext()).getSlidingMenu().showMenu();
			}
		});
		
		right_angle = (ImageView) findViewById(R.id.right_angle);
		right_angle.setImageResource(R.drawable.education_right_angle);
		
		A_Theme = (TextView) mainView.findViewById(R.id.A_Theme);
		A_DateTime = (TextView) mainView.findViewById(R.id.A_DateTime);
		A_Location = (TextView) mainView.findViewById(R.id.A_Location);
		A_Object = (TextView) mainView.findViewById(R.id.A_Object);
		A_Crowd = (TextView) mainView.findViewById(R.id.A_Crowd);
		A_Form = (TextView) mainView.findViewById(R.id.A_Form);
		A_Organizers = (TextView) mainView.findViewById(R.id.A_Organizers);
		A_Duration = (TextView) mainView.findViewById(R.id.A_Duration);
		A_Missionary = (TextView) mainView.findViewById(R.id.A_Form);
		A_Partners = (TextView) mainView.findViewById(R.id.A_Partners);
		A_Number = (TextView) mainView.findViewById(R.id.A_Number);
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

	public void loadData(EducationData educationData, int pageIndex) {
		if (!isLoad) {
			this.educationData = educationData;
			A_Theme.setText("  "+ educationData.getA_Theme());
			Date date = new Date(educationData.getA_DateTime());
			int year = date.getYear() + 1900;
			int month = date.getMonth() + 1;
			int day = date.getDate();
			A_DateTime.setText(year + "年" + month + "月" + day  + "日");
			A_Location.setText(educationData.getA_Location());
			A_Object.setText(educationData.getA_Object());
			A_Crowd.setText(educationData.getA_Crowd());
			A_Form.setText(educationData.getA_Form());
			A_Organizers.setText(educationData.getA_Organizers());
			A_Duration.setText(educationData.getA_Duration() + "");
			A_Missionary.setText(educationData.getA_Missionary());
			A_Partners.setText(educationData.getA_Partners());
			A_Number.setText(educationData.getA_Number() + "");
			isLoad = true;
		}
	}
}
