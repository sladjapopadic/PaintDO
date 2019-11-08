package geometry;

import java.awt.Color;
import java.awt.Graphics;

import hexagon.Hexagon;

public class HexagonAdapter extends AreaShape {

	private static final long serialVersionUID = 1L;
	private Hexagon h;

	public HexagonAdapter(Hexagon h) {
		this.h = h;
	}

	public int getX() {
		return this.h.getX();
	}

	public int getY() {
		return this.h.getY();
	}

	public int getR() {
		return this.h.getR();
	}

	public void setX(int x) {
		this.h.setX(x);
	}

	public void setY(int y) {
		this.h.setY(y);
	}

	public void setR(int r) {
		this.h.setR(r);
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof HexagonAdapter) {
			HexagonAdapter forwarded = (HexagonAdapter) o;
			return this.getR() - forwarded.getR();
		} else {
			return 0;
		}
	}

	@Override
	public void draw(Graphics g) {
		this.h.paint(g);
	}

	@Override
	public boolean contains(int x, int y) {
		return this.h.doesContain(x, y);
	}

	@Override
	public HexagonAdapter clone() {
		Hexagon he = new Hexagon(this.h.getX(), this.getY(), this.getR());
		HexagonAdapter hexagonClone = new HexagonAdapter(he);
		hexagonClone.setInsideColor(this.getInsideColor());
		hexagonClone.setOutlineColor(this.getOutlineColor());
		return hexagonClone;
	}

	@Override
	public void fill(Graphics g) {
		this.h.paint(g);
	}

	@Override
	public void selected(Graphics g) {
		this.h.paint(g);
	}

	@Override
	public void setOutlineColor(Color outlineColor) {
		this.h.setBorderColor(outlineColor);
	}

	@Override
	public void setInsideColor(Color insideColor) {
		this.h.setAreaColor(insideColor);
	}

	@Override
	public void setSelected(boolean b) {
		this.h.setSelected(b);
		super.notifyObserver();
	}

	@Override
	public Color getOutlineColor() {
		return this.h.getBorderColor();
	}

	@Override
	public Color getInsideColor() {
		return this.h.getAreaColor();
	}

	@Override
	public boolean isSelected() {
		return this.h.isSelected();
	}

	@Override
	public String toString() {
		Point center = new Point(h.getX(), h.getY());
		return "hexagon:center" + center.getCoordinatesText() + ",radius(" + h.getR() + ")," + this.getOutlineText()
				+ "," + this.getInsideText();
	}

	@Override
	public String getOutlineText() {
		return "outline(" + this.h.getBorderColor().getRed() + "." + this.h.getBorderColor().getGreen() + "."
				+ this.h.getBorderColor().getBlue() + ")";
	}

	@Override
	public String getInsideText() {
		return "inside(" + this.h.getAreaColor().getRed() + "." + this.h.getAreaColor().getGreen() + "."
				+ this.h.getAreaColor().getBlue() + ")";
	}

	@Override
	public boolean equals(Object second) {
		if (second instanceof HexagonAdapter) {
			HexagonAdapter secondHexagon = (HexagonAdapter) second;
			if (this.getX() == secondHexagon.getX() && this.getY() == secondHexagon.getY()
					&& this.getR() == secondHexagon.getR())
				return true;
			else {
				return false;
			}
		} else {
			return false;
		}
	}
}
