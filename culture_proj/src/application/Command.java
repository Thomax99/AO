package application;


/**
 * This interface represents a command
 * it means an executable things which is put on a command bag, and we are ensure it will be end later
 * @author thomas
 *
 */
public abstract class Command extends CQRS {
	public abstract void execute() ;


}
