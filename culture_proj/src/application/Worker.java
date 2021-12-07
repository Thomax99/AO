package application;


public class Worker extends Thread {
	private CommandBag bag ;
	private boolean cont ;
	
	public Worker(CommandBag bag) {
		this.bag = bag ;
		cont = true ;
	}
	public void arrest() {
		cont = false ;
	}
	
	public void run() {
		while (cont) {
			while (cont && bag.isEmpty()) ;
			if (cont) {
				Command c = bag.popCommand() ;
				c.execute();
			}
		}
	}
}
