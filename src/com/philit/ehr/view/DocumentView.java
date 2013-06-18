package com.philit.ehr.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.R.integer;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.philit.ehr.R;
import com.philit.ehr.db.PeriodicalData;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

public class DocumentView extends LinearLayout{

	private List<BinderView> binderViews;
	private List<PeriodicalData> periodicalDatas;
	private BinderLayout binderLayout;
	private View mainView, halfTransparentView;
	private Timer timer1, timer2;
	private int index;
	private int num1 = -1, num2 = -1;
	private boolean pause;
	private Context context;
	private float downY, upY;
	private AlphaAnimation alphaAnimation;
	private BigTimerTask bigTimerTask;
	private FirstTimerTask firstTimerTask;
	private ArrayAdapter<PeriodicalData> adapter;
	private TextView pageIndexTv, showMenuTv;
	private ImageView right_angle;
	
	public DocumentView(Context context) {
		super(context);
		this.context = context;
		this.binderViews = new ArrayList<BinderView>();
		this.periodicalDatas = new ArrayList<PeriodicalData>();
		initView();
	}
	
	/**
	 * Default constructor.
	 */
	public DocumentView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initView();
	}
	
	@SuppressWarnings("deprecation")
	public void initView() {
		mainView = inflate(getContext(), R.layout.view_document, null);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		addView(mainView, params);
		
		RelativeLayout main_layout = (RelativeLayout) mainView.findViewById(R.id.main_layout);
		binderLayout = (BinderLayout) mainView.findViewById(R.id.binderLayout);
		
		halfTransparentView = mainView.findViewById(R.id.half_transparent);
		pageIndexTv = (TextView) mainView.findViewById(R.id.pageIndex);
		pageIndexTv.setVisibility(View.VISIBLE);
		showMenuTv = (TextView) findViewById(R.id.showMenuTv);
		showMenuTv.setText(R.string.health_knowledge);
		showMenuTv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				((SlidingFragmentActivity)getContext()).getSlidingMenu().showMenu();
			}
		});
		
		right_angle = (ImageView) findViewById(R.id.right_angle);
		right_angle.setImageResource(R.drawable.health_right_angle);
		
		pause = true;  //默认暂停
	}
	
	/**
	 * 初始化主题
	 */
	public void initTitle() {
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.push_left_top_in);
	}
	
	public void removeTitle() {
		//subject.setVisibility(View.INVISIBLE);
	}
	
	/**
	 * 初始化翻动
	 */
	public void initFlip() {
		//initTitle();
		if (binderViews.size() > 0) {
			pause = false;
			index = 0;
			if (firstTimerTask == null) {
				synchronized (FirstTimerTask.class) {
					if (firstTimerTask == null) {
						firstTimerTask = new FirstTimerTask();
						timer1 = new Timer(true);
						timer1.schedule(firstTimerTask, 1000, 400);
					}
				}
			}else {
				synchronized (FirstTimerTask.class) {
					firstTimerTask = new FirstTimerTask();
					timer1 = new Timer(true);
					timer1.schedule(firstTimerTask, 1000, 400);
				}
			}
		}else {
			if (firstTimerTask != null) {
				firstTimerTask.cancel();
				firstTimerTask = null;
			}
		}
	}
	
	/**
	 * 开始随机翻动
	 */
	public void beginRandomFlip(){
		if (!pause) {
			if (bigTimerTask == null) {
				synchronized (BigTimerTask.class) {
					if (bigTimerTask == null) {
						bigTimerTask = new BigTimerTask();
						timer2 = new Timer(true);
						timer2.schedule(bigTimerTask, 3000 + 400*(binderViews.size()), 3000);
					}
				}
			}
		}else {
			if (bigTimerTask != null) {
				bigTimerTask.cancel();
				bigTimerTask = null;
			}
		}
	}
	
	class FirstTimerTask extends TimerTask{

		@Override
		public void run() {
			if (index < binderViews.size()) {
				if (pause) {
					this.cancel();
					timer1.cancel();
				}else {
					post(new Runnable() {
					
						@Override
						public void run() {
							binderLayout.flip(index);
							index++;
						}
					});
				}
			}else {
				beginRandomFlip();
				this.cancel();
				timer1.cancel();
			}
		}
	}
	
	class BigTimerTask extends TimerTask{

		@Override
		public void run() {
			if (pause) {
				this.cancel();
			}else {
				Random random = new Random();
		        num1 = random.nextInt(binderViews.size());
		        while (binderViews.get(num1).isMoving()) {
		        	num1 = random.nextInt(binderViews.size());
				}
		        //3个以上才随机翻动2个
		        if(binderViews.size() >= 3){
		        	num2 = random.nextInt(binderViews.size());
		        	//保证随机数不相等,并且不能是正在移动的
		        	while (num1 == num2 || binderViews.get(num2).isMoving()) {
		        		num2 = random.nextInt(binderViews.size());
					}
		        }
		        
		        postDelayed(new Runnable() {
					
					@Override
					public void run() {
						binderLayout.flip(num1);
					}
				},400);
		        
		        postDelayed(new Runnable() {
					
					@Override
					public void run() {
						binderLayout.flip(num2);
					}
				},1000);
			}
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			return false;
		}
		return super.onTouchEvent(event);
	}

	public void removeAllView() {
		binderLayout.removeAllView();
	}

	

	/*public BinderLayout getBinderLayout() {
		return binderLayout;
	}

	public void setBinderLayout(BinderLayout binderLayout) {
		this.binderLayout = binderLayout;
		this.binderViews = binderLayout.getDataSources();
		binderLayout.setAutoFlip(true);
	}*/

	public List<BinderView> getList() {
		return binderViews;
	}

	public void setList(List<BinderView> list, int pageIndex) {
		this.binderViews = list;
		binderLayout.setDataSources(binderViews);
		binderLayout.setAutoFlip(true);
		pageIndexTv.setText(pageIndex + "");
	}

	public boolean isPause() {
		return pause;
	}

	public void setPause(boolean pause) {
		//如果原来已经暂停，则重新启动
		if (this.pause) {
			this.pause = pause;
		}else{
			this.pause = pause;
		}
		beginRandomFlip();
	}
	
	public void bringTitleToFront() {
		binderLayout.bringTitleToFront();
		setPause(true);
	}
}
