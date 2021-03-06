package jp.knct.di.c6t.ui.exploration;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.knct.di.c6t.IntentData;
import jp.knct.di.c6t.R;
import jp.knct.di.c6t.communication.BasicClient;
import jp.knct.di.c6t.model.Exploration;
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

public class SearchExplorationActivity extends ListActivity implements OnClickListener {
	private static final String[] OPTION_LABELS = new String[] {
			"ルート名",
			"作成ユーザ名",
	};
	private static final String[] OPTION_VALUES = new String[] {
			BasicClient.SearchExplorationParams.SCOPE_ROUTE_TITLE,
			BasicClient.SearchExplorationParams.SCOPE_USER_NAME,
	};
	private List<Exploration> mExplorations;
	private Map<String, String> mOptionMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_exploration);

		setupSearchScopeSpinner();

		ActivityUtil.setOnClickListener(this, this, new int[] {
				R.id.search_exploration_search,
		});
	}

	private void setupSearchScopeSpinner() {
		SpinnerAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, OPTION_LABELS);
		mOptionMap = new HashMap<String, String>();
		for (int i = 0; i < OPTION_LABELS.length; i++) {
			mOptionMap.put(OPTION_LABELS[i], OPTION_VALUES[i]);
		}

		Spinner spinner = (Spinner) findViewById(R.id.search_exploration_scope);
		spinner.setAdapter(adapter);

	}

	protected void onListItemClick(ListView l, View v, int position, long id) {
		Exploration targetExploration = mExplorations.get(position);
		Intent intent = new Intent(this, ExplorationDetailActivity.class)
				.putExtra(IntentData.EXTRA_KEY_EXPLORATION, targetExploration);
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.search_exploration_search:
			// TODO: display alert dialog
			new LoadingTask().execute();
			break;

		default:
			break;
		}
	}

	private String getSearchQuery() {
		return ActivityUtil.getText(this, R.id.search_exploration_name);
	}

	private String getSelectedScopeValue() {
		String selectedSpinnerLabel = (String) ((Spinner) findViewById(R.id.search_exploration_scope)).getSelectedItem();
		return mOptionMap.get(selectedSpinnerLabel);
	}

	private String getSelectedOrderValue() {
		int selectedOrderId = ((RadioGroup) findViewById(R.id.search_exploration_order)).getCheckedRadioButtonId();
		switch (selectedOrderId) {
		case R.id.search_exploration_order_desc:
			return BasicClient.SearchExplorationParams.ORDER_DESC;

		case R.id.search_exploration_order_asc:
			return BasicClient.SearchExplorationParams.ORDER_ASC;

		default:
			throw new AssertionError("Unknown ID");
		}
	}

	private void setExplorations(List<Exploration> explorations) {
		ExplorationsAdapter adapter = new ExplorationsAdapter(this, explorations);
		setListAdapter(adapter);
	}

	private class LoadingTask extends AsyncTask<Void, Void, List<Exploration>> {
		@Override
		protected List<Exploration> doInBackground(Void... params) {
			BasicClient client = new BasicClient();
			List<Exploration> explorations = null;
			try {
				explorations = client.getExplorations(
						getSelectedScopeValue(),
						getSearchQuery(),
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

			return explorations;
		}

		@Override
		protected void onPostExecute(List<Exploration> explorations) {
			mExplorations = explorations;
			setExplorations(mExplorations);

			Toast.makeText(getApplicationContext(), "ヒット数: " + explorations.size(), Toast.LENGTH_SHORT).show();
		}
	}
}
