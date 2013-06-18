package com.philit.ehr.fragment;

import java.io.File;
import java.util.List;

import com.philit.ehr.R;
import com.philit.ehr.R.id;
import com.philit.ehr.db.AnnouncementData;
import com.philit.ehr.db.Database;
import com.philit.ehr.db.DocumentData;
import com.philit.ehr.define.Define;
import com.philit.ehr.viewpager.AnnouncementViewPager;
import com.philit.ehr.viewpager.DocumentViewPager;

import android.R.integer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AnnouncementContentFragment extends Fragment{

	private int mPos = -1, position;
	private View mainView;
	
	public AnnouncementContentFragment() {
		this.position = 0;
	}
	
	public AnnouncementContentFragment(int position) {
		this.position = position;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (mPos == -1 && savedInstanceState != null)
			mPos = savedInstanceState.getInt("mPos");
		mainView = inflater.inflate(R.layout.announcement_frame, null);
		AnnouncementViewPager announcementViewPager = (AnnouncementViewPager)mainView.findViewById(R.id.announcementViewPager);
		List<AnnouncementData> announcementDatas = Database.getInstance().getAllAnnouncementList();
		announcementViewPager.setList(announcementDatas, position);
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
