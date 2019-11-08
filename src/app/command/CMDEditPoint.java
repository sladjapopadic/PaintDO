package app.command;

import geometry.Point;

public class CMDEditPoint implements Command {

	private static final long serialVersionUID = 1L;
	private Point oldState;
	private Point newState;
	private Point originalState = new Point();

	public CMDEditPoint(Point oldState, Point newState) {
		this.oldState = oldState;
		this.newState = newState;
	}

	@Override
	public void execute() {
		originalState = oldState.clone();

		oldState.setX(newState.getX());
		oldState.setY(newState.getY());
		oldState.setOutlineColor(newState.getOutlineColor());
	}

	@Override
	public void unexecute() {
		oldState.setX(originalState.getX());
		oldState.setY(originalState.getY());
		oldState.setOutlineColor(originalState.getOutlineColor());
	}

	@Override
	public String toString() {
		return "edit:" + originalState.toString() + ",to:" + newState;
	}

}