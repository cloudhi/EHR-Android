package com.philit.ehr.util;

import android.content.Context;;

/**
 * px与dp互相转换类
 * @author 何泽江
 * @version 1.0
 * @Date 2012-12-01
 */
public class DensityUtil {
		// TODO Auto-generated constructor stub
		/** 
	     * 根据手机的分辨率从 dp 的单位 转成为 px(像素) 
	     * @param context 上下文对象
	     * @param dpValue dp值
	     * @return 返回对应的px值
	     */  
	    public static int dip2px(Context context, float dpValue) {  
	        final float scale = context.getResources().getDisplayMetrics().density;  
	        return (int) (dpValue * scale + 0.5f);
	    }
	  
	    /** 
	     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
	     * @param context 上下文对象
	     * @param pxValue px值
	     * @return 返回对应的dp值
	     */  
	    public static int px2dip(Context context, float pxValue) {  
	        final float scale = context.getResources().getDisplayMetrics().density;  
	        return (int) (pxValue / scale + 0.5f);  
	    }
}
