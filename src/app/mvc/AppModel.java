package app.mvc;

import java.io.Serializable;
import java.util.ArrayList;

import geometry.Shape;

public class AppModel implements Serializable {

	private static final long serialVersionUID = 1L;
	private ArrayList<Shape> listOfShapes = new ArrayList<Shape>();

	public AppModel() {

	}

	public void add(Shape s) {
		listOfShapes.add(s); 
	}

	public void remove(Shape s) {
		listOfShapes.remove(s);
	}

	public Shape get(int index) {
		return listOfShapes.get(index);
	}

	public ArrayList<Shape> getAll() {
		return listOfShapes;
	}
}
