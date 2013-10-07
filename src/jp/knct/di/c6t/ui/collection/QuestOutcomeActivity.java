package jp.knct.di.c6t.ui.collection;

import java.text.ParseException;

import jp.knct.di.c6t.IntentData;
import jp.knct.di.c6t.R;
import jp.knct.di.c6t.communication.OutcomesClient;
import jp.knct.di.c6t.model.Outcome;
import jp.knct.di.c6t.model.QuestOutcome;
import jp.knct.di.c6t.util.ActivityUtil;
import jp.knct.di.c6t.util.TimeUtil;

import org.json.JSONException;

import android.app.Activity;
import android.os.Bundle;

public class QuestOutcomeActivity extends Activity {
	private QuestOutcome mQuestOutcome;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_outcome_quest);
		Outcome outcome = getIntent().getParcelableExtra(IntentData.EXTRA_KEY_QUEST);
		mQuestOutcome = QuestOutcome.convertOutcome(outcome);
		new ActivityUtil(this)
				.setText(R.id.outcome_quest_route_name, mQuestOutcome.getRoute().getName())
				.setText(R.id.outcome_quest_pose_name, mQuestOutcome.getPose())
				.setText(R.id.outcome_quest_photoed_at, TimeUtil.format(mQuestOutcome.getPhotoedAt()))
				.setText(R.id.outcome_quest_comment, mQuestOutcome.getComment())
				.setImageBitmap(R.id.outcome_quest_photo, mQuestOutcome.decodePhotoBitmap(10));
	}

	@Override
	protected void onPause() {
		super.onPause();

		String comment = ActivityUtil.getText(this, R.id.outcome_quest_comment);
		mQuestOutcome.setComment(comment);
		try {
			new OutcomesClient().updateQuestOutcome(this, mQuestOutcome);
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
