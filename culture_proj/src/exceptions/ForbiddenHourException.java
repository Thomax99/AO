package exceptions;

@SuppressWarnings("serial")
public class ForbiddenHourException extends MultilangException {
	private final int hour ;

	public ForbiddenHourException(int hour) {
		this.hour = hour ;
	}

	@Override
	public String getMessageFR() {
		return "L'heure " + hour + " est invalide";
	}

	@Override
	public String getMessageEN() {
		return "The hour " + hour + " is invalid";
	}

}
