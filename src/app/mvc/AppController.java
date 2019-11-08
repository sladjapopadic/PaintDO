package app.mvc;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import app.command.CMDAddShape;
import app.command.CMDBringToBack;
import app.command.CMDBringToFront;
import app.command.CMDDeselectAll;
import app.command.CMDEditCircle;
import app.command.CMDEditHexagon;
import app.command.CMDEditLine;
import app.command.CMDEditPoint;
import app.command.CMDEditRectangle;
import app.command.CMDEditSquare;
import app.command.CMDRemoveShape;
import app.command.CMDSelectShape;
import app.command.CMDToBack;
import app.command.CMDToFront;
import app.command.Command;
import app.dialogs.AddCircleDialog;
import app.dialogs.AddHexagonDialog;
import app.dialogs.AddRectangleDialog;
import app.dialogs.AddSquareDialog;
import app.dialogs.EditCircleDialog;
import app.dialogs.EditHexagonDialog;
import app.dialogs.EditLineDialog;
import app.dialogs.EditPointDialog;
import app.dialogs.EditRectangleDialog;
import app.dialogs.EditSquareDialog;
import app.dialogs.OpenLogDialog;
import app.io.SavingDrawing;
import app.io.SavingLog;
import app.io.SavingManager;
import geometry.Circle;
import geometry.HexagonAdapter;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Shape;
import geometry.Square;
import hexagon.Hexagon;

@SuppressWarnings("serial")
public class AppController implements Serializable {

	private AppModel model;
	private AppFrame frame;
	private int currentCmdIndex;
	private boolean isReversed = false;
	private ArrayList<Command> listOfCommands = new ArrayList<Command>();

	public AppController(AppModel model, AppFrame frame) {
		this.model = model;
		this.frame = frame;
	}

	public void select(MouseEvent e) {

		boolean foundShape = false;

		Collections.reverse(model.getAll());
		isReversed = true;
		for (Shape s : model.getAll()) {
			s.setObserver(frame);
			if (s.contains(e.getX(), e.getY())) { 
				foundShape = true;
				executeCommands(new CMDSelectShape(s));
				break;
			}
		}
		Collections.reverse(model.getAll());
		isReversed = false;
		
		if (foundShape == false) {
			boolean atLeastOneSelected=false;
			for(Shape s:model.getAll())
			{
				if(s.isSelected())
				{
					atLeastOneSelected=true;
					break;
				}
			}
			
			if(atLeastOneSelected==true)
				executeCommands(new CMDDeselectAll(model, frame));
		}

	}

	public void draw(MouseEvent e, MouseEvent firstClick, Color outline, Color inside) {

		if (frame.getBtnPoint().isSelected()) {
			Point p = new Point(e.getX(), e.getY(), outline);
			executeCommands(new CMDAddShape(model, p));
		} else if (frame.getBtnLine().isSelected()) {
			Line l = new Line(new Point(firstClick.getX(), firstClick.getY()), new Point(e.getX(), e.getY()), outline);
			executeCommands(new CMDAddShape(model, l));
		} else if (frame.getBtnSquare().isSelected()) {
			AddSquareDialog asd = new AddSquareDialog();
			asd.setVisible(true);
			int side = asd.getSide();
			if (side > 0 && side <= 400) {
				Square s = new Square(new Point(e.getX(), e.getY()), side, outline, inside);
				executeCommands(new CMDAddShape(model, s));
			}
		} else if (frame.getBtnRectangle().isSelected()) {
			AddRectangleDialog ard = new AddRectangleDialog();
			ard.setVisible(true);
			int height = ard.getHeightR();
			int width = ard.getWidthR();

			if (height > 0 && height <= 400 && width > 0 && width <= 400) {
				Rectangle r = new Rectangle(new Point(e.getX(), e.getY()), height, width, outline, inside);
				executeCommands(new CMDAddShape(model, r));
			}
		} else if (frame.getBtnCircle().isSelected()) {
			AddCircleDialog acd = new AddCircleDialog();
			acd.setVisible(true);
			int radius = acd.getRadius();

			if (radius > 0 && radius <= 400) {
				Circle c = new Circle(new Point(e.getX(), e.getY()), radius, outline, inside);
				executeCommands(new CMDAddShape(model, c));
			}
		} else if (frame.getBtnHexagon().isSelected()) {
			AddHexagonDialog ahd = new AddHexagonDialog();
			ahd.setVisible(true);
			int radius = ahd.getRadius();

			if (radius > 0 && radius <= 400) {
				Hexagon h = new Hexagon(e.getX(), e.getY(), radius);
				HexagonAdapter ha = new HexagonAdapter(h);
				ha.setOutlineColor(outline);
				ha.setInsideColor(inside);
				executeCommands(new CMDAddShape(model, ha));
			}
		}
		frame.update();
	}

