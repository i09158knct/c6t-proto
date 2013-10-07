package jp.knct.di.c6t.ui.collection;

import java.text.ParseException;
import java.util.List;

import jp.knct.di.c6t.IntentData;
import jp.knct.di.c6t.R;
import jp.knct.di.c6t.communication.OutcomesClient;
import jp.knct.di.c6t.model.QuestOutcome;
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

public class QuestOutcomesFragment extends ListFragment {
	private List<QuestOutcome> mQuestOutcomes;
	private QuestOutcomesAdapter mQuestOutcomesAdapter;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		OutcomesClient client = new OutcomesClient();
		try {
			mQuestOutcomes = client.getQuestOutcomes(getActivity());
		}
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		mQuestOutcomesAdapter = new QuestOutcomesAdapter(getActivity(), mQuestOutcomes);
		setListAdapter(mQuestOutcomesAdapter);
	}

	@Override
	public void onResume() {
		super.onResume();
		OutcomesClient client = new OutcomesClient();
		try {
			mQuestOutcomes = client.getQuestOutcomes(getActivity());
		}
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mQuestOutcomesAdapter.mQuestOutcomes = mQuestOutcomes;
		mQuestOutcomesAdapter.notifyDataSetChanged();
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(getActivity(), QuestOutcomeActivity.class)
				.putExtra(IntentData.EXTRA_KEY_OUTCOME, mQuestOutcomes.get(position));
		startActivity(intent);
	}

	private class QuestOutcomesAdapter extends ArrayAdapter<QuestOutcome> {
		private List<QuestOutcome> mQuestOutcomes;
		private LayoutInflater mInflater;

		public QuestOutcomesAdapter(Activity context, List<QuestOutcome> questOutcomes) {
			super(context, 0, questOutcomes);
			mQuestOutcomes = questOutcomes;
			mInflater = context.getLayoutInflater();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.list_item_outcome_quest, parent, false);
			}

			QuestOutcome questOutcome = mQuestOutcomes.get(position);
			new ActivityUtil(convertView)
					.setText(R.id.list_item_outcome_quest_route_name, questOutcome.getRoute().getName())
					.setText(R.id.list_item_outcome_quest_pose_name, questOutcome.getPose())
					.setText(R.id.list_item_outcome_quest_photoed_at, TimeUtil.format(questOutcome.getPhotoedAt()))
					.setText(R.id.list_item_outcome_quest_comment, questOutcome.getComment())
					.setImageBitmap(R.id.list_item_outcome_quest_photo, questOutcome.decodePhotoBitmap(10));

			return convertView;
		}
	}
}
