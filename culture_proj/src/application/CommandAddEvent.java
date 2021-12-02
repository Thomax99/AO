package application;

import domain.OpenDate;

public class CommandAddEvent extends Command {
	private int showRoomId, eventRef, cityId, year, month, day, hour ;
	public CommandAddEvent(int cityId, int showRoomId, int eventRef, int year, int month, int day, int hour) {
		this.showRoomId = showRoomId ;
		this.eventRef = eventRef ;
		this.cityId = cityId ;
		this.hour = hour ;
		this.year = year ;
		this.month = month ;
		this.day = day ;
	}
	@Override
	public void execute() {
		getCityService().addEvent(cityId, showRoomId, eventRef, new OpenDate(year, month, day, hour)) ;
	}
}
