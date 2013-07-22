package jp.knct.di.c6t.util;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public class MapUtil {

	public static final LatLng TOKYO = new LatLng(35.4138, 139.4505);
	public static final CameraPosition INITIAL_CAMERA_POSITION =
			new CameraPosition(TOKYO, 0, 0, 0);
	public static final CameraUpdate INITIAL_CAMERA_UPDATE =
			CameraUpdateFactory.newCameraPosition(INITIAL_CAMERA_POSITION);

	public static Geofence createEventPointGeofence(String id, LatLng location) {
		return new Geofence.Builder()
				.setCircularRegion(location.latitude, location.longitude, 100)
				.setRequestId(id)
				.setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
				.setExpirationDuration(Geofence.NEVER_EXPIRE)
				.build();

	}
}
