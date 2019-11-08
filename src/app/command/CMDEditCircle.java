package app.command;

import geometry.Circle;

public class CMDEditCircle implements Command {

	private static final long serialVersionUID = 1L;
	private Circle oldState;
	private Circle newState;
	private Circle originalState = new Circle();

	public CMDEditCircle(Circle oldState, Circle newState) {
		this.oldState = oldState;
		this.newState = newState;
	}

	@Override
	public void execute() {
		originalState = oldState.clone();

		oldState.setCenter(newState.getCenter());
		oldState.setRadius(newState.getRadius());
		oldState.setOutlineColor(newState.getOutlineColor());
		oldState.setInsideColor(newState.getInsideColor());
	}

	@Override
	public void unexecute() {
		oldState.setCenter(originalState.getCenter());
		oldState.setRadius(originalState.getRadius());
		oldState.setOutlineColor(originalState.getOutlineColor());
		oldState.setInsideColor(originalState.getInsideColor());
	}
	
	@Override
	public String toString() {
		return "edit:" + originalState.toString() + ",to:" + newState;
	}


}
