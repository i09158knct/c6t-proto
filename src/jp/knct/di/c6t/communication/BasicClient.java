package jp.knct.di.c6t.communication;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import jp.knct.di.c6t.model.Exploration;
import jp.knct.di.c6t.model.Route;
import jp.knct.di.c6t.model.User;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;

public class BasicClient {
	private static final String PREFERENCE_KEY_USER_ID = "user_id";
	private static final String PREFERENCE_KEY_USER_NAME = "user_name";
	private static final String PREFERENCE_KEY_USER_AREA = "user_area";

	private static final String SERVER_URL = "http://192.168.11.2:8080/";
	private static final String EXPLORATIONS_URL = SERVER_URL + "explorations/";
	private static final String NEW_EXPLORATION_URL = SERVER_URL + "explorations/new";
	private static final String ROUTES_URL = SERVER_URL + "routes/";
	private static final String NEW_ROUTE_URL = SERVER_URL + "routes/new";
	private static final String USERS_URL = SERVER_URL + "users/";
	private static final String NEW_USER_URL = SERVER_URL + "users/new";

	public static class SearchRouteParams {
		public static final String SCOPE_TITLE = "title";
		public static final String SCOPE_DESCRIPTION = "description";
		public static final String SCOPE_USER_NAME = "user_name";
		public static final String SCOPE_LOCATION = "location";
		public static final String ORDER_DESC = "desc";
		public static final String ORDER_ASC = "asc";
	}

	public static class SearchExplorationParams {
		public static final String SCOPE_ROUTE_TITLE = "route_title";
		public static final String SCOPE_ROUTE_ID = "route_id";
		public static final String SCOPE_USER_NAME = "user_name";
		public static final String ORDER_DESC = "desc";
		public static final String ORDER_ASC = "asc";
	}

	public static HttpResponse putJSONObject(String url, JSONObject object)
			throws ClientProtocolException, IOException {
		HttpPut request = new HttpPut(url);
		StringEntity entity = new StringEntity(object.toString());
		request.setEntity(entity);
		request.setHeader("Accept", "application/json");
		request.setHeader("Content-type", "application/json");

		HttpClient client = new DefaultHttpClient();
		return client.execute(request);
	}

	public static HttpResponse postJSONObject(String url, JSONObject object)
			throws ClientProtocolException, IOException {
		HttpPost request = new HttpPost(url);
		StringEntity entity = new StringEntity(object.toString());
		request.setEntity(entity);
		request.setHeader("Accept", "application/json");
		request.setHeader("Content-type", "application/json");

		HttpClient client = new DefaultHttpClient();
		return client.execute(request);
	}

	private static JSONObject getJsonObject(String url)
			throws ClientProtocolException, JSONException, IOException {
		return new JSONObject(getString(url));
	}

	private static JSONArray getJsonArray(String url) throws ClientProtocolException, JSONException, IOException {
		return new JSONArray(getString(url));
	}

	private static String getString(String url) throws ClientProtocolException, IOException {
		HttpGet request = new HttpGet(url);
		HttpClient client = new DefaultHttpClient();
		HttpResponse response = client.execute(request);
		return extractEntity(response);
	}

	private static String extractEntity(HttpResponse response) throws IOException, ClientProtocolException {
		ByteArrayOutputStream outstream = new ByteArrayOutputStream();
		response.getEntity().writeTo(outstream);
		return outstream.toString();
	}

	/*
	 * Exploration
	 * ===========
	 */

	// GET /explorations/
	public List<Exploration> getExplorations(User user)
			throws ClientProtocolException, JSONException, IOException, ParseException {
		JSONArray explorationsJSON = getJsonArray(Uri.parse(EXPLORATIONS_URL).buildUpon()
				.appendQueryParameter("user_id", "" + user.getId())
				.build()
				.toString());
		return Exploration.parseExplorations(explorationsJSON);
	}

	// GET /explorations/
	public List<Exploration> getExplorations(String searchText)
			throws ClientProtocolException, JSONException, IOException, ParseException {
		JSONArray explorationsJSON = getJsonArray(Uri.parse(EXPLORATIONS_URL).buildUpon()
				.appendQueryParameter("query", searchText)
				.build()
				.toString());
		return Exploration.parseExplorations(explorationsJSON);
	}

