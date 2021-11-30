package application;

/**
 * This interface represents a query to the domain layer, by the intermediate of the application layer
 * it means an executable function which wait for the end of the execution
 * @author thomas
 *
 */
public abstract class Query<T> extends CQRS {
	public abstract T execute() ;
}
