package com.easemob.chatuidemo.utils.img;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.util.AttributeSet;

//圆角图片
public class ImageYuanJiao extends MaskedImage {
public static final int YUANJIAO=10;	
	
	public ImageYuanJiao(Context paramContext) {
		super(paramContext);
	}

	public ImageYuanJiao(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
	}

	public ImageYuanJiao(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
		super(paramContext, paramAttributeSet, paramInt);
	}

	public Bitmap createMask() {
		int i = getWidth();
		int j = getHeight();
		Bitmap.Config localConfig = Bitmap.Config.ARGB_8888;
		Bitmap localBitmap = Bitmap.createBitmap(i, j, localConfig);
		
		Canvas localCanvas = new Canvas(localBitmap);
		
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, i, j);
		final RectF rectF = new RectF(rect);
 
		paint.setColor(color);
		paint.setAntiAlias(true);
		localCanvas.drawARGB(0, 0, 0, 0);
		localCanvas.drawRoundRect(rectF, YUANJIAO, YUANJIAO, paint);
 
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		localCanvas.drawBitmap(localBitmap, rect, rect, paint);
		return localBitmap;
	}
	
	
	
}
