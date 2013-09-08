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

public class Exploration implements Parcelable {

	public static final int QUEST_NUMBER_NOT_STARTED = -1;
	public static final int QUEST_NUMBER_FINISHED = -2;

	private static final String ID = "id";
	private static final String HOST = "host";
	private static final String ROUTE = "route";
	private static final String START_TIME = "start_time";
	private static final String DESCRIPTION = "description";
	private static final String MEMBERS = "members";
	private static final String PHOTOGRAPHED = "photographed";
	private static final String CURRENT_QUEST_NUMBER = "current_quest_number";
	private static final String CURRENT_MISSION_COMPLETED_NUMBER_COUNT = "current_mission_completed_number_count";

	public static List<Exploration> parseExplorations(JSONArray explorations) throws JSONException, ParseException {
		List<Exploration> explorationList = new LinkedList<Exploration>();
		for (int i = 0; i < explorations.length(); i++) {
			Exploration exploration = parseJSON(explorations.getJSONObject(i));
			explorationList.add(exploration);
		}
		return explorationList;
	}

	public static Exploration parseJSONString(String explorationString) throws JSONException, ParseException {
		return parseJSON(new JSONObject(explorationString));
	}

	public static Exploration parseJSON(JSONObject exploration) throws JSONException, ParseException {
		int id = exploration.getInt(ID); // TODO: case: undefined
		User host = User.parseJSON(exploration.getJSONObject(HOST));
		Route route = Route.parseJSON(exploration.getJSONObject(ROUTE));
		Date startTime = TimeUtil.parse(exploration.getString(START_TIME));
		String description = exploration.getString(DESCRIPTION);
		List<User> menbers = User.parseUsers(exploration.getJSONArray(MEMBERS));
		boolean photographed = exploration.getBoolean(PHOTOGRAPHED);
		int questNumber = exploration.getInt(CURRENT_QUEST_NUMBER);
		int missionCompletedNumber = exploration.getInt(CURRENT_MISSION_COMPLETED_NUMBER_COUNT);
		return new Exploration(host, route, startTime, description, menbers, id, photographed, questNumber, missionCompletedNumber);
	}

	private int id = -1;
	private User host;
	private Route route;
	private Date startTime;
	private String description;
	private List<User> menbers;
	private boolean photographed = false;
	private int questNumber = QUEST_NUMBER_NOT_STARTED;
	private int missionCompletedNumber = 0;

	public Exploration(User host, Route route, Date startTime, String description, List<User> menbers, int id, boolean photographed, int questNumber, int missioncompletedNumber) {
		setId(id);
		setHost(host);
		setRoute(route);
		setStartTime(startTime);
		setDescription(description);
		setMembers(menbers);
		setPhotographed(photographed);
		setQuestNumber(questNumber);
		setMissionCompletedNumber(missioncompletedNumber);
	}

	public Exploration(User host, Route route, Date startTime, String description, List<User> menbers, int id) {
		this(host, route, startTime, description, menbers, -1, false, QUEST_NUMBER_NOT_STARTED, 0);
	}

	public Exploration(User host, Route route, Date startTime, String description, List<User> menbers) {
		this(host, route, startTime, description, menbers, -1);
	}

	public Exploration() {
		this(null, null, null, null, new LinkedList<User>());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getHost() {
		return host;
	}

	public void setHost(User host) {
		this.host = host;
	}

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<User> getMembers() {
		return menbers;
	}

	public void setMembers(List<User> menbers) {
		this.menbers = menbers;
	}

	public boolean isPhotographed() {
		return photographed;
	}

	public void setPhotographed(boolean photographed) {
		this.photographed = photographed;
	}

	public int getQuestNumber() {
		return questNumber;
	}

	public void setQuestNumber(int questNumber) {
		this.questNumber = questNumber;
	}

	public JSONObject toJSON() {
		try {
			return new JSONObject()
					.put(ID, getId())
					.put(HOST, getHost().toJSON())
					.put(ROUTE, getRoute().toJSON())
					.put(START_TIME, TimeUtil.format(getStartTime()))
					.put(DESCRIPTION, getDescription())
					.put(MEMBERS, User.convertUsersToJsonArray(getMembers()))
					.put(PHOTOGRAPHED, isPhotographed())
					.put(CURRENT_QUEST_NUMBER, getQuestNumber())
					.put(CURRENT_MISSION_COMPLETED_NUMBER_COUNT, getMissionCompletedNumber());
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

	public boolean isStarted() {
		return getQuestNumber() != QUEST_NUMBER_NOT_STARTED;
	}

	public boolean isFinished() {
		return getQuestNumber() == QUEST_NUMBER_FINISHED;
	}

	public int getMissionCompletedNumber() {
		return missionCompletedNumber;
	}

	public void setMissionCompletedNumber(int missionCompletedNumber) {
		this.missionCompletedNumber = missionCompletedNumber;
	}

	public boolean isHost(User user) {
		return user.getId() == getHost().getId();
	}

	public boolean isMissionCompleted() {
		return getMembers().size() >= getMissionCompletedNumber();
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(getId());
		dest.writeParcelable(getHost(), -1);
		dest.writeParcelable(getRoute(), -1);
		dest.writeSerializable(getStartTime());
		dest.writeString(getDescription());
		dest.writeTypedList(getMembers());
		dest.writeByte((byte) (isPhotographed() ? 1 : 0));
		dest.writeInt(getQuestNumber());
		dest.writeInt(getMissionCompletedNumber());
	}

	public static final Parcelable.Creator<Exploration> CREATOR = new Parcelable.Creator<Exploration>() {
		@Override
		public Exploration createFromParcel(Parcel source) {
			int id = source.readInt();
			User host = source.readParcelable(User.class.getClassLoader());
			Route route = source.readParcelable(Route.class.getClassLoader());
			Date startTime = (Date) source.readSerializable();
			String description = source.readString();
			List<User> users = new LinkedList<User>();
			source.readTypedList(users, User.CREATOR);
			boolean photographed = source.readByte() == 1;
			int questNumber = source.readInt();
			int missionCompletedNumber = source.readInt();
			return new Exploration(host, route, startTime, description, users, id, photographed, questNumber, missionCompletedNumber);
		}

		@Override
		public Exploration[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Exploration[size];
		}
	};
}
