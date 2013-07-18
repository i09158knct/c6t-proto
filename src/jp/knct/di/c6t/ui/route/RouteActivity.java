package jp.knct.di.c6t.ui.route;

import jp.knct.di.c6t.IntentData;
import jp.knct.di.c6t.R;
import jp.knct.di.c6t.model.Route;
import jp.knct.di.c6t.ui.exploration.NewExplorationActivity;
import jp.knct.di.c6t.util.ActivityUtil;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class RouteActivity extends Activity implements OnClickListener {
	private Route mRoute;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_route);

		mRoute = getIntent().getParcelableExtra(IntentData.EXTRA_KEY_ROUTE);
		putRouteDataIntoComponents(mRoute);

		ActivityUtil.setOnClickListener(this, this, new int[] {
				R.id.route_create_new_exploration,
		});

	}

	private void putRouteDataIntoComponents(Route route) {
		new ActivityUtil(this)
				.setText(R.id.route_location, route.getStartLocation().toString())
				.setText(R.id.route_description, route.getDescription())
				.setText(R.id.route_name, route.getName());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.route_create_new_exploration:
			Intent intent = new Intent(this, NewExplorationActivity.class)
					.putExtra(IntentData.EXTRA_KEY_ROUTE, mRoute);
			startActivity(intent);
			break;

		default:
			break;
		}
	}
}
