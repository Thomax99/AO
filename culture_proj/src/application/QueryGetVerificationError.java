package application;

public class QueryGetVerificationError extends Query<String> {
	private final int cityId ;
	public QueryGetVerificationError(int cityId) {
		this.cityId = cityId ;
	}

	@Override
	public String execute() {
		return getCityService().getVerificationError(cityId);
	}

}
