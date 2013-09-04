package jp.knct.di.c6t.ui.route;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.knct.di.c6t.IntentData;
import jp.knct.di.c6t.R;
import jp.knct.di.c6t.communication.BasicClient;
import jp.knct.di.c6t.model.Route;
import jp.knct.di.c6t.util.ActivityUtil;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

public class SearchRouteActivity extends ListActivity implements OnClickListener {
	private static final String[] OPTION_SCOPE_LABELS = new String[] {
			"ルート名",
			"作成ユーザ名",
			"説明文",
	};
	private static final String[] OPTION_SCOPE_VALUES = new String[] {
			BasicClient.SearchRouteParams.SCOPE_TITLE,
			BasicClient.SearchRouteParams.SCOPE_USER_NAME,
			BasicClient.SearchRouteParams.SCOPE_DESCRIPTION,
	};

	private static final String[] OPTION_SORT_LABELS = new String[] {
			"作成の新しい順/古い順",
			"試遊回数の多い順/少ない順",
			"達成回数の多い順/少ない順",
	};
	private static final String[] OPTION_SORT_VALUES = new String[] {
			BasicClient.SearchRouteParams.SORT_NEW,
			BasicClient.SearchRouteParams.SORT_PLAYED_COUNT,
			BasicClient.SearchRouteParams.SORT_ACHIEVEMENT_COUNT,
	};

	private List<Route> mRoutes;
	private Map<String, String> mScopeOptionMap;
	private Map<String, String> mSortOptionMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_route);

		setupSearchScopeSpinner();
		setupSearchSortSpinner();

		ActivityUtil.setOnClickListener(this, this, new int[] {
				R.id.search_route_search,
		});
	}

	private void setupSearchScopeSpinner() {
		SpinnerAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, OPTION_SCOPE_LABELS);
		mScopeOptionMap = new HashMap<String, String>();
		for (int i = 0; i < OPTION_SCOPE_LABELS.length; i++) {
			mScopeOptionMap.put(OPTION_SCOPE_LABELS[i], OPTION_SCOPE_VALUES[i]);
		}

		Spinner spinner = (Spinner) findViewById(R.id.search_route_scope);
		spinner.setAdapter(adapter);
	}

	private void setupSearchSortSpinner() {
		SpinnerAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, OPTION_SORT_LABELS);
		mSortOptionMap = new HashMap<String, String>();
		for (int i = 0; i < OPTION_SORT_LABELS.length; i++) {
			mSortOptionMap.put(OPTION_SORT_LABELS[i], OPTION_SORT_VALUES[i]);
		}

		Spinner spinner = (Spinner) findViewById(R.id.search_route_sort);
		spinner.setAdapter(adapter);
	}

	protected void onListItemClick(ListView l, View v, int position, long id) {
		Route targetRoute = mRoutes.get(position);
		Intent intent = new Intent(this, RouteActivity.class)
				.putExtra(IntentData.EXTRA_KEY_ROUTE, targetRoute);
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.search_route_search:
			// TODO: display alert dialog
			new LoadingTask().execute();
			break;

		default:
			break;
		}
	}

	private String getSearchQuery() {
		return ActivityUtil.getText(this, R.id.search_route_name);
	}

	private String getSelectedScopeValue() {
		String selectedSpinnerLabel = (String) ((Spinner) findViewById(R.id.search_route_scope)).getSelectedItem();
		return mScopeOptionMap.get(selectedSpinnerLabel);
	}

	private String getSelectedSortValue() {
		String selectedSpinnerLabel = (String) ((Spinner) findViewById(R.id.search_route_sort)).getSelectedItem();
		return mSortOptionMap.get(selectedSpinnerLabel);
	}

	private String getSelectedOrderValue() {
		int selectedOrderId = ((RadioGroup) findViewById(R.id.search_route_order)).getCheckedRadioButtonId();
		switch (selectedOrderId) {
		case R.id.search_route_order_desc:
			return BasicClient.SearchRouteParams.ORDER_DESC;

		case R.id.search_route_order_asc:
			return BasicClient.SearchRouteParams.ORDER_ASC;

		default:
			throw new AssertionError("Unknown ID");
		}
	}

	private void setRoutes(List<Route> routes) {
		RoutesAdapter adapter = new RoutesAdapter(this, routes);
		setListAdapter(adapter);
	}

	private class LoadingTask extends AsyncTask<Void, Void, List<Route>> {
		@Override
		protected List<Route> doInBackground(Void... params) {
			BasicClient client = new BasicClient();
			List<Route> routes = null;
			try {
				routes = client.getRoutes(
						getSelectedScopeValue(),
						getSearchQuery(),
						getSelectedSortValue(),
						getSelectedOrderValue());
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
			setRoutes(mRoutes);

			Toast.makeText(getApplicationContext(), "ヒット数: " + routes.size(), Toast.LENGTH_SHORT).show();
		}
	}
}