	public void edit() {
		for (Shape s : model.getAll()) {
			if (s.isSelected())
				if (s instanceof Point) {
					EditPointDialog epd = new EditPointDialog((Point) s);
					epd.setVisible(true);
					if (epd.getX() > 0 && epd.getX() < 891 && epd.getY() > 0 && epd.getY() < 376) {
						executeCommands(
								new CMDEditPoint((Point) s, new Point(epd.getX(), epd.getY(), epd.getOutline())));
					}
				} else if (s instanceof Line) {
					EditLineDialog eld = new EditLineDialog((Line) s);
					eld.setVisible(true);
					if (eld.getStartX() > 0 && eld.getStartX() < 891 && eld.getStartY() > 0 && eld.getStartY() < 376
							&& eld.getEndX() > 0 && eld.getEndX() < 891 && eld.getEndY() > 0 && eld.getEndY() < 376) {
						executeCommands(new CMDEditLine((Line) s, new Line(new Point(eld.getStartX(), eld.getStartY()),
								new Point(eld.getEndX(), eld.getEndY()), eld.getOutline())));
					}
				} else if (s instanceof Rectangle) {
					EditRectangleDialog erd = new EditRectangleDialog((Rectangle) s);
					erd.setVisible(true);
					if (erd.getStartX() > 0 && erd.getStartX() < 891 && erd.getStartY() > 0 && erd.getStartY() < 376
							&& erd.getHeightR() > 0 && erd.getHeightR() < 400 && erd.getWidthR() > 0
							&& erd.getWidthR() < 400) {
						executeCommands(new CMDEditRectangle((Rectangle) s,
								new Rectangle(new Point(erd.getStartX(), erd.getStartY()), erd.getHeightR(),
										erd.getWidthR(), erd.getOutline(), erd.getInside())));
					}
				} else if (s instanceof Square) {
					EditSquareDialog esd = new EditSquareDialog((Square) s);
					esd.setVisible(true);
					if (esd.getStartX() > 0 && esd.getStartX() < 891 && esd.getStartY() > 0 && esd.getStartY() < 376
							&& esd.getSide() > 0 && esd.getSide() < 400) {
						executeCommands(
								new CMDEditSquare((Square) s, new Square(new Point(esd.getStartX(), esd.getStartY()),
										esd.getSide(), esd.getOutline(), esd.getInside())));
					}
				} else if (s instanceof Circle) {
					EditCircleDialog ecd = new EditCircleDialog((Circle) s);
					ecd.setVisible(true);
					if (ecd.getCenterX() > 0 && ecd.getCenterX() < 891 && ecd.getCenterY() > 0 && ecd.getCenterY() < 376
							&& ecd.getRadius() > 0 && ecd.getRadius() < 400) {
						executeCommands(
								new CMDEditCircle((Circle) s, new Circle(new Point(ecd.getCenterX(), ecd.getCenterY()),
										ecd.getRadius(), ecd.getOutline(), ecd.getInside())));
					}

				} else if (s instanceof HexagonAdapter) {
					EditHexagonDialog ehd = new EditHexagonDialog((HexagonAdapter) s);
					ehd.setVisible(true);
					if (ehd.getCenterX() > 0 && ehd.getCenterX() < 891 && ehd.getCenterY() > 0 && ehd.getCenterY() < 376
							&& ehd.getRadius() > 0 && ehd.getRadius() < 400) {
						Hexagon he = new Hexagon(ehd.getCenterX(), ehd.getCenterY(), ehd.getRadius());
						HexagonAdapter ha = new HexagonAdapter(he);
						ha.setOutlineColor(ehd.getOutline());
						ha.setInsideColor(ehd.getInside());
						executeCommands(new CMDEditHexagon((HexagonAdapter) s, ha));
					}
				}

		}
	}

