package jp.knct.di.c6t.communication;

import java.util.List;

import jp.knct.di.c6t.model.Exploration;
import jp.knct.di.c6t.model.Route;
import jp.knct.di.c6t.model.User;

public interface Client {
	List<Exploration> getExplorations(User user);

	List<Exploration> getExplorations(String searchText);

	List<Route> getRoutes(User user);

	List<Route> getRoutes(String searchText);

	void saveExploration(Exploration exploration);

	void saveRoute(Route route);

	User getMyUserData();

	Exploration refreshExplorationInfo(Exploration exploration);

	void joinExploration(Exploration exploration, User user);
}
