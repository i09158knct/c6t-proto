package jp.knct.di.c6t.ui.schedule;

import java.util.List;

import jp.knct.di.c6t.IntentData;
import jp.knct.di.c6t.R;
import jp.knct.di.c6t.communication.DebugSharedPreferencesClient;
import jp.knct.di.c6t.model.Exploration;
import jp.knct.di.c6t.ui.exploration.ExplorationDetailActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MyScheduleActivity extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_schedule);

		ScheduleFragment schedule = (ScheduleFragment) getFragmentManager().findFragmentById(R.id.my_schedule);

		List<Exploration> explorations = new DebugSharedPreferencesClient(this).getExplorations("");

		schedule.setExplorations(explorations);

		schedule.setOnExplorationPinClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v instanceof ExplorationPin) {
			ExplorationPin pin = (ExplorationPin) v;
			Intent intent = new Intent(this, ExplorationDetailActivity.class)
					.putExtra(IntentData.EXTRA_KEY_EXPLORATION, pin.getExploration());
			startActivity(intent);
		}
	}
}