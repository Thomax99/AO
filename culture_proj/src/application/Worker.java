package application;

import java.util.Scanner;

public class Worker extends Thread {
	private CommandBag bag ;
	
	public Worker(CommandBag bag) {
		this.bag = bag ;
	}
	
	public void run() {
		while (true) {
			while (bag.isEmpty()) ;
			Command c = bag.popCommand() ;
			c.execute();
		}
	}
}
