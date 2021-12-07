package application;

import java.util.List;

import domain.Concert;

public class QueryGetConcerts extends Query<List<Concert>> {
	private final int cityId;
	public QueryGetConcerts(int cityId) {
		this.cityId = cityId ;
	}
	public List<Concert> execute() {
		return getCityService().getConcerts(cityId) ;
	}
}