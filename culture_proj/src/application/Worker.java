package application;

import exceptions.BagNullException;

public class Worker extends Thread {
	private CommandBag bag ;
	private boolean cont ;
	
	public Worker(CommandBag bag) throws BagNullException {
		if (bag == null) {
			throw new BagNullException() ;
		}
		this.bag = bag ;
		cont = true ;
	}
	public void arrest() {
		cont = false ;
		this.interrupt();
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
