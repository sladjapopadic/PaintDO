package app.command;

import java.util.Collections;

import app.mvc.AppModel;
import geometry.Shape;

public class CMDToBack implements Command {

	private static final long serialVersionUID = 1L;
	private Shape s;
	private AppModel model;
	private int index;

	public CMDToBack(Shape s, AppModel model) {
		this.s = s;
		this.model = model;
	}

	@Override
	public void execute() {
		this.index = model.getAll().indexOf(s);
		Collections.swap(model.getAll(), index, index - 1);
	}

	@Override
	public void unexecute() {
		this.index = model.getAll().indexOf(s);
		Collections.swap(model.getAll(), index, index + 1);
	}

	public String toString() {
		return "toback:" + s.toString();
	}

}
