package app.command;

import geometry.Square;

public class CMDEditSquare implements Command {

	private static final long serialVersionUID = 1L;
	private Square oldState;
	private Square newState;
	private Square originalState = new Square();

	public CMDEditSquare(Square oldState, Square newState) {
		this.oldState = oldState;
		this.newState = newState;
	}

	@Override
	public void execute() {
		originalState = oldState.clone();

		oldState.setUpperLeft(newState.getUpperLeft());
		oldState.setSide(newState.getSide());
		oldState.setOutlineColor(newState.getOutlineColor());
		oldState.setInsideColor(newState.getInsideColor());
	}

	@Override
	public void unexecute() {
		oldState.setUpperLeft(originalState.getUpperLeft());
		oldState.setSide(originalState.getSide());
		oldState.setOutlineColor(originalState.getOutlineColor());
		oldState.setInsideColor(originalState.getInsideColor());
	}
	
	@Override
	public String toString() {
		return "edit:" + originalState.toString() + ",to:" + newState;
	}

}
