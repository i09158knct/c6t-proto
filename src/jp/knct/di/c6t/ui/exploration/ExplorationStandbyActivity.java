package jp.knct.di.c6t.ui.exploration;

import java.io.IOException;
import java.text.ParseException;

import jp.knct.di.c6t.IntentData;
import jp.knct.di.c6t.R;
import jp.knct.di.c6t.communication.BasicClient;
import jp.knct.di.c6t.model.Exploration;
import jp.knct.di.c6t.model.User;
import jp.knct.di.c6t.util.ActivityUtil;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.gms.maps.model.CameraPosition;

public class ExplorationStandbyActivity extends ListActivity implements OnClickListener {
	private Exploration mExploration;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exploration_standby);

		mExploration = getIntent().getParcelableExtra(IntentData.EXTRA_KEY_EXPLORATION);

		if (!isHost()) {
			// FIXME
			findViewById(R.id.exploration_standby_start).setVisibility(View.INVISIBLE);
		}

		ActivityUtil.setOnClickListener(this, this, new int[] {
				R.id.exploration_standby_start,
		});

		new JoiningTask().execute(mExploration);
		new UpdatingLoopTask().execute(mExploration);
	}

	private boolean isHost() {
		return mExploration.isHost(new BasicClient().getUserFromLocal(this));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.exploration_standby_start:
			new StartingTask().execute(mExploration);
			break;

		default:
			break;
		}

	}

	private void onExplorationUpdate(Exploration exploration) {
		if (exploration.isStarted()) {
			startExploration(exploration);
			return;
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		for (User user : exploration.getMembers()) {
			adapter.add(user.getName());
		}
		setListAdapter(adapter);
		mExploration = exploration;
	}

	private void startExploration(Exploration exploration) {
		Toast.makeText(this, "íTçıÇäJénÇµÇ‹Ç∑", Toast.LENGTH_SHORT).show();

		CameraPosition position = getIntent().getParcelableExtra(IntentData.EXTRA_KEY_CAMERA_POSITION);

		Intent intent = new Intent(this, ExplorationMainActivity.class)
				.putExtra(IntentData.EXTRA_KEY_EXPLORATION, mExploration)
				.putExtra(IntentData.EXTRA_KEY_CAMERA_POSITION, position);
		startActivity(intent);
		finish();
	}

	private class JoiningTask extends AsyncTask<Exploration, String, Exploration> {
		@Override
		protected Exploration doInBackground(Exploration... exploration) {
			BasicClient client = new BasicClient();
			HttpResponse response = null;
			try {
				User myself = client.getUserFromLocal(getApplicationContext());
				response = client.putMember(exploration[0], myself);
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

			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode >= 300) {
				publishProgress("error: " + statusCode);
				cancel(true);
				return null;
			}

			try {
				return client.getExploration(exploration[0].getId());
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
			return null;
		}

		@Override
		protected void onProgressUpdate(String... progressText) {
			Toast.makeText(getApplicationContext(), progressText[0], Toast.LENGTH_SHORT).show();
		}

		@Override
		protected void onPostExecute(Exploration exploration) {
			if (exploration == null) {
				throw new AssertionError("pending...");
			}

			Toast.makeText(getApplicationContext(), "íTçıÇ…éQâ¡ÇµÇ‹ÇµÇΩ", Toast.LENGTH_SHORT).show();
			onExplorationUpdate(exploration);
		}
	}

	private class StartingTask extends AsyncTask<Exploration, String, Void> {
		@Override
		protected Void doInBackground(Exploration... exploration) {
			BasicClient client = new BasicClient();
			HttpResponse response = null;
			try {
				response = client.startExploration(exploration[0]);
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

			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode >= 300) {
				publishProgress("error: " + statusCode);
				cancel(true);
				return null;
			}

			return null;
		}

		@Override
		protected void onProgressUpdate(String... progressText) {
			Toast.makeText(getApplicationContext(), progressText[0], Toast.LENGTH_SHORT).show();
		}

		@Override
		protected void onPostExecute(Void params) {
			Toast.makeText(getApplicationContext(), "íTçıäJéníÜ...", Toast.LENGTH_SHORT).show();
		}
	}

	private class UpdatingLoopTask extends AsyncTask<Exploration, String, Exploration> {
		private static final int interval = 2000;

		@Override
		protected Exploration doInBackground(Exploration... exploration) {
			try {
				Thread.sleep(interval);
			}
			catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			try {
				return new BasicClient().getExploration(exploration[0].getId());
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

			return null;
		}

		@Override
		protected void onPostExecute(Exploration exploration) {
			if (exploration == null) {
				throw new AssertionError("pending...");
			}

			onExplorationUpdate(exploration);

			if (!exploration.isStarted()) {
				new UpdatingLoopTask().execute(exploration);
			}

		}
	}
}
