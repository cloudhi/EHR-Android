package com.philit.ehr.fragment;

import com.philit.ehr.R;
import com.philit.ehr.activity.MainActivity;

import android.R.integer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class MainMenuFragment extends Fragment implements OnClickListener{
	private View mainView;
	private LinearLayout user_info_layout, myrecord_layout, medication_layout, fllowup_layout, health_layout, announcement_layout, education_layout;
	private ImageButton myrecord_arrow_ib, medication_arrow_ib, fllowup_arrow_ib, health_arrow_ib, announcement_arrow_ib, education_arrow_ib;
	
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
		myrecord_arrow_ib = (ImageButton) mainView.findViewById(R.id.myrecord_arrow_ib);
		
		medication_layout = (LinearLayout) mainView.findViewById(R.id.medication_layout);
		medication_layout.setOnClickListener(this);
		medication_arrow_ib = (ImageButton) mainView.findViewById(R.id.medication_arrow_ib);

		fllowup_layout = (LinearLayout) mainView.findViewById(R.id.fllowup_layout);
		fllowup_layout.setOnClickListener(this);
		fllowup_arrow_ib = (ImageButton) mainView.findViewById(R.id.fllowup_arrow_ib);
		
		health_layout = (LinearLayout) mainView.findViewById(R.id.health_layout);
		health_layout.setOnClickListener(this);
		health_arrow_ib = (ImageButton) mainView.findViewById(R.id.health_arrow_ib);
		
		announcement_layout = (LinearLayout) mainView.findViewById(R.id.announcement_layout);
		announcement_layout.setOnClickListener(this);
		announcement_arrow_ib = (ImageButton) mainView.findViewById(R.id.announcement_arrow_ib);
		
		education_layout = (LinearLayout) mainView.findViewById(R.id.education_layout);
		education_layout.setOnClickListener(this);
		education_arrow_ib = (ImageButton) mainView.findViewById(R.id.education_arrow_ib);
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
			changeArrow(myrecord_arrow_ib);
			break;
		case R.id.medication_layout:
			mainActivity.switchContent(new MedicineFragment());
			changeArrow(medication_arrow_ib);
			break;
		case R.id.fllowup_layout:
			mainActivity.switchContent(new FllowUpFragment());
			changeArrow(fllowup_arrow_ib);
			break;
		case R.id.health_layout:
			mainActivity.switchContent(new DocumentFragment());
			changeArrow(health_arrow_ib);
			break;
		case R.id.announcement_layout:
			mainActivity.switchContent(new AnnouncementFragment());
			changeArrow(announcement_arrow_ib);
			break;
		case R.id.education_layout:
			mainActivity.switchContent(new EdcuationFragment());
			changeArrow(education_arrow_ib);
			break;
		}
	}
	
	public void changeArrow(ImageButton imageButton) {
		resetArrow();
		imageButton.setImageResource(R.drawable.arrow_right);
	}
	
	public void resetArrow() {
		myrecord_arrow_ib.setImageResource(R.drawable.arrow_down);
		medication_arrow_ib.setImageResource(R.drawable.arrow_down);
		fllowup_arrow_ib.setImageResource(R.drawable.arrow_down);
		health_arrow_ib.setImageResource(R.drawable.arrow_down);
		announcement_arrow_ib.setImageResource(R.drawable.arrow_down);
		education_arrow_ib.setImageResource(R.drawable.arrow_down);
	}
}
