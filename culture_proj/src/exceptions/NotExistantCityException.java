package exceptions;

@SuppressWarnings("serial")
public class NotExistantCityException extends MultilangException {
	private int cityId ;
	public NotExistantCityException(int cityId) {
		this.cityId = cityId ;
	}

	@Override
	public String getMessageFR() {
		// TODO Auto-generated method stub
		return "La ville d'id "+cityId +" n'existe pas" ;
	}

	@Override
	public String getMessageEN() {
		return "The city with id "+cityId +" doesn't exist" ;
	} 

}
