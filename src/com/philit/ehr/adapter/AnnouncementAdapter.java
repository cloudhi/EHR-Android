package com.philit.ehr.adapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import android.R.integer;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.philit.ehr.R;
import com.philit.ehr.db.AnnouncementData;

public class AnnouncementAdapter extends ArrayAdapter<AnnouncementData> {
	private List<AnnouncementData> datas;
	private LayoutInflater layoutInflater;
	private List<String> yearMonthList;
	private List<String> pointList;
	
	public AnnouncementAdapter(Context context, int layoutResourceId, List<AnnouncementData> datas) {
		super(context, layoutResourceId, datas);
		this.datas = datas;
		this.layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		yearMonthList = new ArrayList<String>();
		pointList = new ArrayList<String>();
	}

	@Override
	public int getCount() {
		return datas.size();
	}
	/**
	* 获取某一位置的数据
	*/
	@Override
	public AnnouncementData getItem(int position) {
		return datas.get(position);
	}
	/**
	* 获取唯一标识
	*/
	@Override
	public long getItemId(int position) {
		return position;
	}
	/**
	* android绘制每一列的时候，都会调用这个方法
	*/
	@SuppressWarnings("deprecation")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		AnnouncementTag announcementTag = null;
		if (convertView == null) {
			announcementTag = new AnnouncementTag();
			// 获取组件布局
			convertView = layoutInflater.inflate(R.layout.item_announcement, null);
			announcementTag.back_pointIv = (ImageView) convertView.findViewById(R.id.back_point);
			announcementTag.month_dayTv = (TextView) convertView.findViewById(R.id.month_dayTv);
			announcementTag.titleTv = (TextView) convertView.findViewById(R.id.titleTv);
			// 这里要注意，是使用的tag来存储数据的。
			convertView.setTag(announcementTag);
		} else {
			announcementTag = (AnnouncementTag) convertView.getTag();
		}
		// 绑定数据、事件触发
		try {
			AnnouncementData announcementData = datas.get(position);
			Date date = new Date(announcementData.getA_DateTime());
			int year = date.getYear() + 1900;
			int month = date.getMonth() + 1;
			int day = date.getDate();
			announcementTag.month_dayTv.setText(month + "/" +day);
			announcementTag.titleTv.setText(announcementData.getA_Title());
			if(!yearMonthList.contains(year + "|" +month)){
				yearMonthList.add(year + "|" +month);
				if (!pointList.contains(year + "|" +month + "|" +day)) {
					pointList.add(year + "|" +month + "|" +day);
				}
				announcementTag.back_pointIv.setVisibility(View.VISIBLE);
			}else {
				if (pointList.contains(year + "|" +month + "|" +day)) {
					announcementTag.back_pointIv.setVisibility(View.VISIBLE);
				}else {
					announcementTag.back_pointIv.setVisibility(View.INVISIBLE);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return convertView;
	}

	class AnnouncementTag{
		private ImageView back_pointIv;
		private TextView month_dayTv, titleTv;
	}
}
