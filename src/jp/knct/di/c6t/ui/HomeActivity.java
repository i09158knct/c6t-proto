package jp.knct.di.c6t.ui;

import jp.knct.di.c6t.R;
import jp.knct.di.c6t.communication.BasicClient;
import jp.knct.di.c6t.ui.collection.CollectionHomeActivity;
import jp.knct.di.c6t.ui.exploration.ExplorationHomeActivity;
import jp.knct.di.c6t.ui.ranking.RankingActivity;
import jp.knct.di.c6t.ui.route.MyRoutesActivity;
import jp.knct.di.c6t.ui.route.RouteCreationActivity;
import jp.knct.di.c6t.ui.schedule.MyScheduleActivity;
import jp.knct.di.c6t.util.ActivityUtil;
import jp.knct.di.c6t.util.ActivityUtil.ActivityLink;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class HomeActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		if (!new BasicClient().isUserSaved(this)) {
			startActivity(new Intent(this, RegistrationFormActivity.class));
		}

		ActivityUtil.setJumper(this, new ActivityLink[] {
				new ActivityLink(R.id.home_schedule, MyScheduleActivity.class),
				new ActivityLink(R.id.home_ranking, RankingActivity.class),
				new ActivityLink(R.id.home_collection, CollectionHomeActivity.class),
				new ActivityLink(R.id.home_exploration, ExplorationHomeActivity.class),
				new ActivityLink(R.id.home_my_routes, MyRoutesActivity.class),
				new ActivityLink(R.id.home_creation, RouteCreationActivity.class),
		});
	}
}
