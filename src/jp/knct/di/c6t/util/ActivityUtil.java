package jp.knct.di.c6t.util;

import android.app.Activity;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ActivityUtil {
	public static void setOnClickListener(Activity container, OnClickListener listener, int... ids) {
		for (int id : ids) {
			container.findViewById(id).setOnClickListener(listener);
		}
	}

	public static String getText(Activity container, int id) {
		TextView textView = (TextView) container.findViewById(id);
		return textView.getText().toString();
	}

	public static void setText(Activity container, int id, String text) {
		TextView textView = (TextView) container.findViewById(id);
		textView.setText(text);
	}

	private Activity mContainer;

	public ActivityUtil(Activity container) {
		mContainer = container;
	}

	public ActivityUtil setText(int id, String text) {
		ActivityUtil.setText(mContainer, id, text);
		return this;
	}

	public String getText(int id) {
		return ActivityUtil.getText(mContainer, id);
	}
}
