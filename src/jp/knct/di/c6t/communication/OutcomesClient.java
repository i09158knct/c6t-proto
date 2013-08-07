package jp.knct.di.c6t.communication;

import java.text.ParseException;
import java.util.List;

import jp.knct.di.c6t.model.MissionOutcome;
import jp.knct.di.c6t.model.Outcome;
import jp.knct.di.c6t.model.QuestOutcome;
import jp.knct.di.c6t.model.Trophy;

import org.json.JSONException;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class OutcomesClient {

	private static final String PREFERENCE_KEY_MISSION_OUTCOMES = "mission_outcomes";
	private static final String PREFERENCE_KEY_QUEST_OUTCOMES = "quest_outcomes";
	private static final String PREFERENCE_KEY_TROPHIES = "tropies";

	public List<MissionOutcome> getMissionOutcomes(Context context) throws JSONException, ParseException {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		return MissionOutcome.parseMissionOutcomes(preferences.getString(
				PREFERENCE_KEY_MISSION_OUTCOMES,
				"[]"));
	}

	public List<QuestOutcome> getQuestOutcomes(Context context) throws JSONException, ParseException {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		return QuestOutcome.parseQuestOutcomes(preferences.getString(
				PREFERENCE_KEY_QUEST_OUTCOMES,
				"[]"));
	}

	public void addMissionOutcome(Context context, MissionOutcome... missionOutcomes) throws JSONException, ParseException {
		List<MissionOutcome> existingMissionOutcomes = getMissionOutcomes(context);
		for (MissionOutcome outcome : missionOutcomes) {
			existingMissionOutcomes.add(outcome);
		}
		List<Outcome> outcomes = MissionOutcome.convertMissionOutcomes(existingMissionOutcomes);
		String outcomesJSON = Outcome.convertOutcomesToJsonArray(outcomes).toString();

		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		preferences.edit()
				.putString(PREFERENCE_KEY_MISSION_OUTCOMES, outcomesJSON)
				.commit();
	}

	public void addQuestOutcome(Context context, QuestOutcome... questOutcomes) throws JSONException, ParseException {
		List<QuestOutcome> existingQuestOutcomes = getQuestOutcomes(context);
		for (QuestOutcome outcome : questOutcomes) {
			existingQuestOutcomes.add(outcome);
		}
		List<Outcome> outcomes = QuestOutcome.convertQuestOutcomes(existingQuestOutcomes);
		String outcomesJSON = Outcome.convertOutcomesToJsonArray(outcomes).toString();

		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		preferences.edit()
				.putString(PREFERENCE_KEY_QUEST_OUTCOMES, outcomesJSON)
				.commit();
	}

	public List<Trophy> getTrophies(Context context) throws JSONException, ParseException {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		return Trophy.parseTrophies(preferences.getString(
				PREFERENCE_KEY_TROPHIES,
				"[]"));
	}

	public void addTrophy(Context context, Trophy... trophies) throws JSONException, ParseException {
		List<Trophy> existingTropies = getTrophies(context);
		for (Trophy trophy : trophies) {
			existingTropies.add(trophy);
		}
		String trophiesJSON = Trophy.convertTrophysToJsonArray(existingTropies).toString();

		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		preferences.edit()
				.putString(PREFERENCE_KEY_TROPHIES, trophiesJSON)
				.commit();
	}
}
