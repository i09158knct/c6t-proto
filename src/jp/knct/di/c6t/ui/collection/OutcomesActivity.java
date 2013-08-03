package jp.knct.di.c6t.ui.collection;

import jp.knct.di.c6t.R;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;

public class OutcomesActivity extends FragmentActivity {
	private FragmentTabHost mTabHost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_outcomes_tab_host);

		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.fragment_outcomes_tab_host_content);
		mTabHost.addTab(mTabHost.newTabSpec("missions").setIndicator("ミッション"),
				MissionOutcomesFragment.class, null);
		mTabHost.addTab(mTabHost.newTabSpec("quests").setIndicator("クエスト"),
				QuestOutcomesFragment.class, null);
	}
}
