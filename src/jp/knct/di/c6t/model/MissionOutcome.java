package jp.knct.di.c6t.model;

import java.util.Date;

public class MissionOutcome extends Outcome {

	public static MissionOutcome convertOutcome(Outcome outcome) {
		return new MissionOutcome(
				outcome.getRoute(),
				outcome.getQuestNumber(),
				outcome.getPhotoedAt(),
				outcome.getPhotoUri());
	}

	public MissionOutcome(Route route, int questNumber, Date photoedAt, String photoUri) {
		super(route, questNumber, photoedAt, photoUri);
	}

	public String getMission() {
		return getQuest().getMission();
	}

}
