package exceptions;

import java.util.Date;

@SuppressWarnings("serial")
public class NotOpenedShowRoomException extends MultilangException {
	private final int id ;
	private final Date d ;
	public NotOpenedShowRoomException(int id, Date d) {
		this.id = id ;
		this.d = d ;
	}

	@Override
	public String getMessageFR() {
		return "La salle d'id " + id +" n'est pas ouverte à la date " + d ;
	}

	@Override
	public String getMessageEN() {
		return "The showroom with id " + id +" is nto opened at the date " + d ;
	}

}
