package jp.knct.di.c6t.ui.route;

import jp.knct.di.c6t.IntentData;
import jp.knct.di.c6t.R;
import jp.knct.di.c6t.model.Quest;
import jp.knct.di.c6t.model.Route;
import jp.knct.di.c6t.util.ActivityUtil;
import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class RouteCreationActivity extends Activity
		implements OnClickListener,
		ConnectionCallbacks,
		OnConnectionFailedListener {

	private static final LatLng TOKYO = new LatLng(35.4138, 139.4505);
	private static final CameraPosition INITIAL_CAMERA_POSITION = new CameraPosition(TOKYO, 0, 0, 0);

	class QuestInfoWindowAdapter implements InfoWindowAdapter {
		private View mContents;
		private TextView mTitle;
		private ImageView mImage;

		private QuestInfoWindowAdapter() {
			mContents = getLayoutInflater().inflate(R.layout.info_content_route_creation_quest, null);
			mTitle = (TextView) mContents.findViewById(R.id.info_content_route_creation_quest_title);
			mImage = (ImageView) mContents.findViewById(R.id.info_content_route_creation_quest_image);
		}

		@Override
		public View getInfoContents(Marker marker) {
			if (marker.getTitle() != null) {
				return null;
			}

			int questNumber = Integer.parseInt(marker.getSnippet());
			Quest targetQuest = mRoute.getQuests().get(questNumber - 1);

			mTitle.setText(questNumber + ". " + targetQuest.getTitle());

			return mContents;
		}

		@Override
		public View getInfoWindow(Marker marker) {
			return null;
		}
	}

	private GoogleMap mMap;
	private LocationClient mLocationClient;
	private Route mRoute = new Route();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_route_creation);

		ActivityUtil.setOnClickListener(this, this, new int[] {
				R.id.route_creation_start,
				R.id.route_creation_create_new_quest,
				R.id.route_creation_quests,
				R.id.route_creation_finish,
		});

		setUpMap();

		mLocationClient = new LocationClient(this, this, this);
		mLocationClient.connect();

		updateMap();
	}

	private void setUpMap() {
		mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.route_creation_map))
				.getMap();
		mMap.setMyLocationEnabled(true);
		mMap.setInfoWindowAdapter(new QuestInfoWindowAdapter());

		mMap.moveCamera(CameraUpdateFactory.newCameraPosition(INITIAL_CAMERA_POSITION));

		// TODO
		// mMap.setOnInfoWindowClickListener(this);
	}

	private MarkerOptions createStartPointMarker(Route route) {
		LatLng startLocation = route.getStartLocation();
		return new MarkerOptions()
				.position(startLocation)
				.title("スタートポイント");
	}

	private MarkerOptions createQuestPointMarker(Quest quest, int questNumber) {
		LatLng location = quest.getLocation();
		return new MarkerOptions()
				.position(location)
				.snippet("" + questNumber); // for QuestInfoWindowAdapter
	}

	private void updateMap() {
		mMap.clear();

		if (mRoute.getStartLocation() != null) {
			mMap.addMarker(createStartPointMarker(mRoute));
		}

		int questNumber = 1;
		for (Quest quest : mRoute.getQuests()) {
			mMap.addMarker(createQuestPointMarker(quest, questNumber));
			questNumber++;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == RouteCreationQuestFormActivity.REQUEST_CODE_EDIT_QUEST &&
				resultCode == RESULT_OK) {
			Quest quest = data.getParcelableExtra(IntentData.EXTRA_KEY_BASE_QUEST);
			mRoute.addQuest(quest);

			if (mRoute.getQuests().size() == 5) {
				Toast.makeText(this, "終了", 0).show();
				// TODO
			}
			else if (mRoute.getQuests().size() > 5) {
				// TODO

			}
		}

		if (requestCode == RouteCreationDetailFormActivity.REQUEST_CODE_EDIT_ROUTE_DETAIL &&
				resultCode == RESULT_OK) {
			finish();
			return;
		}

		updateMap();
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.route_creation_start:
			mRoute.setStartLocation(getCurrentLocation());
			updateMap();
			Toast.makeText(this, "現在地点をスタートポイントに設定しました。", Toast.LENGTH_SHORT).show();
			break;

		case R.id.route_creation_create_new_quest:
			Quest newQuest = new Quest(getCurrentLocation());
			intent = new Intent(this, RouteCreationQuestFormActivity.class)
					.putExtra(IntentData.EXTRA_KEY_BASE_QUEST, newQuest);
			startActivityForResult(intent, RouteCreationQuestFormActivity.REQUEST_CODE_EDIT_QUEST);
			break;

		case R.id.route_creation_quests:
			// TODO
			break;

		case R.id.route_creation_finish:
			intent = new Intent(this, RouteCreationDetailFormActivity.class)
					.putExtra(IntentData.EXTRA_KEY_ROUTE, mRoute);
			startActivityForResult(intent, RouteCreationDetailFormActivity.REQUEST_CODE_EDIT_ROUTE_DETAIL);
			break;

		// case R.id.route_creation_debug_route_creation_updatequests:
		// break;

		default:
			break;
		}
	}

	private LatLng getCurrentLocation() {
		Location lastLocation = mLocationClient.getLastLocation();
		double latitude = lastLocation.getLatitude();
		double longitude = lastLocation.getLongitude();
		return new LatLng(latitude, longitude);
	}

	@Override
	public void onConnected(Bundle bundle) {
		CameraUpdate update = CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
				.target(getCurrentLocation())
				.zoom(mMap.getMaxZoomLevel())
				.build());
		mMap.animateCamera(update, 0, null);
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		// TODO Auto-generated method stub
	}
}
