package infra;

import domain.City;
import domain.Repository;

/**
 * Cette classe fournit un repo avec un cache (repo en memoire) et un repo en xml
 * @author thomas
 *
 */
public class RepoProxy implements Repository {
	private final Repository memRepo, xmlRepo ;
	private int nbCities ;
	
	public RepoProxy() {
		memRepo = new RepositoryInMemory() ;
		xmlRepo = new RepositoryInXml() ;
		nbCities = 0 ;
	}

	@Override
	public void save(City city) {
		nbCities++ ;
		memRepo.save(city);
		xmlRepo.save(city);
	}

	@Override
	public City findCityById(int id) {
		City city = memRepo.findCityById(id) ;
		if (city == null) {
			city = xmlRepo.findCityById(id) ;
			if (city != null) {
				memRepo.save(city);
			}
		}
		return city;
	}

	@Override
	public void updateCity(int id, City cityToUpdate) {
		memRepo.updateCity(id, cityToUpdate);
		xmlRepo.updateCity(id, cityToUpdate);		
	}

	@Override
	public int getNumberCities() {
		return nbCities;
	}
	

}
