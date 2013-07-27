package jp.knct.di.c6t.ui.exploration;

import jp.knct.di.c6t.IntentData;
import jp.knct.di.c6t.R;
import jp.knct.di.c6t.model.Exploration;
import android.app.Activity;
import android.os.Bundle;

public class ExplorationMainActivity extends Activity {
	private Exploration mExploration;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exploration_main);

		mExploration = getIntent().getParcelableExtra(IntentData.EXTRA_KEY_EXPLORATION);
	}

}
