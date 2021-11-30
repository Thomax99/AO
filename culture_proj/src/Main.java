import java.util.LinkedList;
import java.util.List;

import application.CQRS;
import application.CityService;
import application.Command;
import application.CommandBag;
import application.Worker;
import domain.City;
import domain.Concert;
import domain.Drama;
import domain.Event;
import domain.OpenDate;
import domain.Repository;
import domain.ShowRoom;
import infra.EventCatalog;
import infra.RepositoryInMemory;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		Button button = new Button();
		button.setText("Open a New Window");

		button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				Label secondLabel = new Label("I'm a Label on new Window");

				StackPane secondaryLayout = new StackPane();
				secondaryLayout.getChildren().add(secondLabel);

				Scene secondScene = new Scene(secondaryLayout, 230, 100);

				// New window (Stage)
				Stage newWindow = new Stage();
				newWindow.setTitle("Second Stage");
				newWindow.setScene(secondScene);

				// Set position of second window, related to primary window.
				newWindow.setX(primaryStage.getX() + 200);
				newWindow.setY(primaryStage.getY() + 100);

				newWindow.show();
			}
		});

		StackPane root = new StackPane();
		root.getChildren().add(button);

		Scene scene = new Scene(root, 450, 250);

		primaryStage.setTitle("JavaFX Open a new Window (o7planning.org)");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		List<Event> events = new LinkedList<>() ;
		events.add(new Concert(2022, 5, 13, "Joseph Allard", 600)) ;
		events.add(new Concert(2022, 5, 13, "La Bolduc", 400)) ;
		events.add(new Concert(2022, 5, 13, "Les Indiens", 12)) ;
		events.add(new Concert(2022, 5, 14, "ZenBamboo", 40)) ;
		events.add(new Concert(2022, 5, 14, "WD-40", 800)) ;
		events.add(new Drama(2022, 5, 14, 2022, 8, 14, "Le barbier de Séville", 5)) ;
		events.add(new Drama(2022, 5, 14, 2022, 8, 14, "Le barbier de Kaboul", 500)) ;
		events.add(new Drama(2022, 5, 14, 2022, 8, 14, "Le barbier de Paris", 60)) ;
		events.add(new Drama(2022, 5, 14, 2022, 8, 14, "Le barbier de Alger", 200)) ;
		events.add(new Drama(2022, 5, 14, 2022, 8, 14, "Le barbier de Tanger", 20)) ;
		events.add(new Concert(2022, 6, 10, "Philippe Bruneau", 50)) ;
		events.add(new Concert(2022, 6, 10, "Les Charbonniers de l'Enfer", 20)) ;
		events.add(new Concert(2022, 6, 10, "Garolou", 30)) ;
		events.add(new Concert(2022, 6, 10, "Alfred Montmarquette", 25)) ;
		EventCatalog catalog = new EventCatalog(events) ;
		
		List<OpenDate> dates = new LinkedList<>() ;
		for (int i = 0 ; i < 10 ; i++) {
			dates.add(new OpenDate(2022, 5, i, 8)) ;
		}
		
		ShowRoom r1 = new ShowRoom(dates, 150) ;
		ShowRoom r2 = new ShowRoom(dates, 120) ;
		ShowRoom r3 = new ShowRoom(dates, 50) ;
		ShowRoom r4 = new ShowRoom(dates, 800) ;
		
		City city = new City() ;
		city.addShowRoom(r1);
		city.addShowRoom(r2);
		city.addShowRoom(r3);
		city.addShowRoom(r4);
		
		Repository repo = new RepositoryInMemory() ;
		repo.save(city);
		
		CommandBag bag = new CommandBag() ;
		Worker w = new Worker(bag) ;
		w.start() ;
		
		CityService service = new CityService(repo, catalog) ;
		CQRS.setCityService(service);
		
		
		launch(args);
	}

}
