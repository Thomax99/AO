package user.view;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import domain.DateUtilitaries;
import domain.OpenDate;
import exceptions.ForbiddenDateException;
import exceptions.ForbiddenHourException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import user.model.ModelConcert;
import user.model.ModelDrama;
import user.model.ModelEvent;
import user.model.ModelShowroom;

public class ShowRoomView extends Pane {
	private final Text t;
	public ShowRoomView(ModelShowroom model, Consumer<ModelShowroom> callOnClick, Consumer<ModelShowroom> callOnVerifClick,
				BiConsumer<ModelShowroom, ModelEvent> callOnEventClick) {
		super() ;
		String rpz = "Salle a capacité de " +model.getCapacity() +"\nÈvenements disponibles : \n" ;
		t = new Text(rpz) ;
		t.setFont(new Font("Arial", 15));
		getChildren().add(t) ;
		double yDec = t.getLayoutBounds().getHeight()+10 ;
		for (ModelConcert conc : model.getConcerts()) {
			ConcertView viewConc = new ConcertView(conc, model, callOnEventClick) ;
			viewConc.setTranslateY(yDec);
			getChildren().add(viewConc) ;
			yDec += viewConc.getTextHeight() + 5 ;
		}
		for (ModelDrama drama : model.getDramas()) {
			DramaView viewDrama = new DramaView(drama, model, callOnEventClick) ;
			viewDrama.setTranslateY(yDec);
			getChildren().add(viewDrama) ;
			yDec += viewDrama.getTextHeight() + 5 ;
		}
		OpenDate startDate = null, endDate = null ;
		rpz = "Dates encore disponiblles : \n" ;
		for (OpenDate d : model.getOpenDates()) {
			if (startDate == null) {
				startDate = d ;
				endDate = d ;
			} else {
				try {
					if (endDate.next().equals(d)) {
						endDate = d ;
					} else {
						if (startDate.equals(endDate)) {
							rpz += ("le" + DateUtilitaries.getDay(startDate.getOpenDay()) +"/" + DateUtilitaries.getMonth(startDate.getOpenDay())+"/"+DateUtilitaries.getYear(startDate.getOpenDay()) + " à " + startDate.getOpenHour()+" h\n") ;
						} else {
							rpz+=("Du " + DateUtilitaries.getDay(startDate.getOpenDay())  +"/" + DateUtilitaries.getMonth(startDate.getOpenDay()) +"/"+DateUtilitaries.getYear(startDate.getOpenDay()) +
									" au " + DateUtilitaries.getDay(endDate.getOpenDay()) +"/" + DateUtilitaries.getMonth(endDate.getOpenDay()) +"/"+DateUtilitaries.getYear(endDate.getOpenDay())  + " à " + startDate.getOpenHour() + " h\n") ;
						}
						startDate = endDate = d ;
					}
				} catch (ForbiddenHourException | ForbiddenDateException e) {
					// TODO Auto-generated catch block
					System.err.println("ERREUR FATALE : " + e.getMessageFR()) ;
					System.exit(1);
				}
			}
		}
		Text t2 = new Text(rpz) ;
		t2.setTranslateY(yDec);
		getChildren().add(t2) ;
		yDec += t2.getLayoutBounds().getHeight() + 5 ;

		setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				callOnClick.accept(model) ;
			}
		});
		Button b = new Button("Vérification") ;
		b.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				callOnVerifClick.accept(model);
			}
		});
		getChildren().add(b) ;
		b.setTranslateY(yDec);
	}
	public double getTextWidth() {
		return t.getLayoutBounds().getWidth() + 40;
	}
	public double getTextHeight() {
		return t.getLayoutBounds().getHeight() + 50;
	}

}
