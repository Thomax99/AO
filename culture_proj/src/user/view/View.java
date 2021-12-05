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
import user.model.ModelConcert;
import user.model.ModelEvent;
import user.model.ModelShowroom;

public class View {
	private List<ModelEvent> events ;
	private List<ModelShowroom> showrooms ;
	private Pane rootPane ;
	private Consumer<ModelEvent> callOnEventClick ;
	private Consumer<ModelShowroom> callOnShowroomClick ;
	private final int width ;
	public View (Pane rootPane, Consumer<ModelEvent> callOnEventClick, Consumer<ModelShowroom> callOnShowroomClick, int width) {
		this.rootPane = rootPane ;
		this.callOnEventClick = callOnEventClick ;
		this.callOnShowroomClick = callOnShowroomClick ;
		this.width = width ;
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
		double xPos = 10, yPos = 30 ;
		double maxYSize = 0 ;
		for (ModelEvent evt : events) {
			
			EventView e = null ;
			if (evt instanceof ModelConcert)
				e  = new ConcertView(evt, callOnEventClick) ;
		    else
		    	e = new DramaView(evt, callOnEventClick) ;
			e.setTranslateX(xPos);
			e.setTranslateY(yPos);
			maxYSize = Math.max(e.getTextHeight(), maxYSize) ;
			xPos += e.getTextWidth() ;
			if (xPos > width) {
				yPos += maxYSize ;
				xPos = 10 ;
				e.setTranslateX(xPos);
				e.setTranslateY(yPos);
				xPos += e.getTextWidth() ;
			}
			top.getChildren().add(e) ;
		}
		xPos = 10 ;
		yPos +=  maxYSize ;
		for (ModelShowroom r : showrooms) {
				ShowRoomView s = new ShowRoomView(r, callOnShowroomClick) ;
				s.setTranslateX(xPos);
				s.setTranslateY(yPos);
				xPos += s.getTextWidth() ;
				maxYSize = Math.max(s.getTextHeight(), maxYSize) ;
				if (xPos > width) {
					xPos = 10 ;
					yPos += maxYSize ;
				}
				top.getChildren().add(s) ;
			}
		rootPane.getChildren().add(top) ;
	}
	
}

