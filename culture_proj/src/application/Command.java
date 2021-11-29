package application;

import java.util.function.Consumer;

/**
 * This interface represents a command
 * it means an executable things which is put on a command bag, and we are ensure it will be end later
 * @author thomas
 *
 */
public abstract class Command<T> {
	private Consumer<T> callback ;
	
	public Command(Consumer<T> callback) {
		this.callback = callback ;
	}
	
	public abstract void execute() ;
	public void executeCallback(T arg) {
		callback.accept(arg);
	}
}
