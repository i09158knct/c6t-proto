package jp.knct.di.c6t.ui.exploration;

import jp.knct.di.c6t.R;
import jp.knct.di.c6t.ui.HomeActivity;
import jp.knct.di.c6t.util.ActivityUtil;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class ExplorationEndActivity extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exploration_end);

		ActivityUtil.setOnClickListener(this, this, new int[] {
				R.id.exploration_end_end,
		});

		// TODO: save trophy and outcomes
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.exploration_end_end:
			Intent intent = new Intent(this, HomeActivity.class)
					.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;

		default:
			break;
		}

	}
}
