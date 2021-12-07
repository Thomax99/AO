package user.view;

import java.util.function.BiConsumer;

import user.model.ModelConcert;
import user.model.ModelEvent;
import user.model.ModelShowroom;

public class ConcertView extends EventView{
	public ConcertView(ModelConcert correspEvent, ModelShowroom showroomAttached, BiConsumer<ModelShowroom, ModelEvent> callOnClick) {
		super(correspEvent, showroomAttached, callOnClick);
	}

	public ConcertView(ModelConcert correspEvent, BiConsumer<ModelShowroom, ModelEvent> callOnClick) {
		super(correspEvent, callOnClick);
	}

	@Override
	public String getDate() {
		ModelConcert c = (ModelConcert) getCorrespEvent() ;
		return "Le " + c.getDay()+"/"+c.getMonth()+"/"+c.getYear()+"\n";
	}

}
