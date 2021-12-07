package application;

public class CommandRemoveEvent extends Command {
	private final int cityId, showroomId, eventRef ;
	public CommandRemoveEvent(int cityId, int showroomId, int eventRef) {
		this.cityId = cityId ;
		this.showroomId = showroomId ;
		this.eventRef = eventRef ;
	}

	@Override
	public void execute() {
		getCityService().removeEvent(cityId, showroomId, eventRef);
	}

}
