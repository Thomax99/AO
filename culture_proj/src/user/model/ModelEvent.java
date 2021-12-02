package user.model;

/**
 * This is a value object which represents, for the model, an event
 * @author thomas
 *
 */
public class ModelEvent {
	private final int startYear, startMonth, startDay, endYear, endMonth, endDay, nbPlaces ;
	private final String name ;
	private final int id ;
	public ModelEvent(int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay, int nbPlaces, String name, int id) {
		this.startYear = startYear ;
		this.startDay = startDay ;
		this.startMonth = startMonth ;
		this.endYear = endYear ;
		this.endMonth = endMonth ;
		this.endDay = endDay ;
		this.nbPlaces = nbPlaces ;
		this.name = name ;
		this.id = id ;
	}
	public int getStartYear() {
		return startYear;
	}
	public int getStartMonth() {
		return startMonth;
	}
	public int getStartDay() {
		return startDay;
	}
	public int getEndYear() {
		return endYear;
	}
	public int getEndMonth() {
		return endMonth;
	}
	public int getEndDay() {
		return endDay;
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
