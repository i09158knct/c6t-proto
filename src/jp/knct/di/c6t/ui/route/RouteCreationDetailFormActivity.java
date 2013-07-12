package jp.knct.di.c6t.ui.route;

import jp.knct.di.c6t.R;
import jp.knct.di.c6t.model.Route;

import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class RouteCreationDetailFormActivity extends Activity implements OnClickListener {

	private Route mRoute;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_route_creation_detail_form);

		try {
			mRoute = extractRouteFromIntent(getIntent());
		}
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		setOnClickListeners();
	}

	private static Route extractRouteFromIntent(Intent intent) throws JSONException {
		String routeJSON = intent.getStringExtra(IntentData.EXTRA_KEY_JSON_ROUTE);
		return Route.parseJSONString(routeJSON);
	}

	private void setOnClickListeners() {
		int[] ids = {
				R.id.route_creation_detail_form_cancel,
				R.id.route_creation_detail_form_ok,
		};

		for (int id : ids) {
			findViewById(id).setOnClickListener(this);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.route_creation_detail_form_cancel:
			finish();
			break;

		case R.id.route_creation_detail_form_ok:
			setDetailsFromEditForms(mRoute);
			if (mRoute.isValid()) {
				String routeJSON = mRoute.toJSON().toString();
				Intent intent = new Intent()
						.putExtra(IntentData.EXTRA_KEY_JSON_ROUTE, routeJSON);
				Log.d("finish", routeJSON); // TODO
				finish();
			}
			break;

		default:
			break;
		}
	}

	private void setDetailsFromEditForms(Route route) {
		String name = ((EditText) findViewById(R.id.route_creation_detail_form_name)).getText().toString();
		String description = ((EditText) findViewById(R.id.route_creation_detail_form_description)).getText().toString();
		route.setName(name);
		route.setDescription(description);
	}

}
