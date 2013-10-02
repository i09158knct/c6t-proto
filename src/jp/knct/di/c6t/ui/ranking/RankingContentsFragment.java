package jp.knct.di.c6t.ui.ranking;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import jp.knct.di.c6t.IntentData;
import jp.knct.di.c6t.R;
import jp.knct.di.c6t.communication.BasicClient;
import jp.knct.di.c6t.communication.BasicClient.SearchRouteParams;
import jp.knct.di.c6t.model.Route;
import jp.knct.di.c6t.ui.route.RouteActivity;
import jp.knct.di.c6t.ui.route.RoutesAdapter;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class RankingContentsFragment extends ListFragment {
	private List<Route> mRoutes;
	private String mSortValue;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mSortValue = getArguments().getString(SearchRouteParams.KEY_SORT);
		new LoadingTask().execute(mSortValue);
		Toast.makeText(getActivity(), "ランキング取得中...", Toast.LENGTH_SHORT).show();
		setHasOptionsMenu(true);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.order_chooser, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_choose_order:
			new AlertDialog.Builder(getActivity())
					.setTitle(R.string.action_choose_order)
					.setItems(new String[] { "降順", "昇順" }, new OnClickListener() {
						private static final int DESC = 0;

						@Override
						public void onClick(DialogInterface dialog, final int which) {
							Collections.sort(mRoutes, new Comparator<Route>() {
								@Override
								public int compare(Route a, Route b) {
									if (mSortValue.equals(SearchRouteParams.SORT_ACHIEVEMENT_COUNT)) {
										if (which == DESC) {
											return b.getAchievedCount() - a.getAchievedCount();
										}
										else {
											return a.getAchievedCount() - b.getAchievedCount();
										}
									}
									else {
										if (which == DESC) {
											return b.getPlayedCount() - a.getPlayedCount();
										}
										else {
											return a.getPlayedCount() - b.getPlayedCount();
										}
									}
								}
							});
							((BaseAdapter) getListAdapter()).notifyDataSetChanged();
						}
					})
					.create()
					.show();

			return false;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Route targetRoute = mRoutes.get(position);
		Intent intent = new Intent(getActivity(), RouteActivity.class)
				.putExtra(IntentData.EXTRA_KEY_ROUTE, targetRoute);
		startActivity(intent);
	}

	private class LoadingTask extends AsyncTask<String, Void, List<Route>> {

		@Override
		protected List<Route> doInBackground(String... sortValue) {
			BasicClient client = new BasicClient();
			List<Route> routes = null;
			try {
				routes = client.getRoutes("", "", sortValue[0], SearchRouteParams.ORDER_DESC);
			}
			catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return routes;
		}

		@Override
		protected void onPostExecute(List<Route> routes) {
			mRoutes = routes;
			RoutesAdapter adapter = new RoutesAdapter(getActivity(), mRoutes);
			setListAdapter(adapter);

			if (routes.size() == 0) {
				Toast.makeText(getActivity(), "ルートが見つかりません", Toast.LENGTH_SHORT).show();
			}
		}
	}
}
