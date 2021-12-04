package user.model;

public class ModelDrama extends ModelEvent {
	private final int endYear, endMonth, endDay ; // the date stored in modelEvent is for the start

	public ModelDrama(int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay, int nbPlaces,
			String name, int id) {
		super(startYear, startMonth, startDay, nbPlaces, name, id);
		this.endDay = endDay ;
		this.endMonth = endMonth ;
		this.endYear = endYear ;
	}
	public int getStartDay() {
		return getDay() ;
	}
	public int getStartMonth() {
		return getMonth() ;
	}
	public int getStartYear() {
		return getYear() ;
	}
	public int getEndDay() {
		return endDay ;
	}
	public int getEndMonth() {
		return endMonth ;
	}
	public int getEndYear() {
		return endYear ;
	}
	public String toString() {
		return "Pièce : " + getName() +" du "+ getStartDay()+ "/" + getStartMonth()+"/"+getStartYear() +" au " +getEndDay()+"/"+getEndMonth() +"/"+getEndYear() ;
	}

}
