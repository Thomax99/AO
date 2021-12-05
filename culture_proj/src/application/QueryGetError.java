package application;

public class QueryGetError extends Query<String> {

	@Override
	public String execute() {
		// TODO Auto-generated method stub
		return getCityService().getError();
	}

}
