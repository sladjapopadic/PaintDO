package app.command;

import java.util.ArrayList;

import app.mvc.AppFrame;
import app.mvc.AppModel;
import geometry.Shape;

public class CMDDeselectAll implements Command {

	private static final long serialVersionUID = 1L;
	private AppModel model;
	private AppFrame frame;
	private ArrayList<Shape> wasSelected;

	public CMDDeselectAll(AppModel model, AppFrame frame) {
		this.model = model;
		this.frame = frame;
		wasSelected = new ArrayList<Shape>();

		for (Shape s : model.getAll()) {
			if (s.isSelected()) {
				wasSelected.add(s);
			}
		}
	}

	@Override
	public void execute() {
		for (Shape s : model.getAll()) {
			s.setObserver(frame);
			s.setSelected(false);
		}
	}

	@Override
	public void unexecute() {
		for(Shape s : wasSelected) {
			s.setSelected(true);
		}
	}

	@Override
	public String toString() {
		return "deselectAll";
	}

}
