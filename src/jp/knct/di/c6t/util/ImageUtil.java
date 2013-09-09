package jp.knct.di.c6t.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import jp.knct.di.c6t.R;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

public class ImageUtil {
	public static Bitmap decodeBitmap(String filePath, int scale) {
		Options options = new BitmapFactory.Options();
		options.inSampleSize = scale;
		options.inPurgeable = true;
		return BitmapFactory.decodeFile(filePath, options);
	}

	public static File createTempFile() throws IOException {
		return File.createTempFile("c6t__", ".tmp", Environment.getExternalStorageDirectory());
	}

	public static Uri createTempFileUri() throws IOException {
		return Uri.parse(createTempFile().toURI().toString());
	}

	public static Intent createCaptureImageIntent(Uri destUri) {
		return new Intent(MediaStore.ACTION_IMAGE_CAPTURE)
				.putExtra(MediaStore.EXTRA_OUTPUT, destUri);
	}

	public static String debugGetSampleImagePath(Context context) {
		final String SAMPLE_IMAGE_FILE_NAME = "sample.jpg";
		File sampleFile = context.getFileStreamPath(SAMPLE_IMAGE_FILE_NAME);
		if (!sampleFile.exists()) {
			InputStream is = context.getResources().openRawResource(R.raw.image_default);
			FileOutputStream fos;
			try {
				fos = new FileOutputStream(sampleFile);
				copy(is, fos);
			}
			catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return sampleFile.getAbsolutePath();
	}

	private static void copy(InputStream is, FileOutputStream fos) throws IOException {
		byte[] buff = new byte[1024];
		int read = 0;

		try {
			while ((read = is.read(buff)) > 0) {
				fos.write(buff, 0, read);
			}
		}
		finally {
			is.close();
			fos.close();
		}

	}
}
