package application;

public class QueryGetError extends Query<String> {
	private final int cityId ;
	public QueryGetError(int cityId) {
		this.cityId = cityId ;
	}

	@Override
	public String execute() {
		// TODO Auto-generated method stub
		return getCityService().getError(cityId);
	}

}
