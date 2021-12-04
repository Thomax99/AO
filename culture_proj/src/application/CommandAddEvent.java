package application;

import domain.OpenDate;

public class CommandAddEvent extends Command {
	private int showRoomId, eventRef, cityId ;
	public CommandAddEvent(int cityId, int showRoomId, int eventRef) {
		this.showRoomId = showRoomId ;
		this.eventRef = eventRef ;
		this.cityId = cityId ;
	}
	@Override
	public void execute() {
		getCityService().addEvent(cityId, showRoomId, eventRef) ;
	}
}
