package app.command;

import java.util.ArrayList;

import app.mvc.AppModel;
import geometry.Shape;

public class CMDRemoveShape implements Command {

	private static final long serialVersionUID = 1L;
	private AppModel model;
	private ArrayList<Shape> shapes = new ArrayList<Shape>();

	public CMDRemoveShape(AppModel model, ArrayList<Shape> shapes) { 
		this.model = model;
		this.shapes = shapes;
	}

	@Override
	public void execute() {
		for (Shape s : shapes) {
			model.remove(s);
		}
	}

	@Override
	public void unexecute() {
		for (Shape s : shapes) {
			model.add(s);
		}
	}

	@Override
	public String toString() {
		if (shapes.size() == 1) {
			return "remove:" + shapes.get(0).toString();
		} else {
			String text = "remove:";

			for (Shape s : shapes) {
				if (shapes.indexOf(s) == shapes.size() - 1) {
					text = text + s.toString();
				} else {
					text = text + s.toString() + ":";
				}
			}
			return text;
		}
	}
}
