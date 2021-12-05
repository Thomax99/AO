package user.view;

import java.util.function.Consumer;

import user.model.ModelConcert;
import user.model.ModelEvent;

public class ConcertView extends EventView{
	public ConcertView(ModelEvent correspEvent, Consumer<ModelEvent> callOnClick) {
		super(correspEvent, callOnClick);
	}

	@Override
	public String getDate() {
		ModelConcert c = (ModelConcert) getCorrespEvent() ;
		return "Le " + c.getDay()+"/"+c.getMonth()+"/"+c.getYear()+"\n";
	}

}
