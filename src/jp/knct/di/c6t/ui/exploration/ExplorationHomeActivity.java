package jp.knct.di.c6t.ui.exploration;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import jp.knct.di.c6t.IntentData;
import jp.knct.di.c6t.R;
import jp.knct.di.c6t.communication.BasicClient;
import jp.knct.di.c6t.model.Exploration;
import jp.knct.di.c6t.ui.route.SearchRouteActivity;
import jp.knct.di.c6t.util.ActivityUtil;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

public class ExplorationHomeActivity extends ListActivity implements OnClickListener {
	private List<Exploration> mExplorations;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exploration_home);

		ActivityUtil.setOnClickListener(this, this, new int[] {
				R.id.exploration_home_create_new_exploration,
				R.id.exploration_home_join_exploration,
		});

		new LoadingTask().execute();
	}

	protected void onListItemClick(ListView l, View v, int position, long id) {
		Exploration targetExploration = mExplorations.get(position);
		Intent intent = new Intent(this, ExplorationDetailActivity.class)
				.putExtra(IntentData.EXTRA_KEY_EXPLORATION, targetExploration);
		startActivity(intent);
		finish();
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.exploration_home_create_new_exploration:
			intent = new Intent(this, SearchRouteActivity.class);
			startActivity(intent);
			break;

		case R.id.exploration_home_join_exploration:
			intent = new Intent(this, SearchExplorationActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	}

	private class LoadingTask extends AsyncTask<Void, Void, List<Exploration>> {

		@Override
		protected List<Exploration> doInBackground(Void... params) {
			BasicClient client = new BasicClient();
			List<Exploration> explorations = null;
			try {
				explorations = client.getExplorations(client.getUserFromLocal(getApplicationContext()));
			}
			catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return explorations;
		}

		@Override
		protected void onPostExecute(List<Exploration> myExplorations) {
			mExplorations = myExplorations;
			ExplorationsAdapter adapter = new ExplorationsAdapter(ExplorationHomeActivity.this, mExplorations);
			setListAdapter(adapter);
		}
	}
}
