package jp.knct.di.c6t.model;

import java.util.Date;

public class QuestOutcome extends Outcome {

	public static QuestOutcome convertOutcome(Outcome outcome) {
		return new QuestOutcome(
				outcome.getExploration(),
				outcome.getQuestNumber(),
				outcome.getPhotoedAt(),
				outcome.getPhotoUri());
	}

	public QuestOutcome(Exploration exploration, int questNumber, Date photoedAt, String photoUri) {
		super(exploration, questNumber, photoedAt, photoUri);
	}

	public String getPose() {
		return getQuest().getPose();
	}

}
