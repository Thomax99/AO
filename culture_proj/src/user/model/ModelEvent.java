package user.model;

/**
 * This is a value object which represents, for the model, an event
 * @author thomas
 *
 */
public class ModelEvent {
	private final int year, month, day, nbPlaces ;
	private final String name ;
	private final int id ;
	public ModelEvent(int year, int month, int day, int nbPlaces, String name, int id) {
		this.year = year ;
		this.month = month ;
		this.day = day ;
		this.nbPlaces = nbPlaces ;
		this.name = name ;
		this.id = id ;
	}
	public int getYear() {
		return year;
	}
	public int getMonth() {
		return month;
	}
	public int getDay() {
		return day;
	}
	public int getNbPlaces() {
		return nbPlaces;
	}
	public String getName() {
		return name;
	}
	public int getId() {
		return id;
	}
}
