package jp.knct.di.c6t.ui.route;

import jp.knct.di.c6t.R;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class RouteCreationQuestsActivity extends ListActivity {
	// private AppListManager mAppsManager;
	// private List<String> mCategories;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_route_creation_quests);

		// mCategories = mAppsManager.getAllCategoryNameList();
		// ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		// for (String categoryName : mCategories) {
		// adapter.add(categoryName);
		// }
		// setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// String categoryName = mCategories.get(position);
		// Intent intent = new Intent(this, MainActivity.class);
		// intent.putExtra(MainActivity.EXTRA_KEY_CATEGORY_NAME, categoryName);
		// startActivity(intent);
		// finish();
	}
}
