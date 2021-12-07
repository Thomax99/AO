package exceptions;

@SuppressWarnings("serial")
public class CapacityNegativeException extends MultilangException {

	@Override
	public String getMessageFR() {
		return "La salle a une capacité négative\n";
	}

	@Override
	public String getMessageEN() {
		return "The showroom has a negative capacity\n";
	}

}
