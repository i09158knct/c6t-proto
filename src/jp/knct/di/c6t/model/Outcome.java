package jp.knct.di.c6t.model;

import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import jp.knct.di.c6t.util.ImageUtil;
import jp.knct.di.c6t.util.TimeUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Outcome implements Parcelable {

	private static final String ROUTE = "exploration";
	private static final String QUEST_NUMBER = "quest_number";
	private static final String PHOTOED_AT = "photoed_at";
	private static final String PHOTO_URI = "photo_uri";

	public static List<Outcome> parseOutcomes(JSONArray outcomes) throws JSONException, ParseException {
		List<Outcome> outcomeList = new LinkedList<Outcome>();
		for (int i = 0; i < outcomes.length(); i++) {
			Outcome outcome = parseJSON(outcomes.getJSONObject(i));
			outcomeList.add(outcome);
		}
		return outcomeList;
	}

	public static JSONArray convertOutcomesToJsonArray(List<Outcome> outcomes) {
		JSONArray outcomeList = new JSONArray();
		for (Outcome outcome : outcomes) {
			outcomeList.put(outcome.toJSON());
		}
		return outcomeList;
	}

	public static Outcome parseJSONString(String outcomeString) throws JSONException, ParseException {
		return parseJSON(new JSONObject(outcomeString));
	}

	public static Outcome parseJSON(JSONObject outcome) throws JSONException, ParseException {
		Exploration exploration = Exploration.parseJSON(outcome.getJSONObject(ROUTE));
		int questNumber = outcome.getInt(QUEST_NUMBER);
		Date photoedAt = TimeUtil.parse(outcome.getString(PHOTOED_AT));
		String photoUri = outcome.getString(PHOTO_URI);
		return new Outcome(exploration, questNumber, photoedAt, photoUri);
	}

	private Exploration exploration;
	private int questNumber;
	private Date photoedAt;
	private String photoUri;

	public Outcome(Exploration exploration, int questNumber, Date photoedAt, String photoUri) {
		setExploration(exploration);
		setQuestNumber(questNumber);
		setPhotoedAt(photoedAt);
		setPhotoUri(photoUri);
	}

	public Exploration getExploration() {
		return exploration;
	}

	public void setExploration(Exploration exploration) {
		this.exploration = exploration;
	}

	public int getQuestNumber() {
		return questNumber;
	}

	public void setQuestNumber(int questNumber) {
		this.questNumber = questNumber;
	}

	public Date getPhotoedAt() {
		return photoedAt;
	}

	public void setPhotoedAt(Date photoedAt) {
		this.photoedAt = photoedAt;
	}

	public String getPhotoUri() {
		return photoUri;
	}

	public void setPhotoUri(String photoUri) {
		this.photoUri = photoUri;
	}

	public Bitmap decodePhotoBitmap(int scale) {
		return ImageUtil.decodeBitmap(getPhotoUri(), scale);
	}

	public Route getRoute() {
		return getExploration().getRoute();
	}

	public Quest getQuest() {
		return getExploration().getRoute().getQuests().get(getQuestNumber());
	}

	public JSONObject toJSON() {
		try {
			return new JSONObject()
					.put(ROUTE, getExploration().toJSON())
					.put(QUEST_NUMBER, getQuestNumber())
					.put(PHOTOED_AT, TimeUtil.format(getPhotoedAt()))
					.put(PHOTO_URI, getPhotoUri());
		}
		catch (JSONException e) {
			e.printStackTrace();
			Log.d("Outcome", "JSON Error!!!!!!!!!!!!!!!!");
			return null;
		}
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(getExploration(), -1);
		dest.writeInt(getQuestNumber());
		dest.writeSerializable(getPhotoedAt());
		dest.writeString(getPhotoUri());
	};

	public static final Parcelable.Creator<Outcome> CREATOR = new Parcelable.Creator<Outcome>() {
		@Override
		public Outcome createFromParcel(Parcel source) {
			Exploration exploration = source.readParcelable(Exploration.class.getClassLoader());
			int questNumber = source.readInt();
			Date photoedAt = (Date) source.readSerializable();
			String photoUri = source.readString();
			return new Outcome(exploration, questNumber, photoedAt, photoUri);
		}

		@Override
		public Outcome[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Outcome[size];
		}
	};
}
