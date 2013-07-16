package jp.knct.di.c6t.ui.exploration;

import jp.knct.di.c6t.R;
import jp.knct.di.c6t.ui.route.IntentData;
import jp.knct.di.c6t.util.ActivityUtil;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;

public class NewExplorationActivity extends Activity implements OnClickListener {
	private Parcelable mRoute;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_exploration);

		mRoute = getIntent().getParcelableExtra(IntentData.EXTRA_KEY_ROUTE);

		ActivityUtil.setOnClickListener(this, this, new int[] {
				R.id.route_create_new_exploration,
		});
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
