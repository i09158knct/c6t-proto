package jp.knct.di.c6t.util;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ActivityUtil {

	public static class ActivityLink {
		public final int id;
		public final Class<? extends Activity> target;

		public ActivityLink(int id, Class<? extends Activity> target) {
			this.id = id;
			this.target = target;
		}
	}

	public static void setJumper(final Activity activity, ActivityLink... activityLinks) {
		for (final ActivityLink activityLink : activityLinks) {
			activity.findViewById(activityLink.id).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(activity, activityLink.target);
					activity.startActivity(intent);
				}
			});
		}
	}

	public static void setOnClickListener(Activity container, OnClickListener listener, int... ids) {
		for (int id : ids) {
			container.findViewById(id).setOnClickListener(listener);
		}
	}

	public static String getText(Activity container, int id) {
		TextView textView = (TextView) container.findViewById(id);
		return textView.getText().toString();
	}

	public static String getText(View container, int id) {
		TextView textView = (TextView) container.findViewById(id);
		return textView.getText().toString();
	}

	public static void setText(Activity container, int id, String text) {
		TextView textView = (TextView) container.findViewById(id);
		textView.setText(text);
	}

	public static void setText(View container, int id, String text) {
		TextView textView = (TextView) container.findViewById(id);
		textView.setText(text);
	}

	private Activity mContainerActivity;
	private View mContainerView;

	public ActivityUtil(Activity container) {
		mContainerActivity = container;
	}

	public ActivityUtil(View container) {
		mContainerView = container;
	}

	public ActivityUtil setText(int id, String text) {
		if (mContainerActivity != null) {
			ActivityUtil.setText(mContainerActivity, id, text);
		}
		else if (mContainerView != null) {
			ActivityUtil.setText(mContainerView, id, text);
		}
		else {
			throw new AssertionError("error");
		}
		return this;
	}

	public String getText(int id) {
		if (mContainerActivity != null) {
			return ActivityUtil.getText(mContainerActivity, id);
		}
		else if (mContainerView != null) {
			return ActivityUtil.getText(mContainerView, id);
		}
		else {
			throw new AssertionError("error");
		}
	}
}
