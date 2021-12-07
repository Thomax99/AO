package user.view;

import java.util.function.BiConsumer;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import user.model.ModelEvent;
import user.model.ModelShowroom;

public abstract class EventView extends Pane {
	private static Font fNormal = new Font("Arial", 10), fSelected = Font.font("Arial", FontWeight.BLACK, 10.) ;
	private boolean isSelected = false ;
	private Text t ;
	private final ModelEvent correspEvent ;
	private ModelShowroom showroomAttached;
	public EventView(ModelEvent correspEvent, ModelShowroom showroomAttached, BiConsumer<ModelShowroom, ModelEvent> callOnClick) {
		this(correspEvent, callOnClick) ;
		this.showroomAttached = showroomAttached ;
	}
	public EventView(ModelEvent correspEvent, BiConsumer<ModelShowroom, ModelEvent> callOnClick) {
		super() ;
		this.showroomAttached = null ;
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
				callOnClick.accept(showroomAttached, correspEvent) ;
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
