package exceptions;

public class BagNullException extends MultilangException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getMessageFR() {
		return "Le sac de commande n'existe pas" ;
	}

	@Override
	public String getMessageEN() {
		return "The command bag doesn't exist" ;
	}

}
