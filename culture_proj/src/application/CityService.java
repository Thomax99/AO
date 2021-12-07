package application;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.TreeMap;

import domain.City;
import domain.Concert;
import domain.DateUtilitaries;
import domain.Drama;
import domain.Event;
import domain.Repository;
import domain.ShowRoom;
import exceptions.MultilangException;
import exceptions.NotEnoughPlaceException;
import exceptions.NotExistantCityException;
import exceptions.NotExistantEventException;
import exceptions.NotExistantShowRoomException;
import exceptions.NotOpenedShowRoomException;
import infra.EventCatalog;

public class CityService extends Observable {
	private Repository repo ;
	private EventCatalog events ;
	private Map<Integer, String> errors ;
	private Map<Integer, Boolean> hasBeenVerified ;
	private Map<Integer, String> verificationMessages ;
	private int nbCitiesAttached;
	private List<Worker> workers;
	public CityService(Repository repo, EventCatalog events, List<Worker> workers) {
		errors = new TreeMap<>() ;
		this.repo = repo ;
		this.events = events ;
		this.nbCitiesAttached = repo.getNumberCities() ;
		hasBeenVerified = new TreeMap<>() ;
		verificationMessages = new TreeMap<>() ;
		this.workers = new LinkedList<>(workers) ;
	}
	public List<Concert> getConcerts() {
		return events.getConcerts() ;
	}
	public List<Drama> getDramas() {
		return events.getDramas() ;
	}
	public List<Event> getEvents() {
		List<Event> events = new LinkedList<>(getDramas()) ;
		events.addAll(getConcerts()) ;
		return events ;
	}
	private List<ShowRoom> _getShowRooms(int cityId) throws NotExistantCityException {
		if (repo.findCityById(cityId) == null) {
			throw new NotExistantCityException(cityId) ;
		}
		return repo.findCityById(cityId).getShowrooms() ;
	}
	public List<ShowRoom> getShowRooms(int cityId) {
		try {
			return _getShowRooms(cityId) ;
		} catch (NotExistantCityException e) {
			errors.put(cityId, e.getMessageFR()) ;
	        setChanged();
			this.notifyObservers();
		}
		return null ;
	}

