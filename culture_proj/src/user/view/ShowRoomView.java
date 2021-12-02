package user.view;

import java.util.function.Consumer;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import user.model.ModelShowroom;

public class ShowRoomView extends Pane {
	public ShowRoomView(ModelShowroom model, Consumer<ModelShowroom> callOnClick) {
		super() ;
		String rpz = "Salle a capacité de " +model.getCapacity() +"\n" ;
		for (String str : model.getStringRepresentation()) {
			rpz += (str+"\n") ;
		}
		Text t = new Text(rpz) ;
		getChildren().add(t) ;
		setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				callOnClick.accept(model) ;
			}
		});
	}

}
