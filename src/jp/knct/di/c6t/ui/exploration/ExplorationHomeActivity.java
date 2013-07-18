package jp.knct.di.c6t.ui.exploration;

import java.util.List;

import jp.knct.di.c6t.IntentData;
import jp.knct.di.c6t.R;
import jp.knct.di.c6t.communication.Client;
import jp.knct.di.c6t.communication.DebugSharedPreferencesClient;
import jp.knct.di.c6t.model.Exploration;
import jp.knct.di.c6t.ui.route.SearchRouteActivity;
import jp.knct.di.c6t.util.ActivityUtil;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

public class ExplorationHomeActivity extends ListActivity implements OnClickListener {
	private List<Exploration> mExplorations;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exploration_home);

		ActivityUtil.setOnClickListener(this, this, new int[] {
				R.id.exploration_home_create_new_exploration,
				R.id.exploration_home_join_exploration,
		});

		Client client = new DebugSharedPreferencesClient(this);
		mExplorations = client.getExplorations(client.getMyUserData());
		ExplorationsAdapter adapter = new ExplorationsAdapter(this, mExplorations);
		setListAdapter(adapter);
	}

	protected void onListItemClick(ListView l, View v, int position, long id) {
		Exploration targetExploration = mExplorations.get(position);
		Intent intent = new Intent(this, ExplorationDetailActivity.class)
				.putExtra(IntentData.EXTRA_KEY_EXPLORATION, targetExploration);
		startActivity(intent);
		finish();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.exploration_home_create_new_exploration:
			Intent intent = new Intent(this, SearchRouteActivity.class);
			startActivity(intent);
			break;

		case R.id.exploration_home_join_exploration:
			// TODO
			// Intent intent = new Intent(this, .class);
			// startActivity(intent);
			break;

		default:
			break;
		}
	}
}
