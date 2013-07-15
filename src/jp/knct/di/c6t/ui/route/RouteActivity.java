package jp.knct.di.c6t.ui.route;

import jp.knct.di.c6t.R;
import jp.knct.di.c6t.model.Route;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class RouteActivity extends Activity implements OnClickListener {
	private Route mRoute;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_route_creation_detail_form);

		mRoute = getIntent().getParcelableExtra(IntentData.EXTRA_KEY_ROUTE);
		putRouteDataIntoComponents(mRoute);
		setOnClickListeners();
	}

	private void putRouteDataIntoComponents(Route route) {
		setText(R.id.route_location, route.getStartLocation().toString());
		setText(R.id.route_description, route.getDescription());
		setText(R.id.route_name, route.getName());
	}

	private void setText(int id, String text) {
		TextView textView = (TextView) findViewById(id);
		textView.setText(text);
	}

	private void setOnClickListeners() {
		int[] ids = {
				R.id.route_create_new_exploration,
		};

		for (int id : ids) {
			findViewById(id).setOnClickListener(this);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.route_create_new_exploration:
			Intent intent = new Intent()
					.putExtra(IntentData.EXTRA_KEY_ROUTE, mRoute);
			// TODO
			break;

		default:
			break;
		}
	}
}
