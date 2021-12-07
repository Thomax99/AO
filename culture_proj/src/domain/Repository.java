package domain;

public interface Repository {
	public void save(City city) ;
	public City findCityById(int id) ;
	public void updateCity(int id, City cityToUpdate) ;
	public int getNumberCities();
}
