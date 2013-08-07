package jp.knct.di.c6t.model;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

public class QuestOutcome extends Outcome {

	public static List<QuestOutcome> parseQuestOutcomes(String outcomes) throws JSONException, ParseException {
		List<Outcome> outcomeList = Outcome.parseOutcomes(new JSONArray(outcomes));
		return convertOutcomes(outcomeList);
	}

	public static QuestOutcome convertOutcome(Outcome outcome) {
		return new QuestOutcome(
				outcome.getExploration(),
				outcome.getQuestNumber(),
				outcome.getPhotoedAt(),
				outcome.getPhotoUri());
	}

	public static List<QuestOutcome> convertOutcomes(List<Outcome> outcomes) {
		List<QuestOutcome> questOutcomes = new ArrayList<QuestOutcome>(outcomes.size());
		for (Outcome outcome : outcomes) {
			questOutcomes.add(QuestOutcome.convertOutcome(outcome));
		}
		return questOutcomes;
	}

	public static List<Outcome> convertQuestOutcomes(List<QuestOutcome> questOutcomes) {
		List<Outcome> outcomes = new ArrayList<Outcome>(questOutcomes.size());
		for (Outcome outcome : questOutcomes) {
			outcomes.add(QuestOutcome.convertOutcome(outcome));
		}
		return outcomes;
	}

	public QuestOutcome(Exploration exploration, int questNumber, Date photoedAt, String photoUri) {
		super(exploration, questNumber, photoedAt, photoUri);
	}

	public String getPose() {
		return getQuest().getPose();
	}

}
