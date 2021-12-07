package exceptions;

import java.util.Date;

@SuppressWarnings("serial")
public class ForbiddenDateIntervalException extends MultilangException {
	private final Date d1, d2 ;
	
	public ForbiddenDateIntervalException(Date d1, Date d2) {
		this.d1 = d1 ;
		this.d2 = d2 ;
	}

	@Override
	public String getMessageFR() {
		return "Les dates " + d1 +" et " + d2 +" ne forment pas un intervalle\n";
	}

	@Override
	public String getMessageEN() {
		return "The" + d1 +" and " + d2 +" don't make an interval\n";
	}

}
