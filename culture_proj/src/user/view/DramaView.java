package user.view;

import java.util.function.BiConsumer;

import user.model.ModelDrama;
import user.model.ModelEvent;
import user.model.ModelShowroom;

public class DramaView extends EventView {
	public DramaView(ModelDrama correspEvent, ModelShowroom showroomAttached, BiConsumer<ModelShowroom, ModelEvent> callOnClick) {
		super(correspEvent, showroomAttached, callOnClick);
	}

	public DramaView(ModelDrama correspEvent, BiConsumer<ModelShowroom, ModelEvent> callOnClick) {
		super(correspEvent, callOnClick);
	}

	@Override
	public String getDate() {
		ModelDrama d = (ModelDrama) getCorrespEvent() ;
		return "Du " + d.getStartDay()+"/"+d.getStartMonth()+"/"+d.getStartYear() +"\nAu "+ d.getEndDay()+"/"+d.getEndMonth()+"/"+d.getEndYear()+"\n";
	}

}
