package application;

import java.util.List;

import domain.ShowRoom;

public class QueryGetShowRooms extends Query<List<ShowRoom>> {
	private int cityId ;
	public QueryGetShowRooms(int cityId) {
		this.cityId = cityId ;
	}

	@Override
	public List<ShowRoom> execute() {
		return getCityService().getShowRooms(cityId) ;
	}

}
