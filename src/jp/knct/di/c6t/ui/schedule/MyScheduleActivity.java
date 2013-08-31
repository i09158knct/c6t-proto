package jp.knct.di.c6t.ui.schedule;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import jp.knct.di.c6t.IntentData;
import jp.knct.di.c6t.R;
import jp.knct.di.c6t.communication.BasicClient;
import jp.knct.di.c6t.model.Exploration;
import jp.knct.di.c6t.ui.exploration.ExplorationDetailActivity;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class MyScheduleActivity extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_schedule);

		// TODO: display alert dialog
		new LoadingTask().execute();
	}

	@Override
	public void onClick(View v) {
		if (v instanceof ExplorationPin) {
			ExplorationPin pin = (ExplorationPin) v;
			Intent intent = new Intent(this, ExplorationDetailActivity.class)
					.putExtra(IntentData.EXTRA_KEY_EXPLORATION, pin.getExploration());
			startActivity(intent);
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
			ScheduleFragment schedule = (ScheduleFragment) getFragmentManager().findFragmentById(R.id.my_schedule);

			schedule.setExplorations(myExplorations);
			schedule.setOnExplorationPinClickListener(MyScheduleActivity.this);

			if (myExplorations.size() == 0) {
				Toast.makeText(getApplicationContext(), "—\’è‚Í‚ ‚è‚Ü‚¹‚ñ", Toast.LENGTH_SHORT).show();
			}
		}
	}
}
