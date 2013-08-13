package jp.knct.di.c6t.ui.schedule;

import jp.knct.di.c6t.model.Exploration;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

public class ExplorationPin extends Button {

	private Exploration mExploration;

	public ExplorationPin(Context context) {
		super(context);
	}

	public ExplorationPin(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ExplorationPin(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setExploration(Exploration exploration) {
		mExploration = exploration;
	}

	public Exploration getExploration() {
		return mExploration;
	}
}
