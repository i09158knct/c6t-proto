package jp.knct.di.c6t.ui.route;

import jp.knct.di.c6t.R;
import jp.knct.di.c6t.model.Route;

import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class RouteCreationDetailFormActivity extends Activity implements OnClickListener {

	public static final int REQUEST_CODE_EDIT_ROUTE_DETAIL = 0x1000;
	private Route mRoute;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_route_creation_detail_form);

		mRoute = getIntent().getParcelableExtra(IntentData.EXTRA_KEY_ROUTE);
		setOnClickListeners();
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
			setResult(RESULT_CANCELED);
			finish();
			break;

		case R.id.route_creation_detail_form_ok:
			setDetailsFromEditForms(mRoute);
			if (mRoute.isValid()) {
				Intent intent = new Intent()
						.putExtra(IntentData.EXTRA_KEY_ROUTE, mRoute);
				try {
					Toast.makeText(this, mRoute.toJSON().toString(2), 1).show(); // TODO: save route
				}
				catch (JSONException e) {}
				setResult(RESULT_OK);
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
