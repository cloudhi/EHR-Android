package com.philit.ehr.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsoluteLayout;

import com.philit.ehr.R;

@SuppressWarnings("deprecation")
public class BinderLayout extends AbsoluteLayout{

	private Context context;
	private List<BinderView> dataSources;
	private View loadingView;
	
	private final int COUNT_1 = 1;
	private final int COUNT_2 = 2;
	private final int COUNT_3 = 3;
	private final int COUNT_4 = 4;
	private final int COUNT_5 = 5;
	private final int COUNT_6 = 6;
	private final int COUNT_7 = 7;
	private final int COUNT_8 = 8;

	List<ItemLayoutParams> list;
	
	public BinderLayout(Context context) {
		super(context);
		this.context = context;
	}
	
	/**
	 * Default constructor.
	 */
	public BinderLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initLoad();
	}
	
	/**
	 * Default constructor.
	 */
	public BinderLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		initLoad();
	}

	public void setDataSources(List<BinderView> dataSources) {
		this.dataSources = dataSources;
		initItems();
	}
	
	public List<BinderView> getDataSources() {
		return this.dataSources;
	}
	
	protected void initLoad() {
		LayoutInflater layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		loadingView = layoutInflater.inflate(R.layout.loading, null);
		LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 0, 0);
		this.addView(loadingView, layoutParams);
	}
	
	/**
	 * 初始化绝对定位的坐标集合
	 */
	protected void initItems() {
		list = new ArrayList<ItemLayoutParams>();
		int count = dataSources.size();
		switch (count) {
		case COUNT_1:
			list.add(new ItemLayoutParams(60, 166, 200, 200));
			break;
		case COUNT_2:
			list.add(new ItemLayoutParams(10, 60, 200, 200));
			list.add(new ItemLayoutParams(110, 270, 200, 200));
			break;
		case COUNT_3:
			list.add(new ItemLayoutParams(10, 60, 198, 96));
			list.add(new ItemLayoutParams(214, 163, 96, 96));
			list.add(new ItemLayoutParams(10, 267, 200, 200));
			break;
		case COUNT_4:
			list.add(new ItemLayoutParams(10, 60, 197, 200));
			list.add(new ItemLayoutParams(213, 60, 97, 200));
			list.add(new ItemLayoutParams(10, 267, 97, 200));
			list.add(new ItemLayoutParams(113, 267, 197, 200));
			break;
		case COUNT_5:
			list.add(new ItemLayoutParams(10, 60, 198, 200));
			list.add(new ItemLayoutParams(213, 60, 96, 96));
			list.add(new ItemLayoutParams(213, 163, 96, 96));
			list.add(new ItemLayoutParams(10, 267, 96, 200));
			list.add(new ItemLayoutParams(113, 267, 197, 200));
			break;
		case COUNT_6:
			list.add(new ItemLayoutParams(10, 60, 198, 200));
			list.add(new ItemLayoutParams(213, 60, 96, 96));
			list.add(new ItemLayoutParams(213, 163, 96, 96));
			list.add(new ItemLayoutParams(10, 267, 96, 96));
			list.add(new ItemLayoutParams(10, 370, 96, 96));
			list.add(new ItemLayoutParams(113, 267, 198, 200));
			break;
		case COUNT_7:
			list.add(new ItemLayoutParams(10, 60, 198, 200));
			list.add(new ItemLayoutParams(215, 60, 96, 96));  //将213改成了215
			list.add(new ItemLayoutParams(215, 163, 96, 96)); //将213改成了215
			list.add(new ItemLayoutParams(10, 267, 96, 96));
			list.add(new ItemLayoutParams(10, 370, 96, 96));
			list.add(new ItemLayoutParams(113, 267, 198, 96));
			list.add(new ItemLayoutParams(113, 370, 198, 96));
			break;
		case COUNT_8:
			list.add(new ItemLayoutParams(10, 60, 198, 96));
			list.add(new ItemLayoutParams(213, 60, 96, 96));
			list.add(new ItemLayoutParams(10, 163, 96, 96));
			list.add(new ItemLayoutParams(112, 163, 96, 96));
			list.add(new ItemLayoutParams(214, 163, 96, 96));
			list.add(new ItemLayoutParams(10, 267, 96, 96));
			list.add(new ItemLayoutParams(10, 370, 96, 96));
			list.add(new ItemLayoutParams(112, 267, 198, 200));
			break;
		default:
			break;
		}
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				ItemLayoutParams params = list.get(i);
				LayoutParams layoutParams = new LayoutParams(params.getWidth(), params.getHeight(), params.getX(), params.getY());
				BinderView binderView = (BinderView) dataSources.get(i);
				this.addView(binderView, layoutParams);
			}
		}
	}
	
	/**
	 * 随机翻页
	 */
	public void randomFlip(){
		Random random = new Random();
        int num1 = 0, num2 = 0;
        num1 = random.nextInt(dataSources.size());
        //3个以上才随机翻动2个
        if(dataSources.size() >= 3){
        	num2 = random.nextInt(dataSources.size());
        	//保证随机数不相等
        	while (num1 == num2) {
        		num2 = random.nextInt(dataSources.size());
			}
        	dataSources.get(num2).autoFlip();
        }
        dataSources.get(num1).autoFlip();
	}
	
	public void removeAllView() {
		for (int i = 0; i < dataSources.size(); i++) {
			BinderView binderView = (BinderView) dataSources.get(i);
			binderView.recycle();
		}
		removeAllViews();
		initLoad();
	}
	
	public void allFlip() {
		for (int i = 0; i < dataSources.size(); i++) {
			dataSources.get(i).autoFlip();
		}
	}
	
	public void flip(int index) {
		if (index >= 0 && index < dataSources.size()) {
			dataSources.get(index).autoFlip();	
		}
	}
	
	public void bringTitleToFront() {
		for (int i = 0; i < dataSources.size(); i++) {
			dataSources.get(i).bringTitleToFront();
		}
	}
	
	public void setAutoFlip(boolean isAutoFlip) {
		for (int i = 0; i < dataSources.size(); i++) {
			dataSources.get(i).setAutoFlip(isAutoFlip);
		}
	}

	/**
	 * 绝对定位参数
	 * @author philit
	 *
	 */
	public class ItemLayoutParams{
		private int x, y, width, height;

		public ItemLayoutParams(int x, int y, int width, int height) {
			super();
			WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
			Display display = windowManager.getDefaultDisplay();
			int windowWidth = display.getWidth();
			float scale = windowWidth / (float)320;  //因为iphone版的是基于320的基础上设置坐标
			this.x = (int) (x*scale);
			this.y = (int) ((y-60)*scale);
			this.width = (int) (width*scale);
			this.height = (int) (height*scale);
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

		public int getWidth() {
			return width;
		}

		public void setWidth(int width) {
			this.width = width;
		}

		public int getHeight() {
			return height;
		}

		public void setHeight(int height) {
			this.height = height;
		}
	}
}
