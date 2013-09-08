package jp.knct.di.c6t.ui;

import java.io.IOException;

import jp.knct.di.c6t.R;
import jp.knct.di.c6t.communication.BasicClient;
import jp.knct.di.c6t.model.User;
import jp.knct.di.c6t.util.ActivityUtil;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import android.accounts.NetworkErrorException;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class RegistrationFormActivity extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration_form);

		ActivityUtil.setOnClickListener(this, this, new int[] {
				R.id.registration_form_submit,
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.registration_form_submit:
			setEnableSubmitButton(false);
			User user = extractUserData();
			new RegisteringTask().execute(user);
			break;

		default:
			break;
		}
	}

	private void setEnableSubmitButton(boolean b) {
		findViewById(R.id.registration_form_submit).setEnabled(b);
	}

	private User extractUserData() {
		ActivityUtil getter = new ActivityUtil(this);
		String name = getter.getText(R.id.registration_form_name);
		String area = getter.getText(R.id.registration_form_area);
		return new User(name, area);
	}

	private class RegisteringTask extends AsyncTask<User, String, User> {

		@Override
		protected User doInBackground(User... user) {
			BasicClient client = new BasicClient();
			try {
				return client.postUser(user[0]);
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
		}

		@Override
		protected void onProgressUpdate(String... progressText) {
			Toast.makeText(getApplicationContext(), progressText[0], Toast.LENGTH_SHORT).show();
		}

		@Override
		protected void onCancelled() {
			setEnableSubmitButton(true);
		}

		@Override
		protected void onPostExecute(User user) {
			try {
				Toast.makeText(getApplicationContext(), user.toJSON().toString(2), Toast.LENGTH_SHORT).show();
			}
			catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Toast.makeText(getApplicationContext(), "ÉÜÅ[ÉUÅ[ìoò^Ç™äÆóπÇµÇ‹ÇµÇΩ", Toast.LENGTH_SHORT).show();
			new BasicClient().saveUserToLocal(getApplicationContext(), user);
			finish();
		}
	}
}
