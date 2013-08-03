package jp.knct.di.c6t.ui.exploration;

import java.util.List;

import jp.knct.di.c6t.R;
import jp.knct.di.c6t.model.Exploration;
import jp.knct.di.c6t.util.ActivityUtil;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ExplorationsAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<Exploration> mExplorations;

	public ExplorationsAdapter(Activity activity, List<Exploration> explorations) {
		mInflater = activity.getLayoutInflater();
		mExplorations = explorations;
	}

	@Override
	public int getCount() {
		return mExplorations.size();
	}

	@Override
	public Object getItem(int position) {
		return mExplorations.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_item_exploration, parent, false);
		}

		Exploration exploration = mExplorations.get(position);
		new ActivityUtil(convertView)
				.setText(R.id.list_item_exploration_name, exploration.getRoute().getName())
				.setText(R.id.list_item_exploration_start_time, exploration.getStartTime().toString());

		return convertView;
	}

}
