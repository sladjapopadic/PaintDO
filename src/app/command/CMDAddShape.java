package app.command;

import app.mvc.AppModel;
import geometry.Shape;

public class CMDAddShape implements Command{

	private static final long serialVersionUID = 1L;
	private AppModel model;
	private Shape shape;
	
	public CMDAddShape(AppModel model, Shape shape) {
		this.model = model;
		this.shape = shape;
	}
	
	@Override
	public void execute() {
		model.add(shape);
	}

	@Override
	public void unexecute() {
		model.remove(shape);
	}
	
	@Override
	public String toString() {
		return "add:" + shape.toString();
	}
	
}
