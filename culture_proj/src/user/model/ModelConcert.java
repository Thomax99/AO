package user.model;

public class ModelConcert extends ModelEvent {

	public ModelConcert(int year, int month, int day, int nbPlaces, String name, int id) {
		super(year, month, day, nbPlaces, name, id);
	}
	public String toString() {
		return "Concert de " + getName() +" le " + getDay()+"/"+getMonth() +"/"+getYear() ;
	}
}
