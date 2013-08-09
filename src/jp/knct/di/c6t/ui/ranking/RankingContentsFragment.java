package jp.knct.di.c6t.ui.ranking;

import java.util.List;

import jp.knct.di.c6t.IntentData;
import jp.knct.di.c6t.communication.BasicClient.SearchRouteParams;
import jp.knct.di.c6t.communication.DebugSharedPreferencesClient;
import jp.knct.di.c6t.model.Route;
import jp.knct.di.c6t.ui.route.RouteActivity;
import jp.knct.di.c6t.ui.route.RoutesAdapter;
import android.content.Intent;
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
		Toast.makeText(getActivity(), sortValue, Toast.LENGTH_SHORT).show();

		DebugSharedPreferencesClient client = new DebugSharedPreferencesClient(getActivity());
		mRoutes = client.getRoutes(sortValue); // FIXME

		setListAdapter(new RoutesAdapter(getActivity(), mRoutes));
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Route targetRoute = mRoutes.get(position);
		Intent intent = new Intent(getActivity(), RouteActivity.class)
				.putExtra(IntentData.EXTRA_KEY_ROUTE, targetRoute);
		startActivity(intent);
	}
}
