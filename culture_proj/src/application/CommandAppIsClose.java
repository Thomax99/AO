package application;

public class CommandAppIsClose extends Command {

	@Override
	public void execute() {
		getCityService().removeCity() ;
		System.err.println(getCityService().getNBCities()) ;
		if (getCityService().getNBCities() == 0) {
			getCityService().getWorkers().forEach( w -> w.arrest()) ;
		}

	}

}
