package jp.knct.di.c6t.ui.route;

import jp.knct.di.c6t.R;
import jp.knct.di.c6t.model.Quest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

public class RouteCreationQuestFormActivity extends Activity implements OnClickListener {

	public static int REQUEST_CODE_EDIT_QUEST = 0;

	private int mQuestNumber;
	private LatLng mQuestLocation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_route_creation_quest_form);

		mQuestNumber = getIntent().getIntExtra(IntentData.EXTRA_KEY_QUEST_NUMBER, -1);
		Quest quest = getIntent().getParcelableExtra(IntentData.EXTRA_KEY_BASE_QUEST);
		mQuestLocation = quest.getLocation();
		putQuestDataIntoEditForms(quest);

		setOnClickListeners();
	}

	private Quest createQuestFromEditForms() {
		String mission = getTextFromEditForm(R.id.route_creation_quest_form_mission);
		String pose = getTextFromEditForm(R.id.route_creation_quest_form_pose);
		String title = getTextFromEditForm(R.id.route_creation_quest_form_title);
		return new Quest(mQuestLocation, title, pose, mission, ""); // TODO image
	}

	private String getTextFromEditForm(int id) {
		TextView textView = (TextView) findViewById(id);
		return textView.getText().toString();
	}

	private void putQuestDataIntoEditForms(Quest quest) {
		setText(R.id.route_creation_quest_form_location, quest.getLocation().toString());
		setText(R.id.route_creation_quest_form_mission, quest.getMission());
		setText(R.id.route_creation_quest_form_pose, quest.getPose());
		setText(R.id.route_creation_quest_form_title, quest.getTitle());
	}

	private void setText(int id, String text) {
		TextView textView = (TextView) findViewById(id);
		textView.setText(text);
	}

	private void setOnClickListeners() {
		int[] ids = {
				R.id.route_creation_quest_form_camera,
				R.id.route_creation_quest_form_cancel,
				R.id.route_creation_quest_form_ok,
		};

		for (int id : ids) {
			findViewById(id).setOnClickListener(this);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.route_creation_quest_form_camera:
			Toast.makeText(this, "������", 0).show(); // TODO
			break;

		case R.id.route_creation_quest_form_cancel:
			setResult(RESULT_CANCELED);
			finish();
			break;

		case R.id.route_creation_quest_form_ok:
			Quest quest = createQuestFromEditForms();
			if (quest.isValid()) {
				Intent intent = new Intent()
						.putExtra(IntentData.EXTRA_KEY_BASE_QUEST, quest)
						.putExtra(IntentData.EXTRA_KEY_QUEST_NUMBER, mQuestNumber);
				setResult(RESULT_OK, intent);
				finish();
			}
			break;

		default:
			break;
		}
	}
}
