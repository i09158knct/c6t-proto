package jp.knct.di.c6t.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Exploration implements Parcelable {

	private static final String HOST = "host";
	private static final String ROUTE = "route";
	private static final String START_TIME = "start_time";
	private static final String DESCRIPTION = "description";
	private static final String PARTICIPANTS = "participants";

	public static Exploration parseJSONString(String explorationString) throws JSONException, ParseException {
		return parseJSON(new JSONObject(explorationString));
	}

	public static Exploration parseJSON(JSONObject exploration) throws JSONException, ParseException {
		User host = User.parseJSON(exploration.getJSONObject(HOST));
		Route route = Route.parseJSON(exploration.getJSONObject(ROUTE));
		Date startTime = new SimpleDateFormat().parse(exploration.getString(START_TIME));
		String description = exploration.getString(DESCRIPTION);
		List<User> participants = User.parseUsers(exploration.getJSONArray(PARTICIPANTS));
		return new Exploration(host, route, startTime, description, participants);
	}

	private User host;
	private Route route;
	private Date startTime;
	private String description;
	private List<User> participants;

	public Exploration(User host, Route route, Date startTime, String description, List<User> participants) {
		setHost(host);
		setRoute(route);
		setStartTime(startTime);
		setDescription(description);
		setParticipants(participants);
	}

	public Exploration() {
		this(null, null, null, null, new LinkedList<User>());
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

	public List<User> getParticipants() {
		return participants;
	}

	public void setParticipants(List<User> participants) {
		this.participants = participants;
	}

	public JSONObject toJSON() {
		try {
			return new JSONObject()
					.put(HOST, getHost().toJSON())
					.put(ROUTE, getRoute().toJSON())
					.put(START_TIME, new SimpleDateFormat().format(getStartTime()))
					.put(DESCRIPTION, getDescription())
					.put(PARTICIPANTS, User.convertUsersToJsonArray(getParticipants()));
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
		return 2;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(getHost(), -1);
		dest.writeParcelable(getRoute(), -1);
		dest.writeString(new SimpleDateFormat().format(startTime));
		dest.writeString(getDescription());
		dest.writeTypedList(getParticipants());
	}

	public static final Parcelable.Creator<Exploration> CREATOR = new Parcelable.Creator<Exploration>() {
		@Override
		public Exploration createFromParcel(Parcel source) {
			try {
				User host = source.readParcelable(User.class.getClassLoader());
				Route route = source.readParcelable(Route.class.getClassLoader());
				Date startTime = new SimpleDateFormat().parse(source.readString());
				String description = source.readString();
				List<User> users = new LinkedList<User>();
				source.readTypedList(users, User.CREATOR);
				return new Exploration(host, route, startTime, description, users);
			}
			catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}

		@Override
		public Exploration[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Exploration[size];
		}
	};
}
