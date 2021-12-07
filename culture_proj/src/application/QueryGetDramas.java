package application;

import java.util.List;

import domain.Drama;

public class QueryGetDramas extends Query<List<Drama>> {
	public List<Drama> execute() {
		return getCityService().getDramas() ;
	}
}
