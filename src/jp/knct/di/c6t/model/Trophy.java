package jp.knct.di.c6t.model;

import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import jp.knct.di.c6t.util.TimeUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Trophy implements Parcelable {

	private static final String EXPLORATIOIN = "exploration";
	private static final String ACHIEVED_AT = "achieved_at";
	private static final String PHOTO_URI = "photo_uri";

	public static List<Trophy> parseTrophys(JSONArray trophies) throws JSONException, ParseException {
		List<Trophy> trophyList = new LinkedList<Trophy>();
		for (int i = 0; i < trophies.length(); i++) {
			Trophy trophy = parseJSON(trophies.getJSONObject(i));
			trophyList.add(trophy);
		}
		return trophyList;
	}

	public static JSONArray convertTrophysToJsonArray(List<Trophy> trophies) {
		JSONArray trophyList = new JSONArray();
		for (Trophy trophy : trophies) {
			trophyList.put(trophy.toJSON());
		}
		return trophyList;
	}

	public static Trophy parseJSONString(String trophyString) throws JSONException, ParseException {
		return parseJSON(new JSONObject(trophyString));
	}

	public static Trophy parseJSON(JSONObject trophy) throws JSONException, ParseException {
		Exploration exploration = Exploration.parseJSON(trophy.getJSONObject(EXPLORATIOIN));
		Date achievedAt = TimeUtil.parse(trophy.getString(ACHIEVED_AT));
		String photoUri = trophy.getString(PHOTO_URI);
		return new Trophy(exploration, achievedAt, photoUri);
	}

	private Exploration exploration;
	private Date achievedAt;
	private String photoUri;

	public Trophy(Exploration exploration, Date achievedAt, String photoUri) {
		setExploration(exploration);
		setAchievedAt(achievedAt);
		setPhotoUri(photoUri);
	}

	public Exploration getExploration() {
		return exploration;
	}

	public void setExploration(Exploration exploration) {
		this.exploration = exploration;
	}

	public Date getAchievedAt() {
		return achievedAt;
	}

	public void setAchievedAt(Date achievedAt) {
		this.achievedAt = achievedAt;
	}

	public String getPhotoUri() {
		return photoUri;
	}

	public void setPhotoUri(String photoUri) {
		this.photoUri = photoUri;
	}

	public JSONObject toJSON() {
		try {
			return new JSONObject()
					.put(EXPLORATIOIN, getExploration().toJSON())
					.put(ACHIEVED_AT, TimeUtil.format(getAchievedAt()))
					.put(PHOTO_URI, getPhotoUri());
		}
		catch (JSONException e) {
			e.printStackTrace();
			Log.d("Trophy", "JSON Error!!!!!!!!!!!!!!!!");
			return null;
		}
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 4;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(getExploration(), -1);
		dest.writeSerializable(getAchievedAt());
		dest.writeString(getPhotoUri());
	};

	public static final Parcelable.Creator<Trophy> CREATOR = new Parcelable.Creator<Trophy>() {
		@Override
		public Trophy createFromParcel(Parcel source) {
			Exploration exploration = source.readParcelable(Exploration.class.getClassLoader());
			Date achievedAt = (Date) source.readSerializable();
			String photoUri = source.readString();
			return new Trophy(exploration, achievedAt, photoUri);
		}

		@Override
		public Trophy[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Trophy[size];
		}
	};

}
