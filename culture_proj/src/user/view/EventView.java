package user.view;

import java.util.function.Consumer;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import user.model.ModelEvent;

public class EventView extends Pane {
	private static Font fNormal = new Font("Arial", 10), fSelected = new Font("Arial", 15) ;
	public EventView(ModelEvent correspEvent, Consumer<ModelEvent> callOnClick) {
		super() ;
		Text t = new Text("Évenement : " + correspEvent.getName() + " du " + correspEvent.getStartDay() +"/"+correspEvent.getStartMonth()+"/"+
						correspEvent.getStartYear()+ "au" + correspEvent.getEndDay() +"/"+correspEvent.getEndMonth()+"/"+
								correspEvent.getEndYear()+" avec "+ correspEvent.getNbPlaces() +" places") ;
		getChildren().add(t) ;
		t.setFont(fNormal);
		setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				t.setFont(fSelected);
				callOnClick.accept(correspEvent) ; // TODO
			}
		});
	}

}
