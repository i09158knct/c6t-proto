package jp.knct.di.c6t.ui.exploration;

import jp.knct.di.c6t.IntentData;
import jp.knct.di.c6t.R;
import jp.knct.di.c6t.model.Exploration;
import jp.knct.di.c6t.model.Route;
import jp.knct.di.c6t.util.ActivityUtil;
import jp.knct.di.c6t.util.MapUtil;
import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

public class ExplorationStartActivity extends Activity
		implements OnClickListener,
		ConnectionCallbacks,
		OnConnectionFailedListener {
	private GoogleMap mMap;
	private Exploration mExploration;
	private LocationClient mLocationClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exploration_start);

		mExploration = getIntent().getParcelableExtra(IntentData.EXTRA_KEY_EXPLORATION);

		ActivityUtil.setOnClickListener(this, this, new int[] {});

		setUpMap();

		mLocationClient = new LocationClient(this, this, this);
		mLocationClient.connect();

	}

	private void setUpMap() {
		mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.exploration_start_map))
				.getMap();
		mMap.setMyLocationEnabled(true);

		mMap.moveCamera(MapUtil.INITIAL_CAMERA_UPDATE);

		MarkerOptions startPoint = createStartPointMarker(mExploration.getRoute());
		mMap.addMarker(startPoint);
	}

	private MarkerOptions createStartPointMarker(Route route) {
		LatLng startLocation = route.getStartLocation();
		return new MarkerOptions()
				.position(startLocation)
				.title("スタートポイント");
	}

	private LatLng getCurrentLocation() {
		Location lastLocation = mLocationClient.getLastLocation();
		double latitude = lastLocation.getLatitude();
		double longitude = lastLocation.getLongitude();
		return new LatLng(latitude, longitude);
	}

	@Override
	public void onClick(View v) {
	}

	@Override
	public void onConnected(Bundle bundle) {
		LatLngBounds bounds = new LatLngBounds.Builder()
				.include(getCurrentLocation())
				.include(mExploration.getRoute().getStartLocation())
				.build();
		final CameraUpdate update = CameraUpdateFactory.newLatLngBounds(bounds, 200);

		// Cannot zoom to bounds until the map has a size.
		// http://stackoverflow.com/questions/13692579/movecamera-with-cameraupdatefactory-newlatlngbounds-crashes
		mMap.setOnCameraChangeListener(new OnCameraChangeListener() {
			@Override
			public void onCameraChange(CameraPosition arg0) {
				mMap.animateCamera(update);
				mMap.setOnCameraChangeListener(null);
			}
		});
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
