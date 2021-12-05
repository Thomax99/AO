package user.view;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import com.sun.prism.paint.Color;

import domain.Concert;
import domain.Drama;
import domain.Event;
import domain.ShowRoom;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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
	public void notifyError(String error) {
		 if (! Platform.isFxApplicationThread()) {
	         Platform.runLater(() -> notifyError(error));
	         return;
	    }
		Font f = new Font("Arial", 35) ;
		Text t = new Text(error) ;
		t.setFont(f);

		StackPane secondaryLayout = new StackPane();
		secondaryLayout.getChildren().add(t);

		Scene secondScene = new Scene(secondaryLayout, t.getLayoutBounds().getWidth()+30, t.getLayoutBounds().getHeight()*8);
		

		// New window (Stage)
		Stage newWindow = new Stage();
		newWindow.setTitle("Erreur ...");
		newWindow.setScene(secondScene);
		Button b = new Button("OK") ;
		b.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				newWindow.close();
			}
		}) ;
		b.setTranslateY( t.getLayoutBounds().getHeight()*2);
		secondaryLayout.getChildren().add(b) ;

		// Set position of second window, related to primary window.

		newWindow.show();
	}
	
}

