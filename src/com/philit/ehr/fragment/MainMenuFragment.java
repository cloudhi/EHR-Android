package com.philit.ehr.fragment;

import com.philit.ehr.R;
import com.philit.ehr.activity.MainActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class MainMenuFragment extends Fragment implements OnClickListener{
	private View mainView;
	private LinearLayout user_info_layout, myrecord_layout, medication_layout, fllowup_layout, health_layout, announcement_layout, education_layout;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mainView = inflater.inflate(R.layout.main_menu_frame, null);
		return mainView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		user_info_layout = (LinearLayout) mainView.findViewById(R.id.user_info_layout);
		user_info_layout.setOnClickListener(this);
		
		myrecord_layout = (LinearLayout) mainView.findViewById(R.id.myrecord_layout);
		myrecord_layout.setOnClickListener(this);
		
		medication_layout = (LinearLayout) mainView.findViewById(R.id.medication_layout);
		medication_layout.setOnClickListener(this);
		
		medication_layout = (LinearLayout) mainView.findViewById(R.id.medication_layout);
		medication_layout.setOnClickListener(this);
		
		fllowup_layout = (LinearLayout) mainView.findViewById(R.id.fllowup_layout);
		fllowup_layout.setOnClickListener(this);
		
		announcement_layout = (LinearLayout) mainView.findViewById(R.id.announcement_layout);
		announcement_layout.setOnClickListener(this);
		
		education_layout = (LinearLayout) mainView.findViewById(R.id.announcement_layout);
		education_layout.setOnClickListener(this);
		
		/*getFragmentManager()
		.beginTransaction()
		.replace(R.id.menu_frameLayout, new MenuFragment())
		.commit();*/
	}

	@Override
	public void onClick(View v) {
		MainActivity mainActivity = (MainActivity)getActivity();
		switch (v.getId()) {
		case R.id.user_info_layout:
			mainActivity.switchContent(new LoginFragment());
			break;
		case R.id.myrecord_layout:
			mainActivity.switchContent(new MyRecordFragment());
			break;
		case R.id.medication_layout:
			mainActivity.switchContent(new MedicineFragment());
			break;
		case R.id.fllowup_layout:
			mainActivity.switchContent(new FllowUpFragment());
			break;
		case R.id.announcement_layout:
			mainActivity.switchContent(new AnnouncementFragment());
			break;
		case R.id.education_layout:
			mainActivity.switchContent(new EdcuationFragment());
			break;
		}
	}
}
