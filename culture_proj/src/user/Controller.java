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
import domain.OpenDate;
import domain.ShowRoom;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import user.model.Model;
import user.model.ModelEvent;
import user.model.ModelShowroom;
import user.view.View;

public class Controller  implements Observer  {
	private CommandBag bag ;
	private final View v ;
	private final Model m ;
	private final int cityId ;
	private final StackPane root ;
	private List<Integer> selectedEventRefs ;
	public Controller(CommandBag bag, int cityId, Stage primaryStage) {
		this.cityId = cityId ;
		this.bag = bag ;
		selectedEventRefs = new LinkedList<>() ;
		root = new StackPane();

		Scene scene = new Scene(root, 800, 1400);


		primaryStage.setTitle("Plannificateur ...");
		primaryStage.setScene(scene);
		primaryStage.show();
		v = new View(root,  modEvt -> {
			// on ajoute le modelEventId a la liste des modelEvents
			if (selectedEventRefs.contains(modEvt.getId())) {
				selectedEventRefs.remove(selectedEventRefs.indexOf(modEvt.getId())) ;
			} else {
				selectedEventRefs.add(modEvt.getId()) ;
			}
		}, modsr -> {
			for (Integer id :selectedEventRefs) {
				Command cmd = new CommandAddEvent(cityId, modsr.getCorrespondingId(), id) ;
				bag.pushCommand(cmd);
			}
			selectedEventRefs.clear();
		}, 800) ;
		m = new Model() ;
		update(null) ;
	}

	public void update(Observable ob) {
		update(ob, null) ;
	}

	@Override
	public void update(Observable ob, Object arg) {
		// TODO : update view
		// on commence par recuperer les differentes valeurs
		
		List<Event> events = new QueryGetEvents().execute() ;
		List<ShowRoom> showrooms = new QueryGetShowRooms(cityId).execute() ;
		m.update(events, showrooms);
			
		// TODO Auto-generated method stub
		v.update(m.getEvents(), m.getShowrooms());
	}
	

}
