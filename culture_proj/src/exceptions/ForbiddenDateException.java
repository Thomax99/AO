package exceptions;

@SuppressWarnings("serial")
public class ForbiddenDateException extends MultilangException {
	private final int year, month, day ;
	public ForbiddenDateException(int year, int month, int day) {
		this.year = year ;
		this.day = day ;
		this.month = month ;
	}

	@Override
	public String getMessageFR() {
		return "La date " +day+"/"+month+"/"+year+" est interdite\n";
	}

	@Override
	public String getMessageEN() {
		return "The date " +day+"/"+month+"/"+year+" is forbidden\n";
	}

}
