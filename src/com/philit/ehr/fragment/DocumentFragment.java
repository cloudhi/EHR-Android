package com.philit.ehr.fragment;

import java.io.File;
import java.util.List;

import com.philit.ehr.R;
import com.philit.ehr.R.id;
import com.philit.ehr.db.Database;
import com.philit.ehr.db.DocumentData;
import com.philit.ehr.define.Define;
import com.philit.ehr.view.DocumentViewPager;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DocumentFragment extends Fragment{

	private int mPos = -1;
	private View mainView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (mPos == -1 && savedInstanceState != null)
			mPos = savedInstanceState.getInt("mPos");
		mainView = inflater.inflate(R.layout.document_frame, null);
		DocumentViewPager documentViewPager = (DocumentViewPager)mainView.findViewById(R.id.documentViewPager);
		List<DocumentData> documentDatas = Database.getInstance().getAllDocumentList();
		if (documentDatas.size() == 0) {
			for (int i = 0; i < 8; i++) {
				String SDCardRoot = Environment.getExternalStorageDirectory()
						.getAbsolutePath()
						+ File.separator;
				DocumentData documentData = new DocumentData(i,"健康知识宣传文档" + i +".doc", SDCardRoot + Define.DOCUMENT_PATH + "test.doc",1, "2013-06-12 10:23:11");
				Database.getInstance().createDocument(documentData);
			}
			for (int i = 8; i < 16; i++) {
				String SDCardRoot = Environment.getExternalStorageDirectory()
						.getAbsolutePath()
						+ File.separator;
				DocumentData documentData2 = new DocumentData(i*2,"健康知识宣传文档" + i +".pdf", SDCardRoot + Define.DOCUMENT_PATH + "网上挂号系统.pdf",1, "2013-06-12 10:23:11");
				Database.getInstance().createDocument(documentData2);
			}
			for (int i = 16; i < 24; i++) {
				String SDCardRoot = Environment.getExternalStorageDirectory()
						.getAbsolutePath()
						+ File.separator;
				DocumentData documentData3 = new DocumentData(i*3,"健康知识宣传文档" + i +".txt", SDCardRoot + Define.DOCUMENT_PATH + "IP.txt",1, "2013-06-12 10:23:11");
				Database.getInstance().createDocument(documentData3);
			}
		}
		documentViewPager.initMainViewPager();
		return mainView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("mPos", mPos);
	}
}
