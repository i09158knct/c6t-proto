package jp.knct.di.c6t.ui.collection;

import java.text.ParseException;
import java.util.List;

import jp.knct.di.c6t.IntentData;
import jp.knct.di.c6t.R;
import jp.knct.di.c6t.communication.OutcomesClient;
import jp.knct.di.c6t.model.MissionOutcome;
import jp.knct.di.c6t.util.ActivityUtil;
import jp.knct.di.c6t.util.TimeUtil;

import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MissionOutcomesFragment extends ListFragment {
	private List<MissionOutcome> mMissionOutcomes;
	private MissionOutcomesAdapter mMissionOutcomesAdapter;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		OutcomesClient client = new OutcomesClient();
		try {
			mMissionOutcomes = client.getMissionOutcomes(getActivity());
		}
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		mMissionOutcomesAdapter = new MissionOutcomesAdapter(getActivity(), mMissionOutcomes);
		setListAdapter(mMissionOutcomesAdapter);
	}

	@Override
	public void onResume() {
		super.onResume();
		OutcomesClient client = new OutcomesClient();
		try {
			mMissionOutcomes = client.getMissionOutcomes(getActivity());
		}
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mMissionOutcomesAdapter.mMissionOutcomes = mMissionOutcomes;
		mMissionOutcomesAdapter.notifyDataSetChanged();
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(getActivity(), MissionOutcomeActivity.class)
				.putExtra(IntentData.EXTRA_KEY_OUTCOME, mMissionOutcomes.get(position));
		startActivity(intent);
	}

	private class MissionOutcomesAdapter extends ArrayAdapter<MissionOutcome> {
		private List<MissionOutcome> mMissionOutcomes;
		private LayoutInflater mInflater;

		public MissionOutcomesAdapter(Activity context, List<MissionOutcome> missionOutcomes) {
			super(context, 0, missionOutcomes);
			mMissionOutcomes = missionOutcomes;
			mInflater = context.getLayoutInflater();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.list_item_outcome_mission, parent, false);
			}

			MissionOutcome missionOutcome = mMissionOutcomes.get(position);
			new ActivityUtil(convertView)
					.setText(R.id.list_item_outcome_mission_route_name, missionOutcome.getRoute().getName())
					.setText(R.id.list_item_outcome_mission_name, missionOutcome.getMission())
					.setText(R.id.list_item_outcome_mission_photoed_at, TimeUtil.format(missionOutcome.getPhotoedAt()))
					.setText(R.id.list_item_outcome_mission_comment, missionOutcome.getComment())
					.setImageBitmap(R.id.list_item_outcome_mission_photo, missionOutcome.decodePhotoBitmap(10));

			return convertView;
		}
	}
}
