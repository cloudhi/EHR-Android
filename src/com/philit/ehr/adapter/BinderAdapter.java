/*
   Copyright 2012 Harri Smatt

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

package com.philit.ehr.adapter;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.philit.ehr.R;
import com.philit.ehr.view.MyTextView;

/**
 * Simple View provider interface.
 */
public class BinderAdapter{

	private List<View> list;
	private List<Bitmap> bitmaps;
	
	@SuppressWarnings("deprecation")
	public BinderAdapter(final Context context, final String title, List<Bitmap> bitmaps) {
		list = new ArrayList<View>();
		this.bitmaps = bitmaps;
		LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		for (int i = 0; i < bitmaps.size(); i++) {
			TextView textView = new MyTextView(context, title);
			//textView.setText(title);
			textView.setBackgroundColor(context.getResources().getColor(R.color.deep_red));
			textView.setTextColor(Color.WHITE);
			
			ImageView imageView = new ImageView(context);
			//imageView.setBackgroundDrawable(ImageTools.bitmapToDrawable(bitmaps.get(i)));
		    imageView.setImageBitmap(bitmaps.get(i));
		    imageView.setLayoutParams(params);
		    imageView.setBackgroundColor(context.getResources().getColor(R.color.bg_myrecord));
		    list.add(textView);
		    list.add(imageView);
		}
	}

	public List<View> getList() {
		return list;
	}

	public void setList(List<View> list) {
		this.list = list;
	}

	public List<Bitmap> getBitmaps() {
		return bitmaps;
	}

	public void setBitmaps(List<Bitmap> bitmaps) {
		this.bitmaps = bitmaps;
	}
}
