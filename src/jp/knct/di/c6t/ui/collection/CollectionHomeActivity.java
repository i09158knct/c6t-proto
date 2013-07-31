package jp.knct.di.c6t.ui.collection;

import jp.knct.di.c6t.R;
import jp.knct.di.c6t.util.ActivityUtil;
import jp.knct.di.c6t.util.ActivityUtil.ActivityLink;
import android.app.Activity;
import android.os.Bundle;

public class CollectionHomeActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_collection_home);

		ActivityUtil.setJumper(this, new ActivityLink[] {
				new ActivityLink(R.id.collection_home_outcomes, null),
				new ActivityLink(R.id.collection_home_trophies, null),
		});
	}
}
