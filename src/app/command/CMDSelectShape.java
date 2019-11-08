package app.command;

import geometry.Shape;

public class CMDSelectShape implements Command{

	private static final long serialVersionUID = 1L;
	private Shape s;
	
	public CMDSelectShape(Shape s) {
		this.s = s;
	}
	
	@Override
	public void execute() {
		s.setSelected(true);
	}

	@Override
	public void unexecute() {
		s.setSelected(false);
	}
	
	@Override
	public String toString() {
		return "select:" + s.toString();
	}

}
