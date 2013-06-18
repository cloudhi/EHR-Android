package com.philit.ehr.fragment;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import com.philit.ehr.R;
import com.philit.ehr.adapter.AnnouncementAdapter;
import com.philit.ehr.db.AnnouncementData;
import com.philit.ehr.db.Database;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class AnnouncementFragment extends Fragment{

	private int mPos = -1;
	private View mainView;
	private TextView showMenuTv;
	private ImageView right_angle;
	private ListView announcement_list;
	private AnnouncementAdapter adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (mPos == -1 && savedInstanceState != null)
			mPos = savedInstanceState.getInt("mPos");
		mainView = inflater.inflate(R.layout.announcement, null);
		showMenuTv = (TextView) mainView.findViewById(R.id.showMenuTv);
		showMenuTv.setText(R.string.view_announcement);
		showMenuTv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				((SlidingFragmentActivity)getActivity()).getSlidingMenu().showMenu();
			}
		});
		
		right_angle = (ImageView) mainView.findViewById(R.id.right_angle);
		right_angle.setImageResource(R.drawable.announcement_right);
		
		announcement_list = (ListView) mainView.findViewById(R.id.announcement_list);
		announcement_list.setDividerHeight(0);
		announcement_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
			}
		});
		return mainView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<AnnouncementData> announcementDatas = Database.getInstance().getAllAnnouncementList();
		if (announcementDatas.size() == 0) {
			for (int i = 0; i < 5; i++) {
				long modifyTime = 0;
				try {
					modifyTime = sdf.parse(MessageFormat.format("2012-0{0}-1{1} 12:23:22", i+1, i+1)).getTime();
				} catch (ParseException e) {
					e.printStackTrace();
				}
				AnnouncementData announcementData = new AnnouncementData(i, "公告标题公告标题公告标题" + i, "公告内容公告内" + i, modifyTime, 1, 1);
				Database.getInstance().createAnnouncement(announcementData);
			}
			for (int i = 6; i < 10; i++) {
				long modifyTime = 0;
				try {
					modifyTime = sdf.parse(MessageFormat.format("2013-0{0}-1{1} 12:23:22", i+1, i+1)).getTime();
				} catch (ParseException e) {
					e.printStackTrace();
				}
				AnnouncementData announcementData = new AnnouncementData(i, "公告标题公告标题公告标题" + i, "公告内容公告内" + i, modifyTime, 1, 2);
				Database.getInstance().createAnnouncement(announcementData);
			}
		}
		
		announcementDatas = Database.getInstance().getAllAnnouncementList();
		adapter = new AnnouncementAdapter(getActivity(), 0, announcementDatas);
		announcement_list.setAdapter(adapter);
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("mPos", mPos);
	}
}
