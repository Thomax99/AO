package exceptions;

@SuppressWarnings("serial")
public class NegativePlaceQuantityException extends MultilangException {

	@Override
	public String getMessageFR() {
		return "La quantité de place nécessaire à cet évènement est négative\n" ;
	}

	@Override
	public String getMessageEN() {
		return "The amount of place asked for this event is negative\n" ;
	}

}
