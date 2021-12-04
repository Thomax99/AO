package user.view;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import domain.Concert;
import domain.Drama;
import domain.Event;
import domain.ShowRoom;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import user.Controller;
import user.model.ModelEvent;
import user.model.ModelShowroom;

public class View {
	private List<Event> events ;
	private List<ShowRoom> showrooms ;
	private Pane rootPane ;
	private Consumer<ModelEvent> callOnEventClick ;
	private Consumer<ModelShowroom> callOnShowroomClick ;
	public View (List<Event> events, List<ShowRoom> showrooms, Pane rootPane, Consumer<ModelEvent> callOnEventClick, Consumer<ModelShowroom> callOnShowroomClick) {
		this.events = events ;
		this.showrooms = showrooms ;
		this.rootPane = rootPane ;
		this.callOnEventClick = callOnEventClick ;
		this.callOnShowroomClick = callOnShowroomClick ;
		display() ;
	}
	public void display() {
		Pane top = new Pane() ;
		int xPos = 10, yPos = 30 ;
		for (Event ev : events) {
			ModelEvent evt = null ;
			
			if (ev instanceof Drama) { // DEGUEULASSE : il faut faire des ModelEvents generiques je pense
				Drama d = (Drama) ev ;
				evt = new ModelEvent(d.getStartDate().getYear(), d.getStartDate().getMonth(), d.getStartDate().getDay(),
						d.getEndDate().getYear(), d.getEndDate().getMonth(), d.getEndDate().getDay(),d.getPlaceNumber(),  d.getTitleName().getName(), d.getRef()) ;
				
			
			} else if (ev instanceof Concert) {
				Concert c = (Concert) ev ;
				evt = new ModelEvent(c.getDate().getYear(), c.getDate().getMonth(), c.getDate().getDay(),
						c.getDate().getYear(), c.getDate().getMonth(), c.getDate().getDay(), c.getPlaceNumber(),  c.getArtistName().getName(), c.getRef()) ;
			}
			EventView e = new EventView(evt, callOnEventClick) ;
			e.setTranslateX(xPos);
			e.setTranslateY(yPos);
			yPos +=30 ;
			top.getChildren().add(e) ;
		}
		for (ShowRoom room : showrooms) {
				List<ModelEvent> evts = new LinkedList<>() ;
				for (Event evt : room.getEvents()) {
					ModelEvent md = null ;
					
					if (evt instanceof Drama) { // DEGUEULASSE : il faut faire des ModelEvents generiques je pense
						Drama d = (Drama) evt ;
						md = new ModelEvent(d.getStartDate().getYear(), d.getStartDate().getMonth(), d.getStartDate().getDay(),
								d.getEndDate().getYear(), d.getEndDate().getMonth(), d.getEndDate().getDay(),d.getPlaceNumber(),  d.getTitleName().getName(), d.getRef()) ;
						
					
					} else if (evt instanceof Concert) {
						Concert c = (Concert) evt ;
						md = new ModelEvent(c.getDate().getYear(), c.getDate().getMonth(), c.getDate().getDay(),
								c.getDate().getYear(), c.getDate().getMonth(), c.getDate().getDay(), c.getPlaceNumber(),  c.getArtistName().getName(), c.getRef()) ;
					}
					evts.add(md) ;
				}
				
				ModelShowroom r = new ModelShowroom(room.getCapacity(), evts,room.getOpenDates(), room.getId()) ;
				ShowRoomView s = new ShowRoomView(r, callOnShowroomClick) ;
				s.setTranslateX(xPos);
				s.setTranslateY(yPos);
				yPos+=270 ;
				top.getChildren().add(s) ;
			}
		rootPane.getChildren().add(top) ;
	}
}

