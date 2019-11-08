package app.command;

import app.mvc.AppModel;
import geometry.Shape;

public class CMDBringToFront implements Command {

	private static final long serialVersionUID = 1L;
	private Shape s;
	private AppModel model;
	private int index;

	public CMDBringToFront(Shape s, AppModel model) {
		this.s = s;
		this.model = model;
		this.index = model.getAll().indexOf(s);
	}

	@Override
	public void execute() {
		model.remove(s);
		model.add(s);
	}

	@Override
	public void unexecute() {
		model.remove(s);
		model.getAll().add(index, s);
	}

	@Override
	public String toString() {
		return "bringtofront:" + s.toString();
	}

}
