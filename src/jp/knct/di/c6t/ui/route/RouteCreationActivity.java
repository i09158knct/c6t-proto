package jp.knct.di.c6t.ui.route;

import jp.knct.di.c6t.IntentData;
import jp.knct.di.c6t.R;
import jp.knct.di.c6t.model.Quest;
import jp.knct.di.c6t.model.Route;
import jp.knct.di.c6t.util.ActivityUtil;
import jp.knct.di.c6t.util.ImageUtil;
import jp.knct.di.c6t.util.MapUtil;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class RouteCreationActivity extends Activity
		implements OnClickListener,
		ConnectionCallbacks,
		OnConnectionFailedListener,
		OnInfoWindowClickListener {

	private static int getQuestNumber(Marker marker) {
		return Integer.parseInt(marker.getSnippet());
	}

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

			int questNumber = getQuestNumber(marker);
			Quest targetQuest = mRoute.getQuests().get(questNumber);

			mTitle.setText("クエストポイント" + (questNumber + 1));
			mImage.setImageBitmap(ImageUtil.decodeBitmap(targetQuest.getImage(), 10));

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

		mMap.moveCamera(MapUtil.INITIAL_CAMERA_UPDATE);

		// TODO
		mMap.setOnInfoWindowClickListener(this);
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

		int questNumber = 0;
		for (Quest quest : mRoute.getQuests()) {
			mMap.addMarker(createQuestPointMarker(quest, questNumber));
			questNumber++;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == RouteCreationQuestFormActivity.REQUEST_CODE_CREATE_QUEST &&
				resultCode == RESULT_OK) {
			Quest quest = data.getParcelableExtra(IntentData.EXTRA_KEY_QUEST);
			mRoute.addQuest(quest);

			if (mRoute.getQuests().size() == 5) {
				findViewById(R.id.route_creation_create_new_quest).setVisibility(View.GONE);
				if (mRoute.getStartLocation() != null) {
					findViewById(R.id.route_creation_finish).setVisibility(View.VISIBLE);
				}
			}
		}

		if (requestCode == RouteCreationQuestFormActivity.REQUEST_CODE_EDIT_QUEST &&
				resultCode == RESULT_OK) {
			int questNumber = data.getIntExtra(IntentData.EXTRA_KEY_QUEST_NUMBER, -1);
			Quest quest = data.getParcelableExtra(IntentData.EXTRA_KEY_QUEST);

			if (quest == null) {
				mRoute.getQuests().remove(questNumber);
				findViewById(R.id.route_creation_create_new_quest).setVisibility(View.VISIBLE);
				findViewById(R.id.route_creation_finish).setVisibility(View.GONE);
			}
			else {
				mRoute.getQuests().set(questNumber, quest);
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
			if (mRoute.getQuests().size() == 5) {
				findViewById(R.id.route_creation_finish).setVisibility(View.VISIBLE);
			}
			break;

		case R.id.route_creation_create_new_quest:
			Quest newQuest = new Quest(getCurrentLocation());
			intent = new Intent(this, RouteCreationQuestFormActivity.class)
					.putExtra(IntentData.EXTRA_KEY_QUEST, newQuest);
			startActivityForResult(intent, RouteCreationQuestFormActivity.REQUEST_CODE_CREATE_QUEST);
			break;

		case R.id.route_creation_quests:
			// TODO
			break;

		case R.id.route_creation_finish:
			intent = new Intent(this, RouteCreationDetailFormActivity.class)
					.putExtra(IntentData.EXTRA_KEY_ROUTE, mRoute);
			startActivityForResult(intent, RouteCreationDetailFormActivity.REQUEST_CODE_EDIT_ROUTE_DETAIL);
			break;

		default:
			break;
		}
	}

	private LatLng getCurrentLocation() {
		Location lastLocation = mLocationClient.getLastLocation();

		if (lastLocation == null) {
			lastLocation = mMap.getMyLocation();
		}

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

		mMap.animateCamera(update);
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onInfoWindowClick(Marker marker) {
		if (marker.getTitle() != null) {
			return;
		}

		int questNumber = getQuestNumber(marker);
		Quest quest = mRoute.getQuests().get(questNumber);
		final Intent intent = new Intent(RouteCreationActivity.this, RouteCreationQuestFormActivity.class)
				.putExtra(IntentData.EXTRA_KEY_QUEST, quest)
				.putExtra(IntentData.EXTRA_KEY_QUEST_NUMBER, questNumber)
				.putExtra(IntentData.EXTRA_KEY_REQUEST_CODE, RouteCreationQuestFormActivity.REQUEST_CODE_EDIT_QUEST);

		new AlertDialog.Builder(this)
				.setMessage("クエストを編集しますか？")
				.setCancelable(true)
				.setPositiveButton("はい", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						startActivityForResult(intent, RouteCreationQuestFormActivity.REQUEST_CODE_EDIT_QUEST);
					}
				})
				.setNegativeButton("いいえ", null)
				.show();

	}
}
