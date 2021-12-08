import java.util.LinkedList;
import java.util.List;

import application.CQRS;
import application.CityService;
import application.CommandBag;
import application.Worker;
import domain.City;
import domain.Concert;
import domain.Drama;
import domain.EventCatalog;
import domain.OpenDate;
import domain.Repository;
import domain.ShowRoom;
import infra.RepoProxy;
import infra.RepositoryInMemory;
import infra.RepositoryInXml;
import javafx.application.Application;
import javafx.stage.Stage;
import user.controller.Controller;

public class Main extends Application {

	
	public static void main(String[] args) {

		launch(args);
        }

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		
		
		Repository repo = new RepoProxy() ;

		City city = repo.findCityById(0) ;
		
		if (city == null) {
			List<Concert> concerts = new LinkedList<>() ;
			List<Drama> dramas = new LinkedList<>() ;
			concerts.add(new Concert(2022, 5, 13, "Joseph Allard", 600)) ;
			concerts.add(new Concert(2022, 5, 13, "La Bolduc", 400)) ;
			concerts.add(new Concert(2022, 5, 13, "Les Indiens", 12)) ;
			concerts.add(new Concert(2022, 5, 14, "ZenBamboo", 40)) ;
			concerts.add(new Concert(2022, 5, 14, "WD-40", 800)) ;
			dramas.add(new Drama(2022, 5, 14, 2022, 5, 25, "Le barbier de Séville", 5)) ;
			dramas.add(new Drama(2022, 5, 14, 2022, 8, 14, "Le barbier de Kaboul", 500)) ;
			dramas.add(new Drama(2022, 5, 14, 2022, 8, 14, "Le barbier de Paris", 60)) ;
			dramas.add(new Drama(2022, 5, 14, 2022, 8, 14, "Le barbier de Alger", 200)) ;
			dramas.add(new Drama(2022, 5, 14, 2022, 8, 14, "Le barbier de Tanger", 20)) ;
			concerts.add(new Concert(2022, 6, 10, "Philippe Bruneau", 50)) ;
			concerts.add(new Concert(2022, 6, 10, "Les Charbonniers de l'Enfer", 20)) ;
			concerts.add(new Concert(2022, 6, 10, "Garolou", 30)) ;
			concerts.add(new Concert(2022, 6, 10, "Alfred Montmarquette", 25)) ;
			EventCatalog catalog = new EventCatalog(concerts, dramas) ;
			
			List<OpenDate> dates = new LinkedList<>() ;
			for (int i = 1 ; i < 31 ; i++) {
				dates.add(new OpenDate(2022, 5, i, 8)) ;
				dates.add(new OpenDate(2022, 6, i, 8)) ;
				dates.add(new OpenDate(2022, 7, i, 8)) ;
				dates.add(new OpenDate(2022, 8, i, 8)) ;
			}
			
			ShowRoom r1 = new ShowRoom(dates, 150) ;
			ShowRoom r2 = new ShowRoom(dates, 120) ;
			ShowRoom r3 = new ShowRoom(dates, 50) ;
			ShowRoom r4 = new ShowRoom(dates, 800) ;
			
			city = new City(catalog) ;
			city.addShowRoom(r1);
			city.addShowRoom(r2);
			city.addShowRoom(r3);
			city.addShowRoom(r4);
			
			repo.save(city);
		}
		
		
		
		CommandBag bag = new CommandBag() ;
		Worker w = new Worker(bag) ;
		w.start() ;
		List<Worker> workers = new LinkedList<>() ;
		workers.add(w) ;
		CityService service = new CityService(repo, workers) ;
		CQRS.setCityService(service);
		
		Controller c = new Controller(bag, city.getId(), primaryStage) ;
		service.addObserver(c);
		
	}
}
