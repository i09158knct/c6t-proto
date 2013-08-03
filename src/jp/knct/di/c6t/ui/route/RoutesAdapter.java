package jp.knct.di.c6t.ui.route;

import java.util.List;

import jp.knct.di.c6t.R;
import jp.knct.di.c6t.model.Route;
import jp.knct.di.c6t.util.ActivityUtil;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class RoutesAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<Route> mRoutes;

	public RoutesAdapter(Activity activity, List<Route> routes) {
		mInflater = activity.getLayoutInflater();
		mRoutes = routes;
	}

	@Override
	public int getCount() {
		return mRoutes.size();
	}

	@Override
	public Object getItem(int position) {
		return mRoutes.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_item_route, parent, false);
		}

		Route route = mRoutes.get(position);
		new ActivityUtil(convertView)
				.setText(R.id.list_item_route_name, route.getName())
				.setText(R.id.list_item_route_description, route.getDescription());

		return convertView;
	}

}
