package application;

public class CommandVerifShowRoom extends Command {
	private final int cityId, showroomId ;

	public CommandVerifShowRoom(int cityId, int showroomId) {
		this.cityId = cityId ;
		this.showroomId = showroomId ;
	}

	@Override
	public void execute() {
		getCityService().verify(cityId, showroomId) ;
	}

}
