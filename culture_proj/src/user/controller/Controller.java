package user.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import application.Command;
import application.CommandAddEvent;
import application.CommandAppIsClose;
import application.CommandBag;
import application.CommandRemoveEvent;
import application.CommandVerifShowRoom;
import application.QueryGetConcerts;
import application.QueryGetDramas;
import application.QueryGetError;
import application.QueryGetShowRooms;
import application.QueryGetVerificationError;
import domain.Concert;
import domain.Drama;
import domain.ShowRoom;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import user.model.Model;
import user.view.View;

public class Controller  implements Observer  {
	private final View v ;
	private final Model m ;
	private final int cityId ;
	private final StackPane root ;
	private List<Integer> selectedEventRefs ;
	public Controller(CommandBag bag, int cityId, Stage primaryStage) {
		this.cityId = cityId ;
		selectedEventRefs = new LinkedList<>() ;
		root = new StackPane();

		Scene scene = new Scene(root, 800, 1400);


		primaryStage.setTitle("Plannificateur ...");
		primaryStage.setScene(scene);
		primaryStage.show();
		v = new View(root,  (modSR, modEvt) -> { // click sur un model event disponible au choix
			// on ajoute le modelEventId a la liste des modelEvents
			if (selectedEventRefs.contains(modEvt.getId())) {
				selectedEventRefs.remove(selectedEventRefs.indexOf(modEvt.getId())) ;
			} else {
				selectedEventRefs.add(modEvt.getId()) ;
			}
		}, modsr -> { // click sur une showroom
			for (Integer id :selectedEventRefs) {
				Command cmd = new CommandAddEvent(cityId, modsr.getCorrespondingId(), id) ;
				bag.pushCommand(cmd);
			}
			selectedEventRefs.clear();
		}, modsr -> { // click sur le bouton verifier d'une showroom
			Command cmd = new CommandVerifShowRoom(cityId, modsr.getCorrespondingId()) ;
			bag.pushCommand(cmd);
		}, (modsr, modev) -> {
			Command cmd = new CommandRemoveEvent(cityId, modsr.getCorrespondingId(), modev.getId()) ;
			bag.pushCommand(cmd);
		}
			
		,800) ;
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent ev) {
					Command cmd = new CommandAppIsClose() ;
					bag.pushCommand(cmd) ;
			}
			
		});
		m = new Model() ;
		update(null) ;
	}

	public void update(Observable ob) {
		update(ob, null) ;
	}

	@Override
	public void update(Observable ob, Object arg) {
		// on commence par recuperer les differentes erreurs
		
		String errorVerif = new QueryGetVerificationError(cityId).execute() ;
		if (errorVerif != null) {
			v.notifyError(errorVerif) ;
			update(ob, arg) ; // on recommence pour vider les erreurs, si besoin
			return ;
		}
	
		
		String error = new QueryGetError(cityId).execute() ;
		if (error != null) {
			v.notifyError(error) ;
			update(ob, arg) ; // on recommence pour vider les erreurs, si besoin
			return ;
		}
		
		List<Drama> dramas = new QueryGetDramas().execute() ;
		List<Concert> concerts = new QueryGetConcerts().execute() ;
		List<ShowRoom> showrooms = new QueryGetShowRooms(cityId).execute() ;
		m.update(dramas, concerts, showrooms);
			
		// TODO Auto-generated method stub
		v.update(m.getConcerts(), m.getDramas(),  m.getShowrooms());
	}
	

}