	// GET /explorations/:id
	// GET /explorations/1
	public Exploration getExploration(int id)
			throws ClientProtocolException, JSONException, IOException, ParseException {
		JSONObject explorationJSON = getJsonObject(Uri.parse(EXPLORATIONS_URL).buildUpon()
				.appendPath("" + id)
				.build()
				.toString());
		return Exploration.parseJSON(explorationJSON);
	}

	// POST /explorations/new
	public HttpResponse postExploration(Exploration exploration)
			throws ClientProtocolException, IOException, JSONException, ParseException {
		return postJSONObject(NEW_EXPLORATION_URL, exploration.toJSON());
	}

	// PUT /explorations/:exploration_id/members
	public HttpResponse putMember(Exploration exploration, User user)
			throws ClientProtocolException, IOException, JSONException, ParseException {
		return putJSONObject(Uri.parse(EXPLORATIONS_URL).buildUpon()
				.appendPath("" + exploration.getId())
				.appendPath("members")
				.build()
				.toString(), user.toJSON());
	}

	/*
	 * Route
	 * =====
	 */

	// GET /routes/
	public List<Route> getRoutes(User user)
			throws ClientProtocolException, JSONException, IOException, ParseException {
		JSONArray routesJSON = getJsonArray(Uri.parse(ROUTES_URL).buildUpon()
				.appendQueryParameter("user_id", "" + user.getId())
				.build()
				.toString());
		return Route.parseRoutes(routesJSON);
	}

	// GET /routes/
	public List<Route> getRoutes(String searchText)
			throws ClientProtocolException, JSONException, IOException, ParseException {
		JSONArray routesJSON = getJsonArray(Uri.parse(ROUTES_URL).buildUpon()
				.appendQueryParameter("query", searchText)
				.build()
				.toString());
		return Route.parseRoutes(routesJSON);
	}

	// GET /routes/:id
	// GET /routes/1
	public Route getRoute(int id)
			throws ClientProtocolException, JSONException, IOException, ParseException {
		JSONObject routeJSON = getJsonObject(Uri.parse(ROUTES_URL).buildUpon()
				.appendPath("" + id)
				.build()
				.toString());
		return Route.parseJSON(routeJSON);
	}

	// POST /routes/new
	public HttpResponse postRoute(Route route)
			throws ClientProtocolException, IOException, JSONException {
		return postJSONObject(NEW_ROUTE_URL, route.toJSON());
	}

	// PUT /routes/:route_id/images/:quest_number
	// PUT /routes/1/images/0
	public HttpResponse putQuestImage(Route route, int questNumber)
			throws ClientProtocolException, IOException {
		HttpPut request = new HttpPut(Uri.parse(ROUTES_URL).buildUpon()
				.appendPath("" + route.getId())
				.appendPath("images")
				.appendPath("" + questNumber)
				.build()
				.toString());

		MultipartEntity entity = new MultipartEntity();
		File image = new File(route.getQuests().get(questNumber).getImage());
		entity.addPart("image", new FileBody(image));
		request.setEntity(entity);

		HttpClient client = new DefaultHttpClient();
		return client.execute(request);
	}

	/*
	 * User
	 * ====
	 */

	public User getUserFromLocal(Context context) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		int id = preferences.getInt(PREFERENCE_KEY_USER_ID, -1);
		String name = preferences.getString(PREFERENCE_KEY_USER_NAME, "new user");
		String area = preferences.getString(PREFERENCE_KEY_USER_AREA, "unknown");
		return new User(name, area, id);
	}

	public void saveUserToLocal(Context context, User user) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		preferences.edit()
				.putInt(PREFERENCE_KEY_USER_ID, user.getId())
				.putString(PREFERENCE_KEY_USER_NAME, user.getName())
				.putString(PREFERENCE_KEY_USER_AREA, user.getArea())
				.commit();
	}

	// GET /users/:id
	public User getUser(int id)
			throws ClientProtocolException, JSONException, IOException {
		JSONObject userJSON = getJsonObject(Uri.parse(USERS_URL).buildUpon()
				.appendPath("" + id)
				.build()
				.toString());
		return User.parseJSON(userJSON);
	}

	// POST /users/new
	public HttpResponse postUser(User user)
			throws ClientProtocolException, IOException, JSONException {
		return postJSONObject(NEW_USER_URL, user.toJSON());
	}

}
