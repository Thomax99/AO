package user.view;

import java.util.List;

import domain.Concert;
import domain.Drama;
import domain.Event;
import domain.ShowRoom;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import user.model.ModelEvent;

public class View {
	private List<Event> events ;
	private List<ShowRoom> showrooms ;
	private Pane rootPane ;
	public View (List<Event> events, List<ShowRoom> showrooms, Pane rootPane) {
		this.events = events ;
		this.showrooms = showrooms ;
		this.rootPane = rootPane ;
		display() ;
	}
	public void display() {
		Pane top = new Pane() ;
		int xPos = 10, yPos = 30 ;
		for (Event ev : events) {
			EventView evt = null ;
			if (ev instanceof Drama) { // DEGUEULASSE : il faut faire des ModelEvents generiques je pense
				Drama d = (Drama) ev ;
				evt = new EventView(d.getTitleName().getName(), d.getStartDate().getYear(), d.getStartDate().getMonth(),
						d.getStartDate().getDay(), d.getPlaceNumber(), new ModelEvent(), e -> {
							System.err.println("A faire : definir les ModelEvents correctement") ;
						}) ;
			} else if (ev instanceof Concert) {
				Concert c = (Concert) ev ;
				evt = new EventView(c.getArtistName().getName(), c.getDate().getYear(),
						c.getDate().getMonth(), c.getDate().getDay(), c.getPlaceNumber(),
						new ModelEvent(), e -> {
							System.err.println("A faire : definir les ModelEvents correctement") ;
						}) ;
			}
			evt.setTranslateX(xPos);
			evt.setTranslateY(yPos);
			yPos +=30 ;
			top.getChildren().add(evt) ;
		}
		rootPane.getChildren().add(top) ;
	}
}
