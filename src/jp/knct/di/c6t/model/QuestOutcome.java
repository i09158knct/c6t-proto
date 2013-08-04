package jp.knct.di.c6t.model;

import java.util.Date;

public class QuestOutcome extends Outcome {

	public static QuestOutcome convertOutcome(Outcome outcome) {
		return new QuestOutcome(
				outcome.getRoute(),
				outcome.getQuestNumber(),
				outcome.getPhotoedAt(),
				outcome.getPhotoUri());
	}

	public QuestOutcome(Route route, int questNumber, Date photoedAt, String photoUri) {
		super(route, questNumber, photoedAt, photoUri);
	}

	public String getPose() {
		return getQuest().getPose();
	}

}
