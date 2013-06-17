package com.philit.ehr.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;


public class FileUtils {

	private static FileUtils mInstance;
	
	/**
	 * 
	 * @param isPathIncludeSDCardRoot 传递过来的path是否包括sdcard根目录
	 * @return
	 */
	public static FileUtils getInstance(boolean isPathIncludeSDCardRoot) {
		if (mInstance == null) {
			synchronized (FileUtils.class) {
				if (mInstance == null) {
					mInstance = new FileUtils(isPathIncludeSDCardRoot);
				}
			}
		}
		return mInstance;
	}
	
	private String SDCardRoot;
	
	/**
	 * 
	 * @param isPathIncludeSDCardRoot 传递过来的path是否包括sdcard根目录
	 * @return
	 */
	public FileUtils(boolean isPathIncludeSDCardRoot) {
		if (!isPathIncludeSDCardRoot) {
			// 得到当前外部存储设备的目录
			SDCardRoot = Environment.getExternalStorageDirectory()
					.getAbsolutePath()
					+ File.separator;
		}else {
			SDCardRoot = "";
		}
	}

	/**
	 * 在SD卡上创建文件
	 * 
	 * @throws IOException
	 */
	public File createFileInSDCard(String fileName, String dir)
			throws IOException {
		File file = new File(SDCardRoot + dir + File.separator + fileName);
		System.out.println("file---->" + file);
		file.createNewFile();
		return file;
	}

	/**
	 * 在SD卡上创建目录
	 * 
	 * @param dirName
	 */
	public File creatSDDir(String dir) {
		File dirFile = new File(SDCardRoot + dir + File.separator);
		System.out.println(dirFile.mkdirs());
		return dirFile;
	}

	/**
	 * 判断SD卡上的文件夹是否存在
	 */
	public boolean isFileExist(String fileName, String path) {
		File file = new File(SDCardRoot + path + File.separator + fileName);
		return file.exists();
	}

	/**
	 * 将一个InputStream里面的数据写入到SD卡中
	 */
	public File write2SDFromInput(String path, String fileName,
			InputStream input) {

		File file = null;
		OutputStream output = null;
		try {
			creatSDDir(path);
			file = createFileInSDCard(fileName, path);
			output = new FileOutputStream(file);
			byte buffer[] = new byte[4 * 1024];
			int temp;
			while ((temp = input.read(buffer)) != -1) {
				output.write(buffer, 0, temp);
			}
			output.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return file;
	}
	
	/**
	 * 将一个InputStream里面的数据写入String中
	 */
	public String write2StringFromInput(InputStream input) {

		ByteArrayOutputStream output = new ByteArrayOutputStream();
		String datas = "";
		try {
			byte buffer[] = new byte[4 * 1024];
			int temp;
			while ((temp = input.read(buffer)) != -1) {
				output.write(buffer, 0, temp);
			}
			output.flush();
			datas = output.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
				input.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return datas;
	}
	
	 //android获取一个用于打开PPT文件的intent 
	  public static Intent getPptFileIntent(String param) 
	  { 
		  Intent intent = new Intent("android.intent.action.VIEW"); 
		  intent.addCategory("android.intent.category.DEFAULT"); 
		  intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
		  Uri uri = Uri.fromFile(new File(param )); 
		  intent.setDataAndType(uri, "application/vnd.ms-powerpoint"); 
		  return intent; 
	  } 
	 
	 //android获取一个用于打开Excel文件的intent 
	  public static Intent getExcelFileIntent( String param ) 
	  { 
		    Intent intent = new Intent("android.intent.action.VIEW"); 
		    intent.addCategory("android.intent.category.DEFAULT"); 
		    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
		    Uri uri = Uri.fromFile(new File(param )); 
		    intent.setDataAndType(uri, "application/vnd.ms-excel"); 
		    return intent; 
	  } 
	 
	  //android获取一个用于打开Word文件的intent 
	  public static Intent getWordFileIntent( String param ) 
	  { 
		    Intent intent = new Intent("android.intent.action.VIEW"); 
		    intent.addCategory("android.intent.category.DEFAULT"); 
		    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
		    Uri uri = Uri.fromFile(new File(param )); 
		    intent.setDataAndType(uri, "application/msword"); 
		    return intent; 
	  } 
	 
	//android获取一个用于打开CHM文件的intent 
	  public static Intent getChmFileIntent( String param ) 
	  { 
		    Intent intent = new Intent("android.intent.action.VIEW"); 
		    intent.addCategory("android.intent.category.DEFAULT"); 
		    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
		    Uri uri = Uri.fromFile(new File(param )); 
		    intent.setDataAndType(uri, "application/x-chm"); 
		    return intent; 
	  } 
	 
	//android获取一个用于打开文本文件的intent 
	public static Intent getTextFileIntent( String param, boolean paramBoolean) 
	{ 
		Intent intent = new Intent("android.intent.action.VIEW"); 
		intent.addCategory("android.intent.category.DEFAULT"); 
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
		if (paramBoolean) 
		{ 
			Uri uri1 = Uri.parse(param); 
			intent.setDataAndType(uri1, "text/plain"); 
		} 
		else
		{ 
			Uri uri2 = Uri.fromFile(new File(param)); 
			intent.setDataAndType(uri2, "text/plain"); 
		} 
		return intent; 
	} 
	 
	//android获取一个用于打开PDF文件的intent 
	  public static Intent getPdfFileIntent( String param ) 
	  { 
		    Intent intent = new Intent("android.intent.action.VIEW"); 
		    intent.addCategory("android.intent.category.DEFAULT"); 
		    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
		    Uri uri = Uri.fromFile(new File(param )); 
		    intent.setDataAndType(uri, "application/pdf"); 
		    return intent; 
	  }
	 
}
