package jp.knct.di.c6t.model;

import java.util.LinkedList;
import java.util.List;

import jp.knct.di.c6t.util.ImageUtil;
import jp.knct.di.c6t.util.MapUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

public class Quest implements Parcelable {

	private static final String IMAGE = "image";
	private static final String MISSION = "mission";
	private static final String POSE = "pose";
	private static final String LOCATION = "location";

	public static List<Quest> parseQuests(JSONArray quests) throws JSONException {
		List<Quest> questList = new LinkedList<Quest>();
		for (int i = 0; i < quests.length(); i++) {
			Quest quest = parseJSON(quests.getJSONObject(i));
			questList.add(quest);
		}
		return questList;
	}

	public static JSONArray convertQuestsToJsonArray(List<Quest> quests) {
		JSONArray questList = new JSONArray();
		for (Quest quest : quests) {
			questList.put(quest.toJSON());
		}
		return questList;
	}

	public static Quest parseJSONString(String questString) throws JSONException {
		return parseJSON(new JSONObject(questString));
	}

	public static Quest parseJSON(JSONObject quest) throws JSONException {
		LatLng location = MapUtil.parseLocation(quest.getString(LOCATION));
		String pose = quest.getString(POSE);
		String mission = quest.getString(MISSION);
		String image = quest.getString(IMAGE);
		return new Quest(location, pose, mission, image);
	}

	private String pose;
	private String mission;
	private String image;
	private double latitude;
	private double longitude;

	public Quest(LatLng location, String pose, String mission, String image) {
		setLocation(location);
		setPose(pose);
		setMission(mission);
		setImage(image); // FIXME: image type
	}

	public Quest(LatLng location) {
		this(location, "", "", "");
	}

	public LatLng getLocation() {
		return new LatLng(latitude, longitude);
	}

	public void setLocation(LatLng location) {
		latitude = location.latitude;
		longitude = location.longitude;
	}

	public String getPose() {
		return pose;
	}

	public void setPose(String pose) {
		this.pose = pose;
	}

	public String getMission() {
		return mission;
	}

	public void setMission(String mission) {
		this.mission = mission;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Bitmap decodeImageBitmap(int scale) {
		return ImageUtil.decodeBitmap(getImage(), scale);
	}

	public JSONObject toJSON() {
		try {
			return new JSONObject()
					.put(LOCATION, MapUtil.serializeLocation(getLocation()))
					.put(POSE, getPose())
					.put(MISSION, getMission())
					.put(IMAGE, getImage());
		}
		catch (JSONException e) {
			e.printStackTrace();
			Log.d("Quest", "JSON Error!!!!!!!!!!!!!!!!");
			return null;
		}
	}

	public boolean isValid() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeDouble(getLocation().latitude);
		dest.writeDouble(getLocation().longitude);
		dest.writeString(getPose());
		dest.writeString(getMission());
		dest.writeString(getImage());
	};

	public static final Parcelable.Creator<Quest> CREATOR = new Parcelable.Creator<Quest>() {
		@Override
		public Quest createFromParcel(Parcel source) {
			Double latitude = source.readDouble();
			Double longitude = source.readDouble();
			String pose = source.readString();
			String mission = source.readString();
			String image = source.readString();
			LatLng location = new LatLng(latitude, longitude);
			return new Quest(location, pose, mission, image);
		}

		@Override
		public Quest[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Quest[size];
		}
	};
}
