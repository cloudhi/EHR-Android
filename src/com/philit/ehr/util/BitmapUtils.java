package com.philit.ehr.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.ParcelFileDescriptor;


public class BitmapUtils {
    
	private static int count = 0;
    /**
     * 200px PreviewBitmap
     * @param fileName
     * @return
     */
	public static Bitmap loadPreviewBitmap(String fileName, int width, int height) 
	{
	    try {
    		BitmapFactory.Options options = new BitmapFactory.Options();
    		options.inJustDecodeBounds = false;
    		options.inSampleSize = 1;
    		File file = new File(fileName);
    		if (!file.exists()) {
    		    return null;
    		}
    		if (file.length() > 4 * 1024 * 1024) {
    			options.inSampleSize = 4;
    		}
    		else if (file.length() > 1024 * 1024)
    		{
    			options.inSampleSize = 2;
    		}
    		Bitmap bm = BitmapFactory.decodeFile(fileName, options);
    		if (bm != null)
    		{
    			Bitmap resizebm = resizePreviewBitmap(bm, width, height);
    			bm.recycle();
    			return resizebm;
    		}
    		return null;
	    }
	    catch (OutOfMemoryError e) {
	        return null;
	    }
	}
	
	public static Bitmap resizePreviewBitmap(Bitmap bm, int width, int height)
	{
	    try {
	        if (bm != null) {
	            Bitmap resizebm = Bitmap.createScaledBitmap(bm, width, height, true);
	            return resizebm;
	        }
	        else {
	            return null;
	        }
	    }
	    catch (OutOfMemoryError e) {
            return null;
        }
	}
	
	public static Bitmap loadBitmap(InputStream is)
	{
		try {
            if (is != null && is.available() > 0) {
            	return BitmapFactory.decodeStream(is);
            }
            else {
                return null;
            }
        } catch (IOException e) {
            return null;
        } catch (OutOfMemoryError e) {
            return null;
        }
	}
    
	public static Bitmap loadBitmap(Context c, String fileName) {
		ParcelFileDescriptor pfd;
		try {
			pfd = c.getContentResolver().openFileDescriptor(Uri.parse("file://" + fileName), "r");
		} catch (IOException ex) {
			return null;
		}
		java.io.FileDescriptor fd = pfd.getFileDescriptor();
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inTempStorage = new byte[10*1024]; 
		// 先指定原始大小
		options.inSampleSize = 1;
		// 只进行大小判断
		options.inJustDecodeBounds = true;
		// 调用此方法得到options得到图片的大小
		BitmapFactory.decodeFileDescriptor(fd, null, options);
		// 我们的目标是在400pixel的画面上显示。
		//所以需要调用computeSampleSize得到图片缩放的比例
		//options.inSampleSize = computeSampleSize(options, 400);
		// OK,我们得到了缩放的比例，现在开始正式读入BitMap数据
		options.inJustDecodeBounds = false;
		options.inDither = false;
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;

		// 根据options参数，减少所需要的内存
		Bitmap sourceBitmap = BitmapFactory.decodeFileDescriptor(fd, null,
				options);
		if (count < 4) {
			count++;
		} else {
			count = 0;
			System.gc();
		}
		return sourceBitmap;
	} 
	
	// 这个函数会对图片的大小进行判断，并得到合适的缩放比例，比如2即1/2,3即1/3
	public static int computeSampleSize(BitmapFactory.Options options,
			int target) {
		int w = options.outWidth;
		int h = options.outHeight;
		int candidateW = w / target;
		int candidateH = h / target;
		int candidate = Math.max(candidateW, candidateH);
		if (candidate == 0)
			return 1;
//		if (candidate > 1) {
//			if ((w > target) && (w / candidate) < target)
//				candidate -= 1;
//		}
//		if (candidate > 1) {
//			if ((h > target) && (h / candidate) < target)
//				candidate -= 1;
//		}
		return candidate;
	}

    /** 获取图像的宽高**/
    public static int[] getImageWH(String path){
       int[] wh = {-1, -1};
       if(path == null){
           return wh;
       }
       File file = new File(path);
       if(file.exists() && !file.isDirectory()){
           try {
              BitmapFactory.Options options = new BitmapFactory.Options();
              options.inJustDecodeBounds = true;
              InputStream is = new FileInputStream(path);
              BitmapFactory.decodeStream(is, null, options);
              wh[0] = options.outWidth;
              wh[1] = options.outHeight;
           } catch (Exception e) {
           }
       }
       return wh;
    }
    
    /**
     * 保存bitmap图片
     * @param bitmap
     * @param bitName
     * @return 是否保存成功
     */
	public static boolean saveBitmap(Bitmap bitmap, String bitName){
		try {
			File temp = File.createTempFile("temp", ".png", new File(StringUtil.getNameDelLastPath(bitName)));
			FileOutputStream fOut = null;
			try {
				fOut = new FileOutputStream(temp);
			} catch (FileNotFoundException e) {
			}
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
			fOut.flush();
			fOut.close();
			if(temp.exists())
			{
				File f = new File(bitName);
				if (f.exists())
				{
					f.delete();
				}
				FileUtil.moveFile(temp.getAbsolutePath(), bitName);
			}
			
			return true;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public static Bitmap drawableToBitmap(Drawable drawable) {
	    Bitmap bitmap = Bitmap.createBitmap(   
                        drawable.getIntrinsicWidth(),   
                        drawable.getIntrinsicHeight(),   
                        drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888   
                                : Bitmap.Config.RGB_565);   
        Canvas canvas = new Canvas(bitmap);   
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),   
                drawable.getIntrinsicHeight());   
        drawable.draw(canvas);   
        return bitmap;  
	}
}
