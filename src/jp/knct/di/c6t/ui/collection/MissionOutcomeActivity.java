package jp.knct.di.c6t.ui.collection;

import java.text.ParseException;

import jp.knct.di.c6t.IntentData;
import jp.knct.di.c6t.R;
import jp.knct.di.c6t.communication.OutcomesClient;
import jp.knct.di.c6t.model.MissionOutcome;
import jp.knct.di.c6t.model.Outcome;
import jp.knct.di.c6t.util.ActivityUtil;
import jp.knct.di.c6t.util.TimeUtil;

import org.json.JSONException;

import android.app.Activity;
import android.os.Bundle;

public class MissionOutcomeActivity extends Activity {
	private MissionOutcome mMissionOutcome;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_outcome_mission);
		Outcome outcome = getIntent().getParcelableExtra(IntentData.EXTRA_KEY_OUTCOME);
		mMissionOutcome = MissionOutcome.convertOutcome(outcome);
		new ActivityUtil(this)
				.setText(R.id.outcome_mission_route_name, mMissionOutcome.getRoute().getName())
				.setText(R.id.outcome_mission_name, mMissionOutcome.getMission())
				.setText(R.id.outcome_mission_photoed_at, TimeUtil.format(mMissionOutcome.getPhotoedAt()))
				.setText(R.id.outcome_mission_comment, mMissionOutcome.getComment())
				.setImageBitmap(R.id.outcome_mission_photo, mMissionOutcome.decodePhotoBitmap(10));
	}

	@Override
	protected void onPause() {
		super.onPause();

		String comment = ActivityUtil.getText(this, R.id.outcome_mission_comment);
		mMissionOutcome.setComment(comment);
		try {
			new OutcomesClient().updateMissionOutcome(this, mMissionOutcome);
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
