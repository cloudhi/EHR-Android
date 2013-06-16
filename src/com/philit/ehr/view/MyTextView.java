/*
Copyright 2012 Aphid Mobile

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

package com.philit.ehr.view;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

public class MyTextView extends TextView{
	private String text;

	public MyTextView(Context context, String text) {
		super(context);
		setNumber(text);
		setTextColor(Color.BLACK);
		setBackgroundColor(Color.WHITE);
		setGravity(Gravity.CENTER);
	}

	public String getNumber() {
		return text;
	}

	public void setNumber(String text) {
		this.text = text;
		setText(text);
	}

	@Override
	public String toString() {
		return "MyTextView: " + text;
	}
}
