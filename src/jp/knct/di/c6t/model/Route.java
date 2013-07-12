package jp.knct.di.c6t.model;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

public class Route {
	private static final String NAME = "name";
	private static final String START_LOCATION = "start_location";
	private static final String DESCRIPTION = "description";
	private static final String QUESTS = "quests";

	public static Route parseJSONString(String route) throws JSONException {
		return parseJSON(new JSONObject(route));
	}

	public static Route parseJSON(JSONObject routeJSON) throws JSONException {
		String name = routeJSON.getString(NAME);
		LatLng location = Quest.parseLocation(routeJSON.getString(START_LOCATION));
		String description = routeJSON.getString(DESCRIPTION);
		List<Quest> quests = Quest.parseQuests(routeJSON.getJSONArray(QUESTS));
		return new Route(location, name, description, quests);
	}

	private String name;
	private String description;
	private LatLng startLocation;
	private List<Quest> quests;

	public Route(LatLng startLocation, String name, String description, List<Quest> quests) {
		this.setStartLocation(startLocation);
		this.setName(name);
		this.setDescription(description);
		this.setQuests(quests);
	}

	public Route() {
		this(null, "", "", new LinkedList<Quest>());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LatLng getStartLocation() {
		return startLocation;
	}

	public void setStartLocation(LatLng startLocation) {
		this.startLocation = startLocation;
	}

	public List<Quest> getQuests() {
		return quests;
	}

	public void setQuests(List<Quest> quests) {
		this.quests = quests;
	}

	public void addQuest(Quest quest) {
		this.quests.add(quest);
	}

	public JSONObject toJSON() {
		try {
			return new JSONObject()
					.put(NAME, getName())
					.put(START_LOCATION, Quest.serializeLocation(getStartLocation()))
					.put(DESCRIPTION, getDescription())
					.put(QUESTS, Quest.convertQuestsToJsonArray(getQuests()));
		}
		catch (JSONException e) {
			e.printStackTrace();
			Log.d("Route", "JSON Error!!!!!!!!!!!!!!!!");
			return null;
		}
	}

	public boolean isValid() {
		// TODO Auto-generated method stub
		return true;
	}

}
