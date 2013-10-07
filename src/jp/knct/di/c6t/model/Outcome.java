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
	private static final String PHOTO_PATH = "photo_path";
	private static final String COMMENT = "comment";

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
		String photoPath = outcome.getString(PHOTO_PATH);
		String comment = outcome.getString(COMMENT);
		return new Outcome(exploration, questNumber, photoedAt, photoPath, comment);
	}

	private Exploration exploration;
	private int questNumber;
	private Date photoedAt;
	private String photoPath;
	private String comment;

	public Outcome(Exploration exploration, int questNumber, Date photoedAt, String photoPath) {
		this(exploration, questNumber, photoedAt, photoPath, "");
	}

	public Outcome(Exploration exploration, int questNumber, Date photoedAt, String photoPath, String comment) {
		setExploration(exploration);
		setQuestNumber(questNumber);
		setPhotoedAt(photoedAt);
		setPhotoPath(photoPath);
		setComment(comment);
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

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public Bitmap decodePhotoBitmap(int scale) {
		return ImageUtil.decodeBitmap(getPhotoPath(), scale);
	}

	public Route getRoute() {
		return getExploration().getRoute();
	}

	public Quest getQuest() {
		return getExploration().getRoute().getQuests().get(getQuestNumber());
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public JSONObject toJSON() {
		try {
			return new JSONObject()
					.put(ROUTE, getExploration().toJSON())
					.put(QUEST_NUMBER, getQuestNumber())
					.put(PHOTOED_AT, TimeUtil.format(getPhotoedAt()))
					.put(PHOTO_PATH, getPhotoPath())
					.put(COMMENT, getComment());
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
		dest.writeString(getPhotoPath());
		dest.writeString(getComment());
	};

	public static final Parcelable.Creator<Outcome> CREATOR = new Parcelable.Creator<Outcome>() {
		@Override
		public Outcome createFromParcel(Parcel source) {
			Exploration exploration = source.readParcelable(Exploration.class.getClassLoader());
			int questNumber = source.readInt();
			Date photoedAt = (Date) source.readSerializable();
			String photoPath = source.readString();
			String comment = source.readString();
			return new Outcome(exploration, questNumber, photoedAt, photoPath, comment);
		}

		@Override
		public Outcome[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Outcome[size];
		}
	};
}
