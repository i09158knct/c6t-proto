package jp.knct.di.c6t.ui.exploration;

import jp.knct.di.c6t.R;
import jp.knct.di.c6t.util.ActivityUtil;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class ExplorationHomeActivity extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exploration_home);

		ActivityUtil.setOnClickListener(this, this, new int[] {
				R.id.exploration_home_create_new_exploration,
				R.id.exploration_home_join_exploration,
		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.exploration_home_create_new_exploration:
			// TODO
			// Intent intent = new Intent(this, .class);
			// startActivity(intent);
			break;

		case R.id.exploration_home_join_exploration:
			// TODO
			// Intent intent = new Intent(this, .class);
			// startActivity(intent);
			break;

		default:
			break;
		}
	}
}
