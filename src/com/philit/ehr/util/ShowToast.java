package com.philit.ehr.util;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

public class ShowToast {
	public static void show(final Activity activity,final String text) {
		activity.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(activity, text,
						Toast.LENGTH_LONG).show();
			}

		});
	}
	
	public static void show(final Activity activity,final int textId) {
		activity.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(activity, activity.getResources().getString(textId),
						Toast.LENGTH_LONG).show();
			}

		});
	}
	
	public static void show(final Context context,final String text) {
		Activity activity = (Activity) context;
		activity.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(context, text,
						Toast.LENGTH_LONG).show();
			}
		});
	}
	
	public static void show(final Context context,final int textId) {
		Activity activity = (Activity) context;
		activity.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(context, context.getResources().getString(textId),
						Toast.LENGTH_LONG).show();
			}

		});
	}
}
