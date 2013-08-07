package jp.knct.di.c6t.model;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

public class MissionOutcome extends Outcome {

	public static List<MissionOutcome> parseMissionOutcomes(String outcomes) throws JSONException, ParseException {
		List<Outcome> outcomeList = Outcome.parseOutcomes(new JSONArray(outcomes));
		return convertOutcomes(outcomeList);
	}

	public static MissionOutcome convertOutcome(Outcome outcome) {
		return new MissionOutcome(
				outcome.getExploration(),
				outcome.getQuestNumber(),
				outcome.getPhotoedAt(),
				outcome.getPhotoPath());
	}

	public static List<MissionOutcome> convertOutcomes(List<Outcome> outcomes) {
		List<MissionOutcome> missionOutcomes = new ArrayList<MissionOutcome>(outcomes.size());
		for (Outcome outcome : outcomes) {
			missionOutcomes.add(MissionOutcome.convertOutcome(outcome));
		}
		return missionOutcomes;
	}

	public static List<Outcome> convertMissionOutcomes(List<MissionOutcome> missionOutcomes) {
		List<Outcome> outcomes = new ArrayList<Outcome>(missionOutcomes.size());
		for (Outcome outcome : missionOutcomes) {
			outcomes.add(MissionOutcome.convertOutcome(outcome));
		}
		return outcomes;
	}

	public MissionOutcome(Exploration exploration, int questNumber, Date photoedAt, String photoUri) {
		super(exploration, questNumber, photoedAt, photoUri);
	}

	public String getMission() {
		return getQuest().getMission();
	}

}
