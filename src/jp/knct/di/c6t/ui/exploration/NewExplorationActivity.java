package jp.knct.di.c6t.ui.exploration;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import jp.knct.di.c6t.IntentData;
import jp.knct.di.c6t.R;
import jp.knct.di.c6t.communication.BasicClient;
import jp.knct.di.c6t.model.Exploration;
import jp.knct.di.c6t.model.Route;
import jp.knct.di.c6t.model.User;
import jp.knct.di.c6t.ui.schedule.RouteScheduleActivity;
import jp.knct.di.c6t.util.ActivityUtil;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import android.accounts.NetworkErrorException;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

public class NewExplorationActivity extends Activity implements OnClickListener {
	private Route mRoute;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_exploration);

		mRoute = getIntent().getParcelableExtra(IntentData.EXTRA_KEY_ROUTE);

		ActivityUtil.setOnClickListener(this, this, new int[] {
				R.id.new_exploration_ok,
				R.id.new_exploration_schedule,
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.new_exploration_ok:
			Exploration exploration = createExplorationFromForms();

			// TODO: show dialog
			new PostingTask().execute(exploration);
			break;

		case R.id.new_exploration_schedule:
			Intent intent = new Intent(this, RouteScheduleActivity.class)
					.putExtra(IntentData.EXTRA_KEY_ROUTE, mRoute);
			startActivity(intent);
			break;

		default:
			break;
		}
	}

	private Exploration createExplorationFromForms() {
		User myself = new BasicClient().getUserFromLocal(this);
		Date date = getDateTime();
		String description = ActivityUtil.getText(this, R.id.new_exploration_description);
		return new Exploration(myself, mRoute, date, description, new LinkedList<User>());
	}

	private Date getDateTime() {
		DatePicker datePicker = (DatePicker) findViewById(R.id.new_exploration_date);
		TimePicker timePicker = (TimePicker) findViewById(R.id.new_exploration_time);

		int year = datePicker.getYear();
		int month = datePicker.getMonth();
		int day = datePicker.getDayOfMonth();
		int hour = timePicker.getCurrentHour();
		int minute = timePicker.getCurrentMinute();
		return new GregorianCalendar(year, month, day, hour, minute).getTime();
	}

	private class PostingTask extends AsyncTask<Exploration, String, Void> {

		@Override
		protected Void doInBackground(Exploration... exploration) {
			BasicClient client = new BasicClient();
			try {
				client.postExploration(exploration[0]);
			}
			catch (ClientProtocolException e) {
				publishProgress(e.getLocalizedMessage());
				e.printStackTrace();
				cancel(true);
				return null;
			}
			catch (JSONException e) {
				publishProgress(e.getLocalizedMessage());
				e.printStackTrace();
				cancel(true);
				return null;
			}
			catch (IOException e) {
				publishProgress(e.getLocalizedMessage());
				e.printStackTrace();
				cancel(true);
				return null;
			}
			catch (NetworkErrorException e) {
				publishProgress(e.getLocalizedMessage());
				e.printStackTrace();
				cancel(true);
				return null;
			}
			catch (ParseException e) {
				publishProgress(e.getLocalizedMessage());
				e.printStackTrace();
				cancel(true);
				return null;
			}

			publishProgress("íTçıÇÃó\íËÇÃçÏê¨Ç™äÆóπÇµÇ‹ÇµÇΩ");
			return null;
		}

		@Override
		protected void onProgressUpdate(String... progressText) {
			Toast.makeText(getApplicationContext(), progressText[0], Toast.LENGTH_SHORT).show();
		}

		@Override
		protected void onPostExecute(Void results) {
			startActivity(new Intent(NewExplorationActivity.this, ExplorationHomeActivity.class)
					.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
		}
	}
}
