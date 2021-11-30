package application;

import java.util.Deque;
import java.util.concurrent.LinkedBlockingDeque;

public class CommandBag {
	private Deque<Command> commands ;
	
	public CommandBag() {
		commands = new LinkedBlockingDeque<>() ; // we use a linkedblockingdeque to be blocked when no command
	}
	
	/**
	 * blocking when no element on the deque
	 * @return
	 */
	public boolean isEmpty() {
		return commands.isEmpty() ;
	}
	public Command popCommand() {
		return commands.pop();
	}
	public void pushCommand(Command command) {
		commands.push(command);
	}
	
}
