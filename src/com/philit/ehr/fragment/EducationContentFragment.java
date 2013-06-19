package com.philit.ehr.fragment;

import java.io.File;
import java.util.List;

import com.philit.ehr.R;
import com.philit.ehr.R.id;
import com.philit.ehr.db.AnnouncementData;
import com.philit.ehr.db.Database;
import com.philit.ehr.db.DocumentData;
import com.philit.ehr.db.EducationData;
import com.philit.ehr.define.Define;
import com.philit.ehr.viewpager.AnnouncementViewPager;
import com.philit.ehr.viewpager.DocumentViewPager;
import com.philit.ehr.viewpager.EducationViewPager;

import android.R.integer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class EducationContentFragment extends Fragment{

	private int mPos = -1, position;
	private View mainView;
	
	public EducationContentFragment() {
		this.position = 0;
	}
	
	public EducationContentFragment(int position) {
		this.position = position;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (mPos == -1 && savedInstanceState != null)
			mPos = savedInstanceState.getInt("mPos");
		mainView = inflater.inflate(R.layout.education_frame, null);
		EducationViewPager educationViewPager = (EducationViewPager)mainView.findViewById(R.id.educationViewPager);
		List<EducationData> announcementDatas = Database.getInstance().getAllEducationList();
		educationViewPager.setList(announcementDatas, position);
		return mainView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("mPos", mPos);
	}
}
