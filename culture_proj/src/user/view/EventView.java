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
	public EventView(String name, int year, int month, int day, int nbPlaces, ModelEvent correspEvent, Consumer<ModelEvent> callOnClick) {
		super() ;
		Text t = new Text("Évenement : " + name + " le " + day +"/"+month+"/"+year+" avec "+ nbPlaces +" places") ;
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
