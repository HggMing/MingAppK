package com.study.mingappk.common.views.customcamera;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class ImageUtil {
	/**
	 * @param b
	 * @param rotateDegree
	 * @return
	 */
	public static Bitmap getRotateBitmap(Bitmap b, float rotateDegree){
		
		try {		
		Matrix matrix = new Matrix();
		matrix.postRotate((float)rotateDegree);
		Bitmap rotaBitmap = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, false);
		if (b!=null&&b.isRecycled())
		{
			b.recycle();
			b=null;
		}
		return rotaBitmap;
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}
}
