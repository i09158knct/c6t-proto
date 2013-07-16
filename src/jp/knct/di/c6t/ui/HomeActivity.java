package jp.knct.di.c6t.ui;

import jp.knct.di.c6t.R;
import jp.knct.di.c6t.ui.exploration.ExplorationHomeActivity;
import jp.knct.di.c6t.ui.route.MyRoutesActivity;
import jp.knct.di.c6t.ui.route.RouteCreationActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class HomeActivity extends Activity {

	private static class ActivityLink {
		private final int id;
		private final Class<? extends Activity> target;

		public ActivityLink(int id, Class<? extends Activity> target) {
			this.id = id;
			this.target = target;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		setJumper(new ActivityLink[] {
				new ActivityLink(R.id.home_collection, null), // TODO
				new ActivityLink(R.id.home_ranking, null), // TODO
				new ActivityLink(R.id.home_schedule, null), // TODO
				new ActivityLink(R.id.home_exploration, ExplorationHomeActivity.class),
				new ActivityLink(R.id.home_my_routes, MyRoutesActivity.class),
				new ActivityLink(R.id.home_creation, RouteCreationActivity.class),
		});
	}

	private void setJumper(ActivityLink... activityLinks) {
		for (final ActivityLink activityLink : activityLinks) {
			findViewById(activityLink.id).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(HomeActivity.this, activityLink.target);
					startActivity(intent);
				}
			});
		}
	}
}
