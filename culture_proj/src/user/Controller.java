package user;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import application.Command;
import application.CommandAddEvent;
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
	private List<Integer> selectedEventRefs ;
	public Controller(CommandBag bag, int cityId, Stage primaryStage) {
		this.cityId = cityId ;
		this.bag = bag ;
		selectedEventRefs = new LinkedList<>() ;

		List<Event> events = new QueryGetEvents().execute() ;
		List<ShowRoom> showrooms = new QueryGetShowRooms(cityId).execute() ;
		// TODO Auto-generated method stub

		StackPane root = new StackPane();
		View v = new View(events, showrooms, root,  modEvt -> {
			// on ajoute le modelEventId a la liste des modelEvents
			if (selectedEventRefs.contains(modEvt.getId())) {
				selectedEventRefs.remove(modEvt.getId()) ;
			} else {
				selectedEventRefs.add(modEvt.getId()) ;
			}
		}, modsr -> {
			for (Integer id :selectedEventRefs) {
				Command cmd = new CommandAddEvent(cityId, modsr.getCorrespondingId(), id, 12, 12, 13, 8) ;
				System.err.println("trouver un moyen de choisir la date ...") ;
				bag.pushCommand(cmd);
			}
		}) ;

		Scene scene = new Scene(root, 800, 1400);

		primaryStage.setTitle("Plannificateur ...");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO : update view
		System.err.println("updating") ;
	}
	

}
