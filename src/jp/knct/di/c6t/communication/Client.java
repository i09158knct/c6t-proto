package jp.knct.di.c6t.communication;

import java.util.List;

import jp.knct.di.c6t.model.Route;
import jp.knct.di.c6t.model.User;

public interface Client {

	List<Route> getRoutes(User user);

}
