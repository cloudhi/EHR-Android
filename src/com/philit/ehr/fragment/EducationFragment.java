package com.philit.ehr.fragment;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.philit.ehr.R;
import com.philit.ehr.activity.MainActivity;
import com.philit.ehr.adapter.AnnouncementAdapter;
import com.philit.ehr.adapter.EducationAdapter;
import com.philit.ehr.db.AnnouncementData;
import com.philit.ehr.db.Database;
import com.philit.ehr.db.EducationData;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

import android.R.anim;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class EducationFragment extends Fragment{

	private int mPos = -1;
	private View mainView;
	private TextView showMenuTv;
	private ImageView right_angle;
	private ListView educationLv;
	private EducationAdapter adapter;
	List<EducationData> educationDatas;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (mPos == -1 && savedInstanceState != null)
			mPos = savedInstanceState.getInt("mPos");
		mainView = inflater.inflate(R.layout.announcement_list, null);
		showMenuTv = (TextView) mainView.findViewById(R.id.showMenuTv);
		showMenuTv.setText(R.string.health_education);
		showMenuTv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				((SlidingFragmentActivity)getActivity()).getSlidingMenu().showMenu();
			}
		});
	
		right_angle = (ImageView) mainView.findViewById(R.id.right_angle);
		right_angle.setImageResource(R.drawable.education_right_angle);
		
		educationLv = (ListView) mainView.findViewById(R.id.announcementLv);
		educationLv.setDividerHeight(0);
		return mainView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		educationDatas = Database.getInstance().getAllEducationList();
		if (educationDatas.size() == 0) {
			for (int i = 0; i < 10; i++) {
				long modifyTime = 0;
				try {
					modifyTime = sdf.parse(MessageFormat.format("2012-{0}-{1} 12:23:22", 8, i+1)).getTime();
				} catch (ParseException e) {
					e.printStackTrace();
				}
				EducationData educationData = new EducationData();
				educationData.setActivityID(i);
				educationData.setA_DateTime(modifyTime);
				educationData.setA_Location("小区活动中心");
				educationData.setA_Form("讲座");
				educationData.setA_Object("禄段村委会居民");
				educationData.setA_Crowd("高血压患者及其亲属");
				educationData.setA_Duration(90);
				educationData.setA_Organizers("禄段村委会");
				educationData.setA_Partners("加多宝");
				educationData.setA_Missionary("test");
				educationData.setA_Number(120);
				educationData.setA_Theme("珍惜生命，远离IT" + i);
				Database.getInstance().createEducation(educationData);
			}
			for (int i = 10; i < 20; i++) {
				long modifyTime = 0;
				try {
					modifyTime = sdf.parse(MessageFormat.format("2013-{0}-{1} 12:23:22", 3, i+1)).getTime();
				} catch (ParseException e) {
					e.printStackTrace();
				}
				EducationData educationData = new EducationData();
				educationData.setActivityID(i);
				educationData.setA_DateTime(modifyTime);
				educationData.setA_Location("小区活动中心");
				educationData.setA_Form("讲座");
				educationData.setA_Object("禄段村委会居民");
				educationData.setA_Crowd("高血压患者及其亲属");
				educationData.setA_Duration(90);
				educationData.setA_Organizers("禄段村委会");
				educationData.setA_Partners("加多宝");
				educationData.setA_Missionary("test");
				educationData.setA_Number(120);
				educationData.setA_Theme("珍惜生命，远离IT" + i);
				Database.getInstance().createEducation(educationData);
			}
		}
		
		educationDatas = Database.getInstance().getAllEducationList();
		adapter = new EducationAdapter(getActivity(), 0, educationDatas);
		educationLv.setAdapter(adapter);
		educationLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				((MainActivity)getActivity()).switchContent(new EducationContentFragment(arg2));
			}
		});
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("mPos", mPos);
	}
}
