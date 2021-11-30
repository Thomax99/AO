package user;

import java.util.List;

import application.Query;
import domain.Event;
import domain.ShowRoom;

public class QueryGetShowRooms extends Query<List<ShowRoom>> {
	private int cityId ;

	@Override
	public List<ShowRoom> execute() {
		return getCityService()
	}

}
