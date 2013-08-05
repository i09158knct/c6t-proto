package jp.knct.di.c6t.ui.exploration;

import java.util.List;

import jp.knct.di.c6t.IntentData;
import jp.knct.di.c6t.R;
import jp.knct.di.c6t.communication.Client;
import jp.knct.di.c6t.communication.DebugSharedPreferencesClient;
import jp.knct.di.c6t.model.Exploration;
import jp.knct.di.c6t.util.ActivityUtil;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

public class SearchExplorationActivity extends ListActivity implements OnClickListener {
	private List<Exploration> mExplorations;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_exploration);

		ActivityUtil.setOnClickListener(this, this, new int[] {
				R.id.search_exploration_search,
		});
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
			mExplorations = fetchExplorations();
			setExplorations(mExplorations);
			break;

		default:
			break;
		}
	}

	// TODO
	private List<Exploration> fetchExplorations() {
		String searchText = ActivityUtil.getText(this, R.id.search_exploration_name);
		Client client = new DebugSharedPreferencesClient(this);
		return client.getExplorations(searchText);
	}

	private void setExplorations(List<Exploration> explorations) {
		ExplorationsAdapter adapter = new ExplorationsAdapter(this, explorations);
		setListAdapter(adapter);
	}
}
