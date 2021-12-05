package user.view;

import java.util.function.Consumer;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import user.model.ModelEvent;

public abstract class EventView extends Pane {
	private static Font fNormal = new Font("Arial", 10), fSelected = Font.font("Arial", FontWeight.BLACK, 10.) ;
	private boolean isSelected = false ;
	private Text t ;
	private final ModelEvent correspEvent ;
	public EventView(ModelEvent correspEvent, Consumer<ModelEvent> callOnClick) {
		super() ;
		this.correspEvent = correspEvent ;
		String representation = getName() + getDate() + getNBPlaces() +" places\n" ;
		t = new Text(representation) ;

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
				callOnClick.accept(correspEvent) ;
			}
		});
	}
	public String getName() {
		return correspEvent.getName() +"\n";
	}
	public int getNBPlaces() {
		return correspEvent.getNbPlaces();
	}
	protected ModelEvent getCorrespEvent() {
		return correspEvent ;
	}
	public abstract String getDate() ;
	public double getTextWidth() {
		return t.getLayoutBounds().getWidth() + 20;
	}
	public double getTextHeight() {
		return t.getLayoutBounds().getHeight() + 20;
	}
	

}
