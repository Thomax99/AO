package exceptions;

@SuppressWarnings("serial")
public class EqualsDatesException extends MultilangException {

	@Override
	public String getMessageFR() {
		return "Deux dates d'ouverture égales dans la création de la salle\n";
	}

	@Override
	public String getMessageEN() {
		return "Two OpenDates equals at the showroom creation\n";
	}

}
