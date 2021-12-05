package user.view;

import java.util.function.Consumer;

import user.model.ModelDrama;
import user.model.ModelEvent;

public class DramaView extends EventView {

	public DramaView(ModelEvent correspEvent, Consumer<ModelEvent> callOnClick) {
		super(correspEvent, callOnClick);
	}

	@Override
	public String getDate() {
		ModelDrama d = (ModelDrama) getCorrespEvent() ;
		return "Du " + d.getStartDay()+"/"+d.getStartMonth()+"/"+d.getStartYear() +"\nAu "+ d.getEndDay()+"/"+d.getEndMonth()+"/"+d.getEndYear()+"\n";
	}

}
