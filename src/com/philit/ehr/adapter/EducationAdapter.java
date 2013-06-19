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
import com.philit.ehr.db.EducationData;

public class EducationAdapter extends ArrayAdapter<EducationData> {
	private List<EducationData> datas;
	private LayoutInflater layoutInflater;
	
	public EducationAdapter(Context context, int layoutResourceId, List<EducationData> datas) {
		super(context, layoutResourceId, datas);
		this.datas = datas;
		this.layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return datas.size();
	}
	/**
	* 获取某一位置的数据
	*/
	@Override
	public EducationData getItem(int position) {
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
		
		EducationTag educationTag = null;
		if (convertView == null) {
			educationTag = new EducationTag();
			// 获取组件布局
			convertView = layoutInflater.inflate(R.layout.item_education, null);
			educationTag.dateTimeTv = (TextView) convertView.findViewById(R.id.dateTimeTv);
			educationTag.titleTv = (TextView) convertView.findViewById(R.id.titleTv);
			// 这里要注意，是使用的tag来存储数据的。
			convertView.setTag(educationTag);
		} else {
			educationTag = (EducationTag) convertView.getTag();
		}
		// 绑定数据、事件触发
		try {
			EducationData educationData = datas.get(position);
			Date date = new Date(educationData.getA_DateTime());
			int year = date.getYear() + 1900;
			int month = date.getMonth() + 1;
			int day = date.getDate();
			educationTag.dateTimeTv.setText(year + "-" + month +"-" + day);
			educationTag.titleTv.setText(educationData.getA_Theme());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return convertView;
	}

	class EducationTag{
		private TextView dateTimeTv,titleTv;
	}
}
