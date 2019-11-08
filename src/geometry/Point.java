package geometry;

import java.awt.Color;
import java.awt.Graphics;

public class Point extends Shape {

	private static final long serialVersionUID = 1L;
	private int x, y;

	public Point() {

	}

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Point(int x, int y, Color outlineColor) {
		this.x = x;
		this.y = y;
		super.setOutlineColor(outlineColor);
	}

	public double distance(Point second) {
		return Math.sqrt((second.getX() - this.getX()) * (second.getX() - this.getX())
				+ (second.getY() - this.getY()) * (second.getY() - this.getY()));
	}

	@Override
	public int compareTo(Object second) {
		if (second instanceof Point) {
			Point secondPoint = (Point) second;
			return (int) (this.distance(new Point(0, 0)) - secondPoint.distance(new Point(0, 0)));
		} else {
			return 0;
		}
	}

	@Override
	public boolean contains(int x, int y) {
		Point click = new Point(x, y);
		if (this.distance(click) <= 3) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void selected(Graphics g) {
		g.setColor(Color.BLUE);
		g.drawRect(x - 3, y - 3, 6, 6);
	}

	public boolean equals(Object second) {
		if (second instanceof Point) {
			Point secondPoint = (Point) second;
			if (this.getX() == secondPoint.getX() && this.getY() == secondPoint.getY()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(this.getOutlineColor());
		g.drawLine(x - 1, y - 1, x + 1, y + 1);
		g.drawLine(x - 1, y + 1, x + 1, y - 1);
		if (this.isSelected()) {
			this.selected(g);
		}
	}

	@Override
	public String toString() {
		return "point:" + getCoordinatesText() + "," + getOutlineText();
	}

	@Override
	public Point clone() {
		Point pointClone = new Point();
		pointClone.setX(this.getX());
		pointClone.setY(this.getY());
		pointClone.setOutlineColor(this.getOutlineColor());
		return pointClone;
	}

	public String getCoordinatesText() {
		return "(" + x + "," + y + ")";
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}