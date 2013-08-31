package jp.knct.di.c6t.ui.ranking;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import jp.knct.di.c6t.IntentData;
import jp.knct.di.c6t.communication.BasicClient;
import jp.knct.di.c6t.communication.BasicClient.SearchRouteParams;
import jp.knct.di.c6t.model.Route;
import jp.knct.di.c6t.ui.route.RouteActivity;
import jp.knct.di.c6t.ui.route.RoutesAdapter;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class RankingContentsFragment extends ListFragment {
	private List<Route> mRoutes;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		String sortValue = getArguments().getString(SearchRouteParams.KEY_SORT);
		new LoadingTask().execute(sortValue);

		Toast.makeText(getActivity(), "ランキング取得中...", Toast.LENGTH_SHORT).show();
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
