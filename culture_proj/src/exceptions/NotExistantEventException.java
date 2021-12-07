package exceptions;

@SuppressWarnings("serial")
public class NotExistantEventException extends MultilangException {
	private final int ref ;
	public NotExistantEventException(int eventRef) {
		this.ref = eventRef ;
	}
	

	@Override
	public String getMessageFR() {
		// TODO Auto-generated method stub
		return "L'évènement de référence "+ ref +" n'existe pas" ;
	}

	@Override
	public String getMessageEN() {
		return "The event with reference "+ref +" doesn't exist" ;
	} 


}
