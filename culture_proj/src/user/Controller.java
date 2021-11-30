package user;

import java.util.Observable;
import java.util.Observer;

import application.CommandBag;

public class Controller implements Observer {
	private CommandBag bag ;
	private final int cityId ;
	public Controller(CommandBag bag, int cityId) {
		this.cityId = cityId ;
		this.bag = bag ;
	}
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO : update view
		System.err.println("updating") ;
	}
}
