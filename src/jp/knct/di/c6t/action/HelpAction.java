package jp.knct.di.c6t.action;

import java.util.HashMap;
import java.util.Map;

import jp.knct.di.c6t.R;
import jp.knct.di.c6t.ui.HomeActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.webkit.WebView;

public class HelpAction {
	public static void addHelpAction(final Activity activity, Menu menu, final Object key) {
		activity.getMenuInflater().inflate(R.menu.help, menu);
		menu.findItem(R.id.action_help).setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				View helpView = LayoutInflater.from(activity).inflate(R.layout.dialog_help, null);
				WebView webView = (WebView) helpView.findViewById(R.id.help_webview);
				setHelpHtml(activity, webView, key);
				new AlertDialog.Builder(activity)
						.setTitle(R.string.action_help)
						.setView(helpView)
						.create()
						.show();
				return true;
			}
		});
	}

	private static void setHelpHtml(Activity activity, WebView webView, Object key) {
		String html = gethelpText(activity, key);
		webView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);
	}

	private static String gethelpText(Context context, Object key) {
		return context.getString(sHelpTextIdMap.get(key));
	}

	private static final Map<Object, Integer> sHelpTextIdMap = new HashMap<Object, Integer>();
	static {
		sHelpTextIdMap.put(HomeActivity.class, R.string.help_text_home);
	}
}
