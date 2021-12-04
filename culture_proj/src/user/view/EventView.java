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
	private boolean isSelected = false ;
	public EventView(ModelEvent correspEvent, Consumer<ModelEvent> callOnClick) {
		super() ;
		Text t = new Text("Évenement : " + correspEvent.toString() + " avec "+ correspEvent.getNbPlaces() +" places") ;
		getChildren().add(t) ;
		t.setFont(fNormal);
		setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (isSelected) {
					t.setFont(fNormal);
				} else {
					t.setFont(fSelected);
				}
				isSelected = !isSelected ;
				callOnClick.accept(correspEvent) ; // TODO
			}
		});
	}

}
