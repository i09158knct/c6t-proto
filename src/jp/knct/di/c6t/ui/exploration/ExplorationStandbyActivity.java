package jp.knct.di.c6t.ui.exploration;

import jp.knct.di.c6t.IntentData;
import jp.knct.di.c6t.R;
import jp.knct.di.c6t.communication.Client;
import jp.knct.di.c6t.communication.DebugSharedPreferencesClient;
import jp.knct.di.c6t.model.Exploration;
import jp.knct.di.c6t.model.User;
import jp.knct.di.c6t.util.ActivityUtil;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;

public class ExplorationStandbyActivity extends ListActivity implements OnClickListener {
	private Exploration mExploration;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exploration_standby);

		mExploration = getIntent().getParcelableExtra(IntentData.EXTRA_KEY_EXPLORATION);

		ActivityUtil.setOnClickListener(this, this, new int[] {
				R.id.exploration_standby_start,
		});

		Client client = new DebugSharedPreferencesClient(this);
		client.joinExploration(mExploration, client.getMyUserData());
		mExploration = client.refreshExplorationInfo(mExploration);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		for (User user : mExploration.getMembers()) {
			adapter.add(user.getName());
		}
		setListAdapter(adapter);

		// TODO: continuous update process
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.exploration_standby_start:
			// TODO:
			break;

		default:
			break;
		}

	}

}
