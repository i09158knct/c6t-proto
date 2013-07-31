package jp.knct.di.c6t.ui;

import jp.knct.di.c6t.R;
import jp.knct.di.c6t.ui.exploration.ExplorationHomeActivity;
import jp.knct.di.c6t.ui.route.MyRoutesActivity;
import jp.knct.di.c6t.ui.route.RouteCreationActivity;
import jp.knct.di.c6t.util.ActivityUtil;
import jp.knct.di.c6t.util.ActivityUtil.ActivityLink;
import android.app.Activity;
import android.os.Bundle;

public class HomeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		ActivityUtil.setJumper(this, new ActivityLink[] {
				new ActivityLink(R.id.home_collection, null), // TODO
				new ActivityLink(R.id.home_ranking, null), // TODO
				new ActivityLink(R.id.home_schedule, null), // TODO
				new ActivityLink(R.id.home_exploration, ExplorationHomeActivity.class),
				new ActivityLink(R.id.home_my_routes, MyRoutesActivity.class),
				new ActivityLink(R.id.home_creation, RouteCreationActivity.class),
		});
	}

}
