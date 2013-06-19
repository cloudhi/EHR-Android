package com.philit.ehr.activity;

import com.philit.ehr.R;
import com.philit.ehr.fragment.AnnouncementContentFragment;
import com.philit.ehr.fragment.AnnouncementFragment;
import com.philit.ehr.fragment.EducationContentFragment;
import com.philit.ehr.fragment.EducationFragment;
import com.philit.ehr.fragment.MainMenuFragment;
import com.philit.ehr.fragment.LoginFragment;
import com.philit.ehr.fragment.MyRecordFragment;
import com.philit.ehr.view.EducationView;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends SlidingFragmentActivity {

	private Fragment mContent;
	private long temptime=0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.responsive_content_frame);
		
		// check if the content frame contains the menu frame
		if (findViewById(R.id.menu_frame) == null) {
			setBehindContentView(R.layout.menu_frame);
			getSlidingMenu().setSlidingEnabled(true);
			getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
			// show home as up so we can toggle
			//getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		} else {
			// add a dummy view
			View v = new View(this);
			setBehindContentView(v);
			getSlidingMenu().setSlidingEnabled(false);
			getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}

		// set the Above View Fragment
		if (savedInstanceState != null)
			mContent = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
		if (mContent == null){
			//mContent = new LoginFragment();  //判断是否登录
			mContent = new MyRecordFragment();
		}
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, mContent)
		.commit();

		// set the Behind View Fragment
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.menu_frame, new MainMenuFragment())
		.commit();

		// customize the SlidingMenu
		SlidingMenu sm = getSlidingMenu();
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindScrollScale(0.25f);
		sm.setFadeDegree(0.25f);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		getSupportFragmentManager().putFragment(outState, "mContent", mContent);
	}

	public void switchContent(final Fragment fragment) {
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN); //后来加的
		mContent = fragment;
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, fragment)
		.commit();
		Handler h = new Handler();
		h.postDelayed(new Runnable() {
			public void run() {
				getSlidingMenu().showContent();
			}
		}, 50);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_MENU) {
	        getSlidingMenu().showMenu();
	        super.openOptionsMenu();  // 调用这个，就可以弹出菜单
	    }else if (keyCode == KeyEvent.KEYCODE_BACK)  {
			if (mContent instanceof EducationContentFragment) {
				switchContent(new EducationFragment());
				return true;
			}else if (mContent instanceof AnnouncementContentFragment) {
				switchContent(new AnnouncementFragment());
				return true;
			}else {
				if (!getSlidingMenu().getMenu().isShown()) {
					if(System.currentTimeMillis() - temptime >2000) // 2次按返回键的间隔在2s之外
		            {   
		                Toast.makeText(this, "请再按一次返回退出", Toast.LENGTH_SHORT).show();   
		                temptime = System.currentTimeMillis();
		            }   
		            else {   
		                   finish();    
		                   System.exit(0); //正常退出
		            }
					return true;
				}
			}
		}
	    //return true; // 最后，一定要做完以后返回 true，或者在弹出菜单后返回true，其他键返回super，让其他键默认
	  return  super.onKeyDown(keyCode, event);
	}
}
