package application;

public class CQRS {
	private static CityService service = null ;
	public static void setCityService(CityService serv) {
		service = serv ;
	}
	public static CityService getCityService() {
		return service ;
	}
}
