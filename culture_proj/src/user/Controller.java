package user;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.TreeMap;

import application.Command;
import application.CommandAddEvent;
import application.CommandBag;
import application.QueryGetEvents;
import domain.Concert;
import domain.Drama;
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
import user.model.ModelEvent;
import user.view.View;

public class Controller  implements Observer  {
	private CommandBag bag ;
	private final int cityId ;
	private Map<Integer, ModelEvent> modelsEvent ;
	private List<Integer> selectedEventRefs ;
	public Controller(CommandBag bag, int cityId, Stage primaryStage) {
		this.cityId = cityId ;
		this.bag = bag ;
		selectedEventRefs = new LinkedList<>() ;

		List<Event> events = new QueryGetEvents().execute() ;
		List<ShowRoom> showrooms = new QueryGetShowRooms(cityId).execute() ;
		// TODO Auto-generated method stub
		modelsEvent = new TreeMap<>() ;
		for (Event evt : events) {
			ModelEvent mod = null ;
			if (evt instanceof Drama) { // DEGUEULASSE : il faut faire des ModelEvents generiques je pense
				Drama d = (Drama) evt ;
				mod = new ModelEvent(d.getStartDate().getYear(), d.getStartDate().getMonth(), d.getStartDate().getDay(),
						d.getEndDate().getYear(), d.getEndDate().getMonth(), d.getEndDate().getDay(),d.getPlaceNumber(),  d.getTitleName().getName(), d.getRef()) ;
				
			
			} else if (evt instanceof Concert) {
				Concert c = (Concert) evt ;
				mod = new ModelEvent(c.getDate().getYear(), c.getDate().getMonth(), c.getDate().getDay(),
						c.getDate().getYear(), c.getDate().getMonth(), c.getDate().getDay(), c.getPlaceNumber(),  c.getArtistName().getName(), c.getRef()) ;
			}
			modelsEvent.put(mod.getId(), mod) ;
		}

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
				ModelEvent ev = modelsEvent.get(id) ;
				Command cmd = new CommandAddEvent(cityId, modsr.getCorrespondingId(), id) ;
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
	public void update(Observable arg0) {
		// TODO : update view
		System.err.println("updating 2") ;
	}

	

}
