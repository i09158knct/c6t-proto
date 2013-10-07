package jp.knct.di.c6t.ui.collection;

import java.text.ParseException;

import jp.knct.di.c6t.IntentData;
import jp.knct.di.c6t.R;
import jp.knct.di.c6t.communication.OutcomesClient;
import jp.knct.di.c6t.model.Trophy;
import jp.knct.di.c6t.util.ActivityUtil;
import jp.knct.di.c6t.util.ImageUtil;
import jp.knct.di.c6t.util.TimeUtil;

import org.json.JSONException;

import android.app.Activity;
import android.os.Bundle;

public class TrophyActivity extends Activity {
	private Trophy mTrophy;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trophy);

		mTrophy = getIntent().getParcelableExtra(IntentData.EXTRA_KEY_TROPHY);

		new ActivityUtil(this)
				.setText(R.id.trophy_route_name, mTrophy.getExploration().getRoute().getName())
				.setText(R.id.trophy_achieved_at, TimeUtil.format(mTrophy.getAchievedAt()))
				.setText(R.id.trophy_comment, mTrophy.getComment())
				.setImageBitmap(R.id.trophy_group_photo, ImageUtil.decodeBitmap(mTrophy.getPhotoUri(), 10));
	}

	@Override
	protected void onPause() {
		super.onPause();

		String comment = ActivityUtil.getText(this, R.id.trophy_comment);
		mTrophy.setComment(comment);
		try {
			new OutcomesClient().updateTrophy(this, mTrophy);
		}
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
