package infra;

import java.util.Map;
import java.util.TreeMap;

import domain.City;
import domain.Repository;

public class RepositoryInMemory implements Repository {
	private Map<Integer, City> showrooms ;
	
	public RepositoryInMemory() {
		showrooms = new TreeMap<>() ;
	}

	@Override
	public void save(City city) {
		if (showrooms.containsKey(city.getId())) {
			throw new RuntimeException("The repository already contains the given key") ;
		}
		showrooms.put(city.getId(), city) ;
	}

	@Override
	public City findCityById(int id) {
		return showrooms.get(id) ;
	}

	@Override
	public void updateCity(int id, City cityToUpdate) {
		if (!showrooms.containsKey(id)) {
			throw new RuntimeException("The repository does not contains the given key") ;
		}
		showrooms.put(id, cityToUpdate) ;		
	}

}
