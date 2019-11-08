package app.mvc;

import java.awt.Graphics;
import java.util.Iterator;

import javax.swing.JPanel;

import geometry.Shape;

public class AppView extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private AppModel model;

	public void setModel(AppModel model) {
		this.model = model;
	}

	public AppModel getModel() {
		return this.model;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (model != null) {
			Iterator<Shape> it = model.getAll().iterator();
			while (it.hasNext()) {
				it.next().draw(g);
			}
		}
		repaint();
	}

}
