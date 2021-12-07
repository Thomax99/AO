package user.model;

import java.util.List;

import domain.OpenDate;

/**
 * A value object which represents a showroom for the MVC
 * @author thomas
 *
 */
public class ModelShowroom {
	private final int capacity ;
	private final int correspondingId ;
	private final List<ModelConcert> concerts ;
	private final List<ModelDrama> dramas ;
	private final List<OpenDate> dates ;
	public ModelShowroom(int capacity, List<ModelConcert> concerts, List<ModelDrama> dramas, List<OpenDate> dates, int id) {
		this.capacity = capacity ;
		this.concerts = concerts ;
		this.dramas = dramas ;
		this.correspondingId = id ;
		this.dates = dates ;
	}
	public int getCapacity() {
		return capacity ;
	}
	public List<ModelConcert> getConcerts() {
		return concerts ;
	}
	public List<ModelDrama> getDramas() {
		return dramas ;
	}
	public List<OpenDate> getOpenDates() {
		return dates ;
	}
	public int getCorrespondingId() {
		return correspondingId;
	}
	
}
