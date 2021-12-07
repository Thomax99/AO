package application;

import java.util.List;

import domain.Drama;

public class QueryGetDramas extends Query<List<Drama>> {
	private final int cityId;
	public QueryGetDramas(int cityId) {
		this.cityId = cityId ;
	}
	public List<Drama> execute() {
		return getCityService().getDramas(cityId) ;
	}
}