	public void delete() {

		ArrayList<Shape> selectedShapes = new ArrayList<Shape>();
		for (Shape s : model.getAll()) {
			if (s.isSelected())
				selectedShapes.add(s);
		}
		executeCommands(new CMDRemoveShape(model, selectedShapes));
		frame.update();
	}

	public void executeCommands(Command c) {

		listOfCommands.add(c);
		currentCmdIndex = listOfCommands.size() - 1;
		c.execute();
		frame.addToLog(c.toString());

		frame.getBtnUndo().setEnabled(true);
		frame.getBtnRedo().setEnabled(false);
	}

	public void undo() {
		frame.getBtnRedo().setEnabled(true);
		listOfCommands.get(currentCmdIndex).unexecute(); 
		frame.addToLog("undo:" + listOfCommands.get(currentCmdIndex).toString());
		currentCmdIndex--; 
		if (currentCmdIndex < 0) { 
			frame.getBtnUndo().setEnabled(false);
		}
	}

	public void redo() {
		currentCmdIndex++; 
		listOfCommands.get(currentCmdIndex).execute();
		frame.addToLog("redo:" + listOfCommands.get(currentCmdIndex).toString());
		frame.getBtnUndo().setEnabled(true);
		if (currentCmdIndex == listOfCommands.size() - 1) {
			frame.getBtnRedo().setEnabled(false);
		}
	}

	public void toBack() {
		for (Shape s : model.getAll()) {
			if (s.isSelected()) {
				executeCommands(new CMDToBack(s, model));
				break;
			}
		}
	}

	public void toFront() {
		for (Shape s : model.getAll()) {
			if (s.isSelected()) {
				executeCommands(new CMDToFront(s, model));
				break;
			}
		}
	}

	public void bringToBack() {
		for (Shape s : model.getAll()) {
			if (s.isSelected()) {
				executeCommands(new CMDBringToBack(s, model));
				break;
			}
		}
	}

	public void bringToFront() {
		for (Shape s : model.getAll()) {
			if (s.isSelected()) {
				executeCommands(new CMDBringToFront(s, model));
				break;
			}
		}
	}

	public boolean isReversed() {
		return isReversed;
	}

	public void save(int option) { 

		if (option == JOptionPane.YES_OPTION) { 
			SavingManager smLog = new SavingManager(new SavingLog(frame.getDlm()));
			smLog.save();
		} else if (option == JOptionPane.NO_OPTION) {
			SavingManager smDrawing = new SavingManager(new SavingDrawing(model.getAll()));
			smDrawing.save();
		}
	}

