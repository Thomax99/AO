package domain;

/**
 * A value object class which represents the name of a band or a musician
 * @author thomas
 *
 */
public class Name {
	private final String name ;

	public Name(String name) {
		this.name = name ;
	}

	public String getName() {
		return name;
	}

}
