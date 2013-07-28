package jp.knct.di.c6t.util;

import java.io.File;
import java.io.IOException;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.provider.MediaStore;

public class ImageUtil {
	public static Bitmap decodeBitmap(String filePath, int scale) {
		Options options = new BitmapFactory.Options();
		options.inSampleSize = scale;
		options.inPurgeable = true;
		return BitmapFactory.decodeFile(filePath, options);
	}

	public static File createTempFile() throws IOException {
		return File.createTempFile("c6t__", ".tmp");
	}

	public static Uri createTempFileUri() throws IOException {
		return Uri.parse(createTempFile().toURI().toString());
	}

	public static Intent createCaptureImageIntent(Uri destUri) {
		return new Intent(MediaStore.ACTION_IMAGE_CAPTURE)
				.putExtra(MediaStore.EXTRA_OUTPUT, destUri);
	}
}
