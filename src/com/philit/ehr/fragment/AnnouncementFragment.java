package com.philit.ehr.fragment;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.philit.ehr.R;
import com.philit.ehr.activity.MainActivity;
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
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class AnnouncementFragment extends Fragment{

	private int mPos = -1;
	private View mainView;
	private TextView showMenuTv, yearTv;
	private ImageView right_angle;
	private ListView announcementLv;
	private AnnouncementAdapter adapter;
	List<AnnouncementData> announcementDatas;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (mPos == -1 && savedInstanceState != null)
			mPos = savedInstanceState.getInt("mPos");
		mainView = inflater.inflate(R.layout.announcement_list, null);
		showMenuTv = (TextView) mainView.findViewById(R.id.showMenuTv);
		showMenuTv.setText(R.string.view_announcement);
		showMenuTv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				((SlidingFragmentActivity)getActivity()).getSlidingMenu().showMenu();
			}
		});
		
		right_angle = (ImageView) mainView.findViewById(R.id.right_angle);
		right_angle.setImageResource(R.drawable.announcement_right_angle);
		
		yearTv = (TextView) mainView.findViewById(R.id.yearTv);
		
		announcementLv = (ListView) mainView.findViewById(R.id.announcementLv);
		announcementLv.setDividerHeight(0);
		
		return mainView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		announcementDatas = Database.getInstance().getAllAnnouncementList();
		if (announcementDatas.size() == 0) {
			for (int i = 0; i < 10; i++) {
				long modifyTime = 0;
				try {
					modifyTime = sdf.parse(MessageFormat.format("2012-{0}-{1} 12:23:22", 6, i+1)).getTime();
				} catch (ParseException e) {
					e.printStackTrace();
				}
				AnnouncementData announcementData = new AnnouncementData(i, "公告标题公告标题公告标题" + i, "公告内容公告内" + i, modifyTime, 1, 1);
				Database.getInstance().createAnnouncement(announcementData);
			}
			for (int i = 10; i < 20; i++) {
				long modifyTime = 0;
				try {
					modifyTime = sdf.parse(MessageFormat.format("2013-{0}-{1} 12:23:22", 5, i)).getTime();
				} catch (ParseException e) {
					e.printStackTrace();
				}
				AnnouncementData announcementData = new AnnouncementData(i, "公告标题公告标题公告标题" + i, "公告内容公告内" + i, modifyTime, 1, 2);
				Database.getInstance().createAnnouncement(announcementData);
			}
		}
		
		announcementDatas = Database.getInstance().getAllAnnouncementList();
		adapter = new AnnouncementAdapter(getActivity(), 0, announcementDatas);
		announcementLv.setAdapter(adapter);
		announcementLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				((MainActivity)getActivity()).switchContent(new AnnouncementContentFragment(arg2));
			}
		});
		announcementLv.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				Date date = new Date(announcementDatas.get(firstVisibleItem).getA_DateTime());
				int year = date.getYear() + 1900;
				yearTv.setText(year + "");
			}
		});
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("mPos", mPos);
	}
}
