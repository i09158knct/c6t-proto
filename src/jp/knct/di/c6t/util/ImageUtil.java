package jp.knct.di.c6t.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;

public class ImageUtil {
	public static Bitmap decodeBitmap(String filePath, int scale) {
		Options options = new BitmapFactory.Options();
		options.inSampleSize = scale;
		options.inPurgeable = true;
		return BitmapFactory.decodeFile(filePath, options);
	}
}