	public void open() {
		try {

			JFileChooser fileChooser = new JFileChooser("c:\\Users\\sladj\\Desktop");

			if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				File f = (fileChooser.getSelectedFile());

				if (f.getAbsolutePath().endsWith("_log.txt")) {

					model.getAll().clear();
					listOfCommands.clear();
					frame.reset(); 

					FileReader fr = new FileReader(f);
					BufferedReader bf = new BufferedReader(fr);

					OpenLogDialog old = new OpenLogDialog(AppController.this, bf);
					old.setVisible(true);

				} else {
					FileInputStream fis = new FileInputStream(f);
					ObjectInputStream ois = new ObjectInputStream(fis);
					model.getAll().clear();
					listOfCommands.clear();
					frame.reset();
					@SuppressWarnings("unchecked")
					ArrayList<Shape> inputList = (ArrayList<Shape>) ois.readObject();

					for (Shape s : inputList) {
						model.add(s);
					}

					frame.update();

					ois.close();
					fis.close();
				}
			}

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Error while opening file", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void loadNext(String line) {

		String[] array = line.split(":");

			if (array[0].equals("add")) {

				String valuesLine = array[2].replaceAll("[^0-9,.]", "");
				String[] values = valuesLine.split(",");
				if (array[1].equals("point")) {

					Color outline = parseColor(values[2].split("\\."));
					Point p = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]), outline);

