package exceptions;

@SuppressWarnings("serial")
public abstract class MultilangException extends Exception {
	public abstract String getMessageFR() ;
	public abstract String getMessageEN() ;
}
