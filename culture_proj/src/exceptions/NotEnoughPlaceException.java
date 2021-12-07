package exceptions;

@SuppressWarnings("serial")
public class NotEnoughPlaceException extends MultilangException {
	private final int showroomId, placeInShowroom, placeAsked ;
	public NotEnoughPlaceException(int showroomId, int placeInShowroom, int placeAsked) {
		this.showroomId = showroomId ;
		this.placeInShowroom = placeInShowroom ;
		this.placeAsked = placeAsked ;
	}

	@Override
	public String getMessageFR() {
		return "La salle de spectacle d'id " + showroomId +" ne peut pas accueillir un évènement de " + placeAsked +" places : elle n'a que " + placeInShowroom + "places";
	}

	@Override
	public String getMessageEN() {
		return "The showroom with id" + showroomId +" cannot welcome an event with " + placeAsked +" places : it as just " + placeInShowroom + "places";
	}

}