					executeCommands(new CMDAddShape(model, p));
				} else if (array[1].equals("line")) {

					Point start = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
					Point end = new Point(Integer.parseInt(values[2]), Integer.parseInt(values[3]));
					Color outline = parseColor(values[4].split("\\."));

					executeCommands(new CMDAddShape(model, new Line(start, end, outline)));
				} else if (array[1].equals("square")) {

					Point start = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
					int side = Integer.parseInt(values[2]);
					Color outline = parseColor(values[3].split("\\."));
					Color inside = parseColor(values[4].split("\\."));

					executeCommands(new CMDAddShape(model, new Square(start, side, outline, inside)));
				} else if (array[1].equals("rectangle")) {

					Point start = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
					int width = Integer.parseInt(values[2]);
					int height = Integer.parseInt(values[3]);
					Color outline = parseColor(values[4].split("\\."));
					Color inside = parseColor(values[5].split("\\."));

					executeCommands(new CMDAddShape(model, new Rectangle(start, height, width, outline, inside)));
				} else if (array[1].equals("circle")) {

					Point center = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
					int radius = Integer.parseInt(values[2]);
					Color outline = parseColor(values[3].split("\\."));
					Color inside = parseColor(values[4].split("\\."));

					executeCommands(new CMDAddShape(model, new Circle(center, radius, outline, inside)));
				} else if (array[1].equals("hexagon")) {

					int radius = Integer.parseInt(values[2]);
					Color outline = parseColor(values[3].split("\\."));
					Color inside = parseColor(values[4].split("\\."));

					Hexagon h = new Hexagon(Integer.parseInt(values[0]), Integer.parseInt(values[1]), radius);
					HexagonAdapter ha = new HexagonAdapter(h);
					ha.setOutlineColor(outline);
					ha.setInsideColor(inside);
					executeCommands(new CMDAddShape(model, ha));
				}
			} else if (array[0].equals("edit")) {

				String valuesLine = array[2].replaceAll("[^0-9,.]", "");
				String[] values = valuesLine.split(",");
				String newValuesLine = array[4].replaceAll("[^0-9,.]", "");
				String[] newValues = newValuesLine.split(",");

				if (array[1].equals("point")) {

					Point oldState = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]),
							parseColor(values[2].split("\\.")));
					Point newState = new Point(Integer.parseInt(newValues[0]), Integer.parseInt(newValues[1]),
							parseColor(newValues[2].split("\\.")));

					for (Shape s : model.getAll()) {
						if (s.equals(oldState)) {
							executeCommands(new CMDEditPoint((Point) s, newState));
							break;
						}
					}
				} else if (array[1].equals("line")) {

					Line oldState = new Line(new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1])),
							new Point(Integer.parseInt(values[2]), Integer.parseInt(values[3])),
							parseColor(values[4].split("\\.")));
					Line newState = new Line(new Point(Integer.parseInt(newValues[0]), Integer.parseInt(newValues[1])),
							new Point(Integer.parseInt(newValues[2]), Integer.parseInt(newValues[3])),
							parseColor(newValues[4].split("\\.")));

					for (Shape s : model.getAll()) {
						if (s.equals(oldState)) {
							executeCommands(new CMDEditLine((Line) s, newState));
							break;
						}
					}
				} else if (array[1].equals("square")) {

					Square oldState = new Square(new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1])),
							Integer.parseInt(values[2]), parseColor(values[3].split("\\.")),
							parseColor(values[4].split("\\.")));
					Square newState = new Square(
							new Point(Integer.parseInt(newValues[0]), Integer.parseInt(newValues[1])),
							Integer.parseInt(newValues[2]), parseColor(newValues[3].split("\\.")),
							parseColor(newValues[4].split("\\.")));

					for (Shape s : model.getAll()) {
						if (s.equals(oldState)) {
							executeCommands(new CMDEditSquare((Square) s, newState));
							break;
						}
					}
				} else if (array[1].equals("rectangle")) {

					Rectangle oldState = new Rectangle(
							new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1])),
							Integer.parseInt(values[3]), Integer.parseInt(values[2]),
							parseColor(values[4].split("\\.")), parseColor(values[5].split("\\.")));
					Rectangle newState = new Rectangle(
							new Point(Integer.parseInt(newValues[0]), Integer.parseInt(newValues[1])),
							Integer.parseInt(newValues[3]), Integer.parseInt(newValues[2]),
							parseColor(newValues[4].split("\\.")), parseColor(newValues[5].split("\\.")));

					for (Shape s : model.getAll()) {
						if (s.equals(oldState)) {
							executeCommands(new CMDEditRectangle((Rectangle) s, newState));
							break;
						}
					}
				} else if (array[1].equals("circle")) {

					Circle oldState = new Circle(new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1])),
							Integer.parseInt(values[2]), parseColor(values[3].split("\\.")),
							parseColor(values[4].split("\\.")));
					Circle newState = new Circle(
							new Point(Integer.parseInt(newValues[0]), Integer.parseInt(newValues[1])),
							Integer.parseInt(newValues[2]), parseColor(newValues[3].split("\\.")),
							parseColor(newValues[4].split("\\.")));

					for (Shape s : model.getAll()) {
						if (s.equals(oldState)) {
							executeCommands(new CMDEditCircle((Circle) s, newState));
							break;
						}
					}
				} else if (array[1].equals("hexagon")) {

					Hexagon hOldState = new Hexagon(Integer.parseInt(values[0]), Integer.parseInt(values[1]),
							Integer.parseInt(values[2]));
					HexagonAdapter haOldState = new HexagonAdapter(hOldState);
					haOldState.setOutlineColor(parseColor(values[3].split("\\.")));
					haOldState.setInsideColor(parseColor(newValues[4].split("\\.")));

					Hexagon hNewState = new Hexagon(Integer.parseInt(newValues[0]), Integer.parseInt(newValues[1]),
							Integer.parseInt(newValues[2]));
					HexagonAdapter haNewState = new HexagonAdapter(hNewState);
					haNewState.setOutlineColor(parseColor(newValues[3].split("\\.")));
					haNewState.setInsideColor(parseColor(newValues[4].split("\\.")));

					for (Shape s : model.getAll()) {

						if (s.equals(haOldState)) {
							executeCommands(new CMDEditHexagon((HexagonAdapter) s, haNewState));
							break;
						}
					}
				}
			} else if (array[0].equals("remove")) {
				ArrayList<Shape> shapes = new ArrayList<Shape>();

				for (int i = 0; i < array.length; i++) {

					if (array[i].equals("point")) {
						String pValues = array[i + 1].replaceAll("[^0-9,.]", "");
						String[] pointValues = pValues.split(",");

						Color outline = parseColor(pointValues[2].split("\\."));
						Point p = new Point(Integer.parseInt(pointValues[0]), Integer.parseInt(pointValues[1]),
								outline);
						shapes.add(p);
					} else if (array[i].equals("line")) {
						String pValues = array[i + 1].replaceAll("[^0-9,.]", "");
						String[] pointValues = pValues.split(",");

						Point start = new Point(Integer.parseInt(pointValues[0]), Integer.parseInt(pointValues[1]));
						Point end = new Point(Integer.parseInt(pointValues[2]), Integer.parseInt(pointValues[3]));
						Color outline = parseColor(pointValues[4].split("\\."));

						Line l = new Line(start, end, outline);
						shapes.add(l);
					} else if (array[i].equals("square")) {
						String pValues = array[i + 1].replaceAll("[^0-9,.]", "");
						String[] pointValues = pValues.split(",");

						Point start = new Point(Integer.parseInt(pointValues[0]), Integer.parseInt(pointValues[1]));
						int side = Integer.parseInt(pointValues[2]);
						Color outline = parseColor(pointValues[3].split("\\."));
						Color inside = parseColor(pointValues[4].split("\\."));

						Square s = new Square(start, side, outline, inside);
						shapes.add(s);
					} else if (array[i].equals("rectangle")) {
						String pValues = array[i + 1].replaceAll("[^0-9,.]", "");
						String[] pointValues = pValues.split(",");

						Point start = new Point(Integer.parseInt(pointValues[0]), Integer.parseInt(pointValues[1]));
						int width = Integer.parseInt(pointValues[2]);
						int height = Integer.parseInt(pointValues[3]);
						Color outline = parseColor(pointValues[4].split("\\."));
						Color inside = parseColor(pointValues[5].split("\\."));

						Rectangle r = new Rectangle(start, height, width, outline, inside);
						shapes.add(r);
					} else if (array[i].equals("circle")) {
						String pValues = array[i + 1].replaceAll("[^0-9,.]", "");
						String[] pointValues = pValues.split(",");

						Point center = new Point(Integer.parseInt(pointValues[0]), Integer.parseInt(pointValues[1]));
						int radius = Integer.parseInt(pointValues[2]);
						Color outline = parseColor(pointValues[3].split("\\."));
						Color inside = parseColor(pointValues[4].split("\\."));

						Circle c = new Circle(center, radius, outline, inside);
						shapes.add(c);
					} else if (array[i].equals("hexagon")) {
						String pValues = array[i + 1].replaceAll("[^0-9,.]", "");
						String[] pointValues = pValues.split(",");

						int radius = Integer.parseInt(pointValues[2]);
						Color outline = parseColor(pointValues[3].split("\\."));
						Color inside = parseColor(pointValues[4].split("\\."));

						Hexagon h = new Hexagon(Integer.parseInt(pointValues[0]), Integer.parseInt(pointValues[1]),
								radius);
						HexagonAdapter ha = new HexagonAdapter(h);
						ha.setOutlineColor(outline);
						ha.setInsideColor(inside);

						shapes.add(ha);
					}
				}
				executeCommands(new CMDRemoveShape(model, shapes));
			} else if ((array[0].equals("toback")) || (array[0].equals("tofront")) || (array[0].equals("bringtoback"))
					|| (array[0].equals("bringtofront"))) {

				String valuesLine = array[2].replaceAll("[^0-9,.]", "");
				String[] values = valuesLine.split(",");
				
				if (array[1].equals("point")) {

					Color outline = parseColor(values[2].split("\\."));
					Point p = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]), outline);

					for (Shape s : model.getAll()) {
						if (s.equals(p)) {
							if (array[0].equals("toback"))
								executeCommands(new CMDToBack((Point) s, model));
							else if (array[0].equals("tofront"))
								executeCommands(new CMDToFront((Point) s, model));
							else if (array[0].equals("bringtoback"))
								executeCommands(new CMDBringToBack((Point) s, model));
							else if (array[0].equals("bringtofront"))
								executeCommands(new CMDBringToFront((Point) s, model));
							break;
						}
					}
				} else if (array[1].equals("line")) {

					Point start = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
					Point end = new Point(Integer.parseInt(values[2]), Integer.parseInt(values[3]));
					Color outline = parseColor(values[4].split("\\."));

					Line l = new Line(start, end, outline);

					for (Shape s : model.getAll()) {
						if (s.equals(l)) {
							if (array[0].equals("toback"))
								executeCommands(new CMDToBack((Line) s, model));
							else if (array[0].equals("tofront"))
								executeCommands(new CMDToFront((Line) s, model));
							else if (array[0].equals("bringtoback"))
								executeCommands(new CMDBringToBack((Line) s, model));
							else if (array[0].equals("bringtofront"))
								executeCommands(new CMDBringToFront((Line) s, model));
							break;
						}
					}
				} else if (array[1].equals("square")) {

					Point start = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
					int side = Integer.parseInt(values[2]);
					Color outline = parseColor(values[3].split("\\."));
					Color inside = parseColor(values[4].split("\\."));

					Square sq = new Square(start, side, outline, inside);

					for (Shape s : model.getAll()) {
						if (s.equals(sq)) {
							if (array[0].equals("toback"))
								executeCommands(new CMDToBack((Square) s, model));
							else if (array[0].equals("tofront"))
								executeCommands(new CMDToFront((Square) s, model));
							else if (array[0].equals("bringtoback"))
								executeCommands(new CMDBringToBack((Square) s, model));
							else if (array[0].equals("bringtofront"))
								executeCommands(new CMDBringToFront((Square) s, model));
							break;
						}
					}
				} else if (array[1].equals("rectangle")) {

					Point start = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
					int width = Integer.parseInt(values[2]);
					int height = Integer.parseInt(values[3]);
					Color outline = parseColor(values[4].split("\\."));
					Color inside = parseColor(values[5].split("\\."));

					Rectangle r = new Rectangle(start, height, width, outline, inside);

					for (Shape s : model.getAll()) {
						if (s.equals(r)) {
							if (array[0].equals("toback"))
								executeCommands(new CMDToBack((Rectangle) s, model));
							else if (array[0].equals("tofront"))
								executeCommands(new CMDToFront((Rectangle) s, model));
							else if (array[0].equals("bringtoback"))
								executeCommands(new CMDBringToBack((Rectangle) s, model));
							else if (array[0].equals("bringtofront"))
								executeCommands(new CMDBringToFront((Rectangle) s, model));
							break;
						}
					}
				} else if (array[1].equals("circle")) {

					Point center = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
					int radius = Integer.parseInt(values[2]);
					Color outline = parseColor(values[3].split("\\."));
					Color inside = parseColor(values[4].split("\\."));

					Circle c = new Circle(center, radius, outline, inside);

					for (Shape s : model.getAll()) {
						if (s.equals(c)) {
							if (array[0].equals("toback"))
								executeCommands(new CMDToBack((Circle) s, model));
							else if (array[0].equals("tofront"))
								executeCommands(new CMDToFront((Circle) s, model));
							else if (array[0].equals("bringtoback"))
								executeCommands(new CMDBringToBack((Circle) s, model));
							else if (array[0].equals("bringtofront"))
								executeCommands(new CMDBringToFront((Circle) s, model));
							break;
						}
					}
				} else if (array[1].equals("hexagon")) {

					int radius = Integer.parseInt(values[2]);
					Color outline = parseColor(values[3].split("\\."));
					Color inside = parseColor(values[4].split("\\."));

					Hexagon h = new Hexagon(Integer.parseInt(values[0]), Integer.parseInt(values[1]), radius);
					HexagonAdapter ha = new HexagonAdapter(h);
					ha.setOutlineColor(outline);
					ha.setInsideColor(inside);

					for (Shape s : model.getAll()) {
						if (s.equals(ha)) {
							if (array[0].equals("toback"))
								executeCommands(new CMDToBack((HexagonAdapter) s, model));
							else if (array[0].equals("tofront"))
								executeCommands(new CMDToFront((HexagonAdapter) s, model));
							else if (array[0].equals("bringtoback"))
								executeCommands(new CMDBringToBack((HexagonAdapter) s, model));
							else if (array[0].equals("bringtofront"))
								executeCommands(new CMDBringToFront((HexagonAdapter) s, model));
							break;
						}
					}
				}
			} else if ((array[0].equals("undo")) || (array[0].equals("redo"))) {
				String logCommand = line.substring(5);

				for (Command c : listOfCommands) {
					if (c.toString().equals(logCommand)) {
						if (array[0].equals("undo"))
							this.undo();
						else if (array[0].equals("redo"))
							this.redo();
					}
				}
			} else if (array[0].equals("select")) {

				String valuesLine = array[2].replaceAll("[^0-9,.]", "");
				String[] values = valuesLine.split(",");
				if (array[1].equals("point")) {

					Color outline = parseColor(values[2].split("\\."));
					Point p = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]), outline);

					for (Shape s : model.getAll()) {
						if (s.equals(p)) {
							s.setObserver(frame);
							executeCommands(new CMDSelectShape(s));
						}
					}
				} else if (array[1].equals("line")) {

					Point start = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
					Point end = new Point(Integer.parseInt(values[2]), Integer.parseInt(values[3]));
					Color outline = parseColor(values[4].split("\\."));

					Line l = new Line(start, end, outline);

					for (Shape s : model.getAll()) {
						if (s.equals(l)) {
							s.setObserver(frame);
							executeCommands(new CMDSelectShape(s));
						}
					}
				} else if (array[1].equals("square")) {

					Point start = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
					int side = Integer.parseInt(values[2]);
					Color outline = parseColor(values[3].split("\\."));
					Color inside = parseColor(values[4].split("\\."));

					Square sq = new Square(start, side, outline, inside);

					for (Shape s : model.getAll()) {
						if (s.equals(sq)) {
							s.setObserver(frame);
							executeCommands(new CMDSelectShape(s));
						}
					}
				} else if (array[1].equals("rectangle")) {

					Point start = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
					int width = Integer.parseInt(values[2]);
					int height = Integer.parseInt(values[3]);
					Color outline = parseColor(values[4].split("\\."));
					Color inside = parseColor(values[5].split("\\."));

					Rectangle r = new Rectangle(start, height, width, outline, inside);

					for (Shape s : model.getAll()) {
						if (s.equals(r)) {
							s.setObserver(frame);
							executeCommands(new CMDSelectShape(s));
						}
					}
				} else if (array[1].equals("circle")) {

					Point center = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
					int radius = Integer.parseInt(values[2]);
					Color outline = parseColor(values[3].split("\\."));
					Color inside = parseColor(values[4].split("\\."));

					Circle c = new Circle(center, radius, outline, inside);

					for (Shape s : model.getAll()) {
						if (s.equals(c)) {
							s.setObserver(frame);
							executeCommands(new CMDSelectShape(s));
						}
					}
				} else if (array[1].equals("hexagon")) {

					int radius = Integer.parseInt(values[2]);
					Color outline = parseColor(values[3].split("\\."));
					Color inside = parseColor(values[4].split("\\."));

					Hexagon h = new Hexagon(Integer.parseInt(values[0]), Integer.parseInt(values[1]), radius);
					HexagonAdapter ha = new HexagonAdapter(h);
					ha.setOutlineColor(outline);
					ha.setInsideColor(inside);

					for (Shape s : model.getAll()) {
						if (s.equals(ha)) {
							s.setObserver(frame);
							executeCommands(new CMDSelectShape(s));
						}
					}
				}
			}  else if (array[0].equals("deselectAll")){
			executeCommands(new CMDDeselectAll(model, frame));
		}
	}

	public Color parseColor(String[] rgb) {
		Color color = new Color(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2]));
		return color;
	}

}
