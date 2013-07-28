package jp.knct.di.c6t.util;

import jp.knct.di.c6t.model.Quest;
import jp.knct.di.c6t.model.Route;
import android.location.Location;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public class MapUtil {

	public static final LatLng TOKYO = new LatLng(35.4138, 139.4505);
	public static final CameraPosition INITIAL_CAMERA_POSITION =
			new CameraPosition(TOKYO, 0, 0, 0);

	// Cannot use CameraUpdateFactory when GoogleMap Object is not available.
	public static CameraUpdate getInitialiCameraUpdate() {
		return CameraUpdateFactory.newCameraPosition(INITIAL_CAMERA_POSITION);
	}

	public static Geofence createEventPointGeofence(String id, LatLng location) {
		return new Geofence.Builder()
				.setCircularRegion(location.latitude, location.longitude, 100)
				.setRequestId(id)
				.setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
				.setExpirationDuration(Geofence.NEVER_EXPIRE)
				.build();

	}

	public static LatLng parseLocation(String location) {
		String[] latlng = location.split(",");
		return new LatLng(
				Double.parseDouble(latlng[0]),
				Double.parseDouble(latlng[1]));
	}

	public static String serializeLocation(LatLng location) {
		return location.latitude + "," + location.longitude;
	}

	public static float[] calculateDistanceAndBearingToStartPoint(Location location, Route route) {
		float[] distanceAndBearing = { 0, 0 };

		Location.distanceBetween(
				location.getLatitude(),
				location.getLongitude(),
				route.getStartLocation().latitude,
				route.getStartLocation().longitude,
				distanceAndBearing);

		return distanceAndBearing;
	}

	public static float[] calculateDistanceAndBearingToQuestPoint(Location location, Quest quest) {
		float[] distanceAndBearing = { 0, 0 };

		Location.distanceBetween(
				location.getLatitude(),
				location.getLongitude(),
				quest.getLocation().latitude,
				quest.getLocation().longitude,
				distanceAndBearing);

		return distanceAndBearing;
	}

}
