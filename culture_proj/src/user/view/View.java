package user.view;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import domain.Concert;
import domain.Drama;
import domain.Event;
import domain.ShowRoom;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import user.Controller;
import user.model.ModelEvent;
import user.model.ModelShowroom;

public class View {
	private List<ModelEvent> events ;
	private List<ModelShowroom> showrooms ;
	private Pane rootPane ;
	private Consumer<ModelEvent> callOnEventClick ;
	private Consumer<ModelShowroom> callOnShowroomClick ;
	public View (Pane rootPane, Consumer<ModelEvent> callOnEventClick, Consumer<ModelShowroom> callOnShowroomClick) {
		this.rootPane = rootPane ;
		this.callOnEventClick = callOnEventClick ;
		this.callOnShowroomClick = callOnShowroomClick ;
	}
	public void update(List<ModelEvent> events, List<ModelShowroom> showrooms) {
		this.events = events ;
		this.showrooms = showrooms ;

		display() ;
	}
	public void display() {
	    if (! Platform.isFxApplicationThread()) {
	         Platform.runLater(() -> display());
	         return;
	    }
	    this.rootPane.getChildren().clear();
		Pane top = new Pane() ;
		int xPos = 10, yPos = 30 ;
		for (ModelEvent evt : events) {
			EventView e = new EventView(evt, callOnEventClick) ;
			e.setTranslateX(xPos);
			e.setTranslateY(yPos);
			yPos +=30 ;
			top.getChildren().add(e) ;
		}
		for (ModelShowroom r : showrooms) {
				ShowRoomView s = new ShowRoomView(r, callOnShowroomClick) ;
				s.setTranslateX(xPos);
				s.setTranslateY(yPos);
				yPos+=270 ;
				top.getChildren().add(s) ;
			}
		rootPane.getChildren().add(top) ;
	}
}

