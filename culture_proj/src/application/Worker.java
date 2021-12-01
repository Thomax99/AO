package application;

import java.util.Scanner;

public class Worker extends Thread {
	private CommandBag bag ;
	
	public Worker(CommandBag bag) {
		this.bag = bag ;
	}
	
	public void run() {
		System.err.println("Attention : il faut changer le worker ...") ;
	    Scanner sc = new Scanner(System.in);
		while (true) {
			String s = sc.nextLine() ;
			if (s.equals("Q")) {
				break ;
			}
			while (bag.isEmpty()) ;
			Command c = bag.popCommand() ;
			c.execute();
		}
	}
}
