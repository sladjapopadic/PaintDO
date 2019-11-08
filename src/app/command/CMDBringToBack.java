package app.command;

import app.mvc.AppModel;
import geometry.Shape;

public class CMDBringToBack implements Command {

	private static final long serialVersionUID = 1L;
	private Shape s;
	private AppModel model;
	private int index;

	public CMDBringToBack(Shape s, AppModel model) {
		this.s = s;
		this.model = model;
		this.index = model.getAll().indexOf(s);
	}

	@Override
	public void execute() {
		model.remove(s);
		model.getAll().add(0, s);
	}

	@Override
	public void unexecute() {
		model.remove(s);
		model.getAll().add(index, s);
	}

	@Override
	public String toString() {
		return "bringtoback:" + s.toString();
	}

}