	public void addEvent(int cityId, int showRoomId, int eventRef) {
		try {
			_addEvent(cityId, showRoomId, eventRef) ;
		} catch (MultilangException e) {
			errors.put(cityId, e.getMessageFR()) ;
		}
        setChanged();
		this.notifyObservers();
	}
	public void removeEvent(int cityId, int showroomId, int eventRef) {
		try {
			_removeEvent(cityId, showroomId, eventRef) ;
		} catch (MultilangException e) {
			errors.put(cityId, e.getMessageFR()) ;
		}
        setChanged();
		this.notifyObservers();
	}
	private void _removeEvent(int cityId, int showroomId, int eventRef) throws NotExistantCityException, NotExistantShowRoomException, NotExistantEventException {
		City city = repo.findCityById(cityId) ;
		if (city == null) {
			throw new NotExistantCityException(cityId) ;
		}
		ShowRoom showroom = city.getShowroom(showroomId) ;
		if (showroom == null)
			throw new NotExistantShowRoomException(showroomId) ;
		Event evt = showroom.removeEvent(eventRef) ;
		if (evt == null) {
			throw new NotExistantEventException(eventRef) ;
		}
		if (evt instanceof Concert) {
			events.addConcert((Concert)evt) ;
		} else if (evt instanceof Drama) {
			events.addDrama((Drama)evt) ;
		}
	}
	private void _addEvent(int cityId, int showRoomId, int eventRef) throws NotExistantCityException, NotExistantShowRoomException,
				NotOpenedShowRoomException, NotEnoughPlaceException, NotExistantEventException {
		if (repo.findCityById(cityId) == null) {
			throw new NotExistantCityException(cityId) ;
		}
		Concert conc = null ;
		for (Concert ev : events.getConcerts()) {
			if (ev.getRef() == eventRef) {
				conc = ev ;
				break ;
			}
		}
		Drama d = null ;
		for (Drama ev : events.getDramas()) {
			if (ev.getRef() == eventRef) {
				d = ev ;
				break ;
			}
		}
		if (conc == null && d == null) {
			throw new NotExistantEventException(eventRef) ;
		}
		City city = repo.findCityById(cityId) ;
		ShowRoom showroom = city.getShowroom(showRoomId) ;
		if (showroom == null)
			throw new NotExistantShowRoomException(showRoomId) ;
		if (conc != null)
			showroom.addEvent(conc);
		else
			showroom.addEvent(d);
		if (conc != null)
			events.removeConcert(conc);
		else
			events.removeDrama(d);
	}
	public String getError(int cityId) {
		String output = errors.get(cityId) ;
		errors.remove(cityId) ;
		return output;
	}
	public String getVerificationError(int cityId) {
		if (hasBeenVerified.get(cityId) == null || !hasBeenVerified.get(cityId)) {
			return null ;
		}
		hasBeenVerified.put(cityId, false) ;
		String output = verificationMessages.get(cityId) ;
		verificationMessages.remove(cityId) ;
		return output ;
		
	}
	/**
	 * Méthode pour vérifier si il n'y a pas des problèmes
	 * Les contraintes :
	 * - si il y a une semaine qui ne contient aucun concert, mais il en reste un de disponible et mettable sur cette semaine-là
	 * - si il reste un drama par exemple qui peut aller un samedi ou dimanche alors qu'il n'y a rien le samedi ou le dimanche mais
	 *   qu'un concert empêche que le drama se mette
	 */
	public void verify(int cityId, int showroomId) {
		boolean error = false ;
		String currentError = verificationMessages.get(cityId)  ;
		if (currentError == null)
			currentError =  "" ;
		currentError += "\nshowroom " + showroomId +" :\n";
		
		// premiere partie : verificat° des concerts ...
		// idee : on regarde les concerts dispos
		// si il y en a un de disponible, alors on regarde si il est posable sur la date (quitte a remplacer un drama) et si il y en a pas sur la semaine
		// de ce concert
		// si c'est le cas, on met une erreur
		ShowRoom sr = repo.findCityById(cityId).getShowroom(showroomId) ;
		
		for (Concert concDisp : events.getConcerts()) {
			if (sr.getCapacity() >= concDisp.getPlaceNumber() && sr.getOpenHour(concDisp.getDate()) != -1) {
				// la salle est ouverte a cette date et on peut y mettre l'event
				// il faut regarder si un concert est deja proposee la semaine de concDisp
				boolean hasConcert = false ;
				Date startWeek = DateUtilitaries.getFirstDayOfWeek(concDisp.getDate()) ;
				Date endWeek = DateUtilitaries.getLastDayOfWeek(concDisp.getDate()) ;
				while (startWeek.before(endWeek) || startWeek.equals(endWeek)) {
					Event evt = sr.getProgrammationOfDate(startWeek) ;
					if (evt != null && evt instanceof Concert) {
						hasConcert = true ;
					}
					startWeek = DateUtilitaries.getNextDate(startWeek) ;
				}
				if (!hasConcert) {
					// on a un concert posable et il y a pas de concerts sur cette semaine la
					error = true ;
					currentError += ("erreur : une semaine ne contient pas de concert alors qu'elle le pourrait\n") ; 
				}
			}
		}
		// reste maintenant a verifier si il y a un drama qui pourrait aller sur un week-end ou il n'y a rien
		// et que cet event ne peut etre pose a cause d'un concert ou juste il n'a pas ete mis
		// idee : on regarde tous les events
		// on regarde si ils peuvent etre dans la salle, et si ils ont lieu un samedi ou un dimanche (au moins en partie)
		// si oui, on regarde si sur ces week-end, il y a un event et si il y en a pas, on regarde si
		// ces evenements peuvent etre mis, ou si il y a un event qui les empeche d'etre la
		
		for (Drama dramDisp : events.getDramas()) {
			boolean canBePut = true ;
			boolean isOnWeekEnd = false, blockedByDrama = false ;
			if (dramDisp.getPlaceNumber() > sr.getCapacity()) {
				canBePut = false ;
			}
			Date startDate = dramDisp.getStartDate(), endDate = dramDisp.getEndDate() ;
			while (startDate.before(endDate) || startDate.equals(endDate)) {
				if (DateUtilitaries.isOnWeekEnd(startDate)) {
					isOnWeekEnd = true ;
				}
				if (sr.getOpenHour(startDate) == -1) {
					canBePut = false ;
				}
				if (sr.getProgrammationOfDate(startDate) != null && sr.getProgrammationOfDate(startDate) instanceof Drama) {
					blockedByDrama = true ;
				}
				startDate = DateUtilitaries.getNextDate(startDate) ;
			}
			if (canBePut && isOnWeekEnd && !blockedByDrama) {
				// error ...
				error = true ;
				currentError += ("erreur : une pièce n'est pas programmée en week-end alors qu'elle le pourrait\n") ; 
			}
		}
		
		
		hasBeenVerified.put(cityId, true) ;
		if (error) {
			verificationMessages.put(cityId, currentError) ;
		} else {
			verificationMessages.put(cityId, "pas d'erreur dans la vérification de la showroom " + sr.getId()) ;
		}
        setChanged();
		this.notifyObservers();
	}
	public void removeCity() {
		nbCitiesAttached -- ;		
	}
	public int getNBCities() {
		return nbCitiesAttached ;
	}
	public List<Worker> getWorkers() {
		return workers ;
	}
}
