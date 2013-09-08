package jp.knct.di.c6t.ui.route;

import java.io.IOException;
import java.util.Arrays;

import jp.knct.di.c6t.IntentData;
import jp.knct.di.c6t.R;
import jp.knct.di.c6t.model.Quest;
import jp.knct.di.c6t.util.ActivityUtil;
import jp.knct.di.c6t.util.ImageUtil;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.android.gms.maps.model.LatLng;

public class RouteCreationQuestFormActivity extends Activity implements OnClickListener {

	private static final int REQUEST_CODE_IMAGE_CAPTURE = 0x100;
	public static final int REQUEST_CODE_CREATE_QUEST = 0;
	public static final int REQUEST_CODE_EDIT_QUEST = 1;

	private int mQuestNumber;
	private LatLng mQuestLocation;

	private Uri mImageUri;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_route_creation_quest_form);

		mQuestNumber = getIntent().getIntExtra(IntentData.EXTRA_KEY_QUEST_NUMBER, -1);
		Quest quest = getIntent().getParcelableExtra(IntentData.EXTRA_KEY_QUEST);
		mQuestLocation = quest.getLocation();
		mImageUri = Uri.parse(quest.getImage());
		putQuestDataIntoEditForms(quest);

		if (getIntent().getIntExtra(IntentData.EXTRA_KEY_REQUEST_CODE, -1) == REQUEST_CODE_EDIT_QUEST) {
			findViewById(R.id.route_creation_quest_form_delete).setVisibility(View.VISIBLE);
		}

		ActivityUtil.setOnClickListener(this, this, new int[] {
				R.id.route_creation_quest_form_camera,
				R.id.route_creation_quest_form_cancel,
				R.id.route_creation_quest_form_ok,
				R.id.route_creation_quest_form_delete,

				R.id.debug_route_creation_quest_form_camera,
		});
	}

	private Quest createQuestFromEditForms() {
		String mission = (String) ((Spinner) findViewById(R.id.route_creation_quest_form_mission))
				.getSelectedItem();
		String pose = (String) ((Spinner) findViewById(R.id.route_creation_quest_form_pose))
				.getSelectedItem();
		return new Quest(mQuestLocation, pose, mission, mImageUri.getPath());
	}

	private void putQuestDataIntoEditForms(Quest quest) {
		new ActivityUtil(this)
				.setText(R.id.route_creation_quest_form_location, quest.getLocation().toString());

		String mission = quest.getMission();
		if (!mission.isEmpty()) {
			String[] missions = getResources().getStringArray(R.array.mission_subjects);
			int index = Arrays.asList(missions).indexOf(mission);
			if (index > -1) {
				((Spinner) findViewById(R.id.route_creation_quest_form_mission))
						.setSelection(index);
			}
		}

		String pose = quest.getPose();
		if (!pose.isEmpty()) {
			String[] poses = getResources().getStringArray(R.array.pose_subjects);
			int index = Arrays.asList(poses).indexOf(pose);
			if (index > -1) {
				((Spinner) findViewById(R.id.route_creation_quest_form_pose))
						.setSelection(index);
			}
		}

		if (mImageUri != null) {
			putQuestImage();
		}
	}

	private void putQuestImage() {
		Bitmap image = ImageUtil.decodeBitmap(mImageUri.getPath(), 10);
		((ImageView) findViewById(R.id.route_creation_quest_form_image))
				.setImageBitmap(image);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE_IMAGE_CAPTURE && resultCode == RESULT_OK) {
			putQuestImage();
		}
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.route_creation_quest_form_camera:
			try {
				mImageUri = ImageUtil.createTempFileUri();
				intent = ImageUtil.createCaptureImageIntent(mImageUri);
				startActivityForResult(intent, REQUEST_CODE_IMAGE_CAPTURE);
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case R.id.route_creation_quest_form_cancel:
			setResult(RESULT_CANCELED);
			finish();
			break;

		case R.id.route_creation_quest_form_ok:
			Quest quest = createQuestFromEditForms();
			if (quest.isValid()) {
				intent = new Intent()
						.putExtra(IntentData.EXTRA_KEY_QUEST, quest)
						.putExtra(IntentData.EXTRA_KEY_QUEST_NUMBER, mQuestNumber);
				setResult(RESULT_OK, intent);
				finish();
			}
			break;

		case R.id.route_creation_quest_form_delete:
			intent = new Intent()
					.putExtra(IntentData.EXTRA_KEY_QUEST, (Quest) null)
					.putExtra(IntentData.EXTRA_KEY_QUEST_NUMBER, mQuestNumber);
			setResult(RESULT_OK, intent);
			finish();
			break;

		case R.id.debug_route_creation_quest_form_camera:
			mImageUri = Uri.parse(ImageUtil.debugGetSampleImagePath(this));
			putQuestImage();
			break;

		default:
			break;
		}
	}
}
