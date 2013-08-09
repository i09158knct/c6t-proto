package jp.knct.di.c6t.ui.ranking;

import jp.knct.di.c6t.R;
import jp.knct.di.c6t.communication.BasicClient.SearchRouteParams;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;

public class RankingActivity extends FragmentActivity {
	private FragmentTabHost mTabHost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_ranking_tab_host);

		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.fragment_ranking_tab_host_content);
		mTabHost.addTab(mTabHost.newTabSpec(SearchRouteParams.SORT_PLAYED_COUNT).setIndicator("ééóVâÒêî"),
				RankingContentsFragment.class,
				buildSortParamBundle(SearchRouteParams.SORT_PLAYED_COUNT));
		mTabHost.addTab(mTabHost.newTabSpec(SearchRouteParams.SORT_ACHIEVEMENT_COUNT).setIndicator("íBê¨âÒêî"),
				RankingContentsFragment.class,
				buildSortParamBundle(SearchRouteParams.SORT_ACHIEVEMENT_COUNT));
	}

	private Bundle buildSortParamBundle(String sortValue) {
		Bundle bundle = new Bundle();
		bundle.putString(SearchRouteParams.KEY_SORT, sortValue);
		return bundle;
	}
}
