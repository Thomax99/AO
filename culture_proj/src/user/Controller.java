package user;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import application.CommandBag;
import application.QueryGetEvents;
import domain.Event;
import domain.ShowRoom;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import user.view.View;

public class Controller  implements Observer  {
	private CommandBag bag ;
	private final int cityId ;
	public Controller(CommandBag bag, int cityId, Stage primaryStage) {
		this.cityId = cityId ;
		this.bag = bag ;
		
		List<Event> events = new QueryGetEvents().execute() ;
		List<ShowRoom> showrooms = new QueryGetShowRooms(cityId).execute() ;
		// TODO Auto-generated method stub

		StackPane root = new StackPane();
		View v = new View(events, showrooms, root) ;

		Scene scene = new Scene(root, 450, 250);

		primaryStage.setTitle("JavaFX Open a new Window (o7planning.org)");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO : update view
		System.err.println("updating") ;
	}
	

}
