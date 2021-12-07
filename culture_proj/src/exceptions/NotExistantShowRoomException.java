package exceptions;

@SuppressWarnings("serial")
public class NotExistantShowRoomException extends MultilangException {
	private int showroomId ;
	public NotExistantShowRoomException(int showroomId) {
		this.showroomId = showroomId ;
	}

	@Override
	public String getMessageFR() {
		return "La salle de spectacle d'id "+showroomId +" n'existe pas" ;
	}

	@Override
	public String getMessageEN() {
		return "The showroom with id "+showroomId +" doesn't exist" ;
	} 
}
