package jp.knct.di.c6t.ui.collection;

import java.util.LinkedList;
import java.util.List;

import jp.knct.di.c6t.R;
import jp.knct.di.c6t.communication.DebugSharedPreferencesClient;
import jp.knct.di.c6t.model.Quest;
import jp.knct.di.c6t.model.Route;
import jp.knct.di.c6t.util.ActivityUtil;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class MissionOutcomesFragment extends ListFragment {
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		MissionsAdapter adapter = new MissionsAdapter(getActivity(), getMissions());
		setListAdapter(adapter);
	}

	private List<Quest> getMissions() {
		// TODO
		DebugSharedPreferencesClient client = new DebugSharedPreferencesClient(getActivity());
		List<Quest> quests = new LinkedList<Quest>();
		for (Route route : client.getRoutes(client.getMyUserData())) {
			quests.addAll(route.getQuests());
		}
		return quests;
	}

	private class MissionsAdapter extends ArrayAdapter<Quest> {
		private List<Quest> mMissions; // FIXME
		private LayoutInflater mInflater;

		public MissionsAdapter(Activity context, List<Quest> missions) {
			super(context, 0, missions);
			mMissions = missions;
			mInflater = context.getLayoutInflater();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Quest mission = mMissions.get(position);

			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.list_item_outcome_mission, parent, false);
			}

			new ActivityUtil(convertView)
					.setText(R.id.list_item_outcome_mission_name, mission.getMission())
					.setText(R.id.list_item_outcome_mission_photoed_at, "______"); // TODO

			return convertView;
		}
	}
}
