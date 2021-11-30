package application;

public class Worker extends Thread {
	private CommandBag bag ;
	
	public Worker(CommandBag bag) {
		this.bag = bag ;
	}
	
	public void run() {
		while (true) {
			Command c = bag.popCommand() ;
			c.execute();
		}
	}
}
