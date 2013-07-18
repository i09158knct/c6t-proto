package jp.knct.di.c6t.ui.exploration;

import jp.knct.di.c6t.IntentData;
import jp.knct.di.c6t.R;
import jp.knct.di.c6t.model.Exploration;
import jp.knct.di.c6t.model.Quest;
import jp.knct.di.c6t.model.Route;
import jp.knct.di.c6t.ui.route.RouteCreationDetailFormActivity;
import jp.knct.di.c6t.ui.route.RouteCreationQuestFormActivity;
import jp.knct.di.c6t.util.ActivityUtil;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class ExplorationStartActivity extends Activity implements OnClickListener {
	private GoogleMap mMap;
	private Exploration mExploration;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exploration_start);
		
		mExploration = getIntent().getParcelableExtra(IntentData.EXTRA_KEY_EXPLORATION);
		
		ActivityUtil.setOnClickListener(this, this, new int[] {
		});

		mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.exploration_start_map))
				.getMap();
		mMap.setMyLocationEnabled(true);

		MarkerOptions startPoint = createStartPointMarker(mExploration.getRoute());
		mMap.addMarker(startPoint);
	}

	private MarkerOptions createStartPointMarker(Route route) {
		LatLng startLocation = route.getStartLocation();
		return new MarkerOptions()
				.position(startLocation)
				.title("スタートポイント");
	}

	@Override
	public void onClick(View v) {
	}

}
