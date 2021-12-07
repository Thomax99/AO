package application;

import java.util.List;

import domain.Concert;

public class QueryGetConcerts extends Query<List<Concert>> {
	public List<Concert> execute() {
		return getCityService().getConcerts() ;
	}
}