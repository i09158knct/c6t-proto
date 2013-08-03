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

public class QuestOutcomesFragment extends ListFragment {
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		List<Quest> quests = getQuests();
		QuestsAdapter adapter = new QuestsAdapter(getActivity(), quests);
		setListAdapter(adapter);
	}

	private List<Quest> getQuests() {
		// TODO
		DebugSharedPreferencesClient client = new DebugSharedPreferencesClient(getActivity());
		List<Quest> quests = new LinkedList<Quest>();
		for (Route route : client.getRoutes(client.getMyUserData())) {
			quests.addAll(route.getQuests());
		}
		return quests;
	}

	private class QuestsAdapter extends ArrayAdapter<Quest> {
		private List<Quest> mQuests; // FIXME
		private LayoutInflater mInflater;

		public QuestsAdapter(Activity context, List<Quest> quests) {
			super(context, 0, quests);
			mQuests = quests;
			mInflater = context.getLayoutInflater();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Quest quest = mQuests.get(position);

			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.list_item_outcome_quest, parent, false);
			}

			new ActivityUtil(convertView)
					.setText(R.id.list_item_outcome_quest_pose_name, quest.getPose())
					.setText(R.id.list_item_outcome_quest_photoed_at, "______"); // TODO

			return convertView;
		}
	}
}
