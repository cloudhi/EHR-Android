package com.philit.ehr.db;

import android.R.integer;

public class MenuData {
	
	private int color;
	private String title;
	
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public MenuData(int color, String title) {
		super();
		this.color = color;
		this.title = title;
	}
	@Override
	public String toString() {
		return "MenuData [color=" + color + ", title=" + title + "]";
	}
}
