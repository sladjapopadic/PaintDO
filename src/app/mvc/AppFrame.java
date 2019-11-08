package app.mvc;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.FlowLayout;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.border.TitledBorder;

import app.observer.Observer;
import geometry.Shape;

import javax.swing.UIManager;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;

public class AppFrame extends JFrame implements Observer {

	private static final long serialVersionUID = 1L;
	private AppController controller;
	private AppView view = new AppView();
	private DefaultListModel<String> dlm = new DefaultListModel<String>(); 
	private Color outline = Color.BLACK;
	private Color inside = Color.WHITE;
	private JToggleButton btnPoint;
	private JToggleButton btnLine;
	private JToggleButton btnSquare;
	private JToggleButton btnRectangle;
	private JToggleButton btnCircle;
	private JToggleButton btnHexagon;
	private JButton btnUndo;
	private JButton btnRedo;
	private JButton btnEdit;
	private JButton btnDelete;
	private JButton btnToBack;
	private JButton btnBringToBack;
	private JButton btnToFront;
	private JButton btnBringToFront;
	private MouseEvent firstClick;
	private JScrollPane spLog;
	private JButton btnOutline;
	private JButton btnInside;
	private JToggleButton btnSelect;

	public AppFrame() {

		setSize(900, 650);
		Image img = new ImageIcon(AppFrame.class.getResource("/icons/paint.png")).getImage();
		this.setIconImage(img);

		JPanel jpMainPanel = new JPanel();
		getContentPane().add(jpMainPanel, BorderLayout.CENTER);
		jpMainPanel.setLayout(new BorderLayout(0, 0));

		JPanel jpLogPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) jpLogPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		jpMainPanel.add(jpLogPanel, BorderLayout.SOUTH);

		spLog = new JScrollPane();
		spLog.setPreferredSize(new Dimension(700, 100));
		jpLogPanel.add(spLog);

		JList<String> jlLog = new JList<String>();
		spLog.setViewportView(jlLog);

		jlLog.setModel(dlm);

		JPanel jpButtonsPanel = new JPanel();
		jpMainPanel.add(jpButtonsPanel, BorderLayout.NORTH);
		jpButtonsPanel.setLayout(new BorderLayout(0, 0));

		JPanel jpMenuPanel = new JPanel();
		jpMenuPanel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		jpButtonsPanel.add(jpMenuPanel, BorderLayout.NORTH);
		jpMenuPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		JButton btnSave = new JButton("", new ImageIcon("src/icons/save.png"));
		btnSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Object[] options = { "Save log", "Save drawing" };
				int option = JOptionPane.showOptionDialog(null, "Save as:", "Save", JOptionPane.DEFAULT_OPTION,
						JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
				controller.save(option);
			}
		});
		btnSave.setToolTipText("Save file");
		btnSave.setPreferredSize(new Dimension(27, 27));
		jpMenuPanel.add(btnSave);

		JButton btnOpen = new JButton("", new ImageIcon("src/icons/open.png"));
		btnOpen.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				controller.open();
			}
		});
		btnOpen.setToolTipText("Open file");
		btnOpen.setPreferredSize(new Dimension(27, 27));
		jpMenuPanel.add(btnOpen);

		btnUndo = new JButton("", new ImageIcon("src/icons/undo.png"));
		btnUndo.setToolTipText("Undo");
		btnUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnUndo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (btnUndo.isEnabled()) {
					controller.undo();
					update();
				}
			}
		});
		btnUndo.setPreferredSize(new Dimension(27, 27));
		btnUndo.setEnabled(false);
		jpMenuPanel.add(btnUndo);

		btnRedo = new JButton("", new ImageIcon("src/icons/redo.png"));
		btnRedo.setToolTipText("Redo");
		btnRedo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnRedo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (btnRedo.isEnabled()) {
					controller.redo();
					update();
				}
			}
		});
		btnRedo.setPreferredSize(new Dimension(27, 27));
		btnRedo.setEnabled(false);
		jpMenuPanel.add(btnRedo);

		JPanel jpToolsPanel = new JPanel();
		jpToolsPanel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		jpButtonsPanel.add(jpToolsPanel, BorderLayout.SOUTH);
		jpToolsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JPanel jpActionsPanel = new JPanel();
		jpActionsPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Actions",
				TitledBorder.CENTER, TitledBorder.BOTTOM, null, new Color(0, 0, 0)));
		jpToolsPanel.add(jpActionsPanel);

		btnSelect = new JToggleButton("", new ImageIcon("src/icons/select.png"));
		btnSelect.setToolTipText("Select");
		btnSelect.setPreferredSize(new Dimension(34, 34));
		btnSelect.setSelected(true);
		jpActionsPanel.add(btnSelect);

		btnEdit = new JButton("", new ImageIcon("src/icons/edit.png"));
		btnEdit.setToolTipText("Edit");
		btnEdit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.edit();
			}
		});
		btnEdit.setPreferredSize(new Dimension(34, 34));
		btnEdit.setEnabled(false);
		jpActionsPanel.add(btnEdit);

		btnDelete = new JButton("", new ImageIcon("src/icons/delete.png"));
		btnDelete.setToolTipText("Delete");
		btnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (btnDelete.isEnabled()) {
					int option = JOptionPane.showConfirmDialog(AppFrame.this,
							"Are you sure you want to delete selected object/s?", "Delete", JOptionPane.YES_NO_OPTION);
					if (option == JOptionPane.YES_OPTION)
						controller.delete();
				}
			}
		});
		btnDelete.setPreferredSize(new Dimension(34, 34));
		btnDelete.setEnabled(false);
		jpActionsPanel.add(btnDelete);

		JPanel jpShapes = new JPanel();
		jpShapes.setBorder(new TitledBorder(null, "Shapes", TitledBorder.CENTER, TitledBorder.BOTTOM, null, null));
		jpToolsPanel.add(jpShapes);
		GridBagLayout gbl_jpShapes = new GridBagLayout();
		gbl_jpShapes.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_jpShapes.rowHeights = new int[] { 0, 0, 0 };
		gbl_jpShapes.columnWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_jpShapes.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		jpShapes.setLayout(gbl_jpShapes);

		btnPoint = new JToggleButton("", new ImageIcon("src/icons/point.png"));
		btnPoint.setToolTipText("Point");
		btnPoint.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				firstClick = null;
			}
		});
		btnPoint.setPreferredSize(new Dimension(27, 27));
		GridBagConstraints gbc_btnPoint = new GridBagConstraints();
		gbc_btnPoint.insets = new Insets(0, 0, 5, 5);
		gbc_btnPoint.gridx = 0;
		gbc_btnPoint.gridy = 0;
		jpShapes.add(btnPoint, gbc_btnPoint);

		btnLine = new JToggleButton("", new ImageIcon("src/icons/line.png"));
		btnLine.setToolTipText("Line");
		btnLine.setPreferredSize(new Dimension(27, 27));
		GridBagConstraints gbc_btnLine = new GridBagConstraints();
		gbc_btnLine.insets = new Insets(0, 0, 5, 5);
		gbc_btnLine.gridx = 1;
		gbc_btnLine.gridy = 0;
		jpShapes.add(btnLine, gbc_btnLine);

		btnSquare = new JToggleButton("", new ImageIcon("src/icons/square.png"));
		btnSquare.setToolTipText("Square");
		btnSquare.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				firstClick = null;
			}
		});
		btnSquare.setPreferredSize(new Dimension(27, 27));
		GridBagConstraints gbc_btnSquare = new GridBagConstraints();
		gbc_btnSquare.insets = new Insets(0, 0, 5, 0);
		gbc_btnSquare.gridx = 2;
		gbc_btnSquare.gridy = 0;
		jpShapes.add(btnSquare, gbc_btnSquare);

		btnRectangle = new JToggleButton("", new ImageIcon("src/icons/rectangle.png"));
		btnRectangle.setToolTipText("Rectangle");
		btnRectangle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				firstClick = null;
			}
		});
		btnRectangle.setPreferredSize(new Dimension(27, 27));
		GridBagConstraints gbc_btnRectangle = new GridBagConstraints();
		gbc_btnRectangle.insets = new Insets(0, 0, 0, 5);
		gbc_btnRectangle.gridx = 0;
		gbc_btnRectangle.gridy = 1;
		jpShapes.add(btnRectangle, gbc_btnRectangle);

		btnCircle = new JToggleButton("", new ImageIcon("src/icons/circle.png"));
		btnCircle.setToolTipText("Circle");
		btnCircle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				firstClick = null;
			}
		});
		btnCircle.setPreferredSize(new Dimension(27, 27));
		GridBagConstraints gbc_btnCircle = new GridBagConstraints();
		gbc_btnCircle.insets = new Insets(0, 0, 0, 5);
		gbc_btnCircle.gridx = 1;
		gbc_btnCircle.gridy = 1;
		jpShapes.add(btnCircle, gbc_btnCircle);

		btnHexagon = new JToggleButton("", new ImageIcon("src/icons/hexagon.png"));
		btnHexagon.setToolTipText("Hexagon");
		btnHexagon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				firstClick = null;
			}
		});
		btnHexagon.setPreferredSize(new Dimension(27, 27));
		GridBagConstraints gbc_btnHexagon = new GridBagConstraints();
		gbc_btnHexagon.gridx = 2;
		gbc_btnHexagon.gridy = 1;
		jpShapes.add(btnHexagon, gbc_btnHexagon);

		JPanel jpColors = new JPanel();
		jpColors.setPreferredSize(new Dimension(120, 60));
		jpColors.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Outline   Inside",
				TitledBorder.CENTER, TitledBorder.BOTTOM, null, new Color(0, 0, 0)));
		//FlowLayout flowLayout_1 = (FlowLayout) jpColors.getLayout();
		jpToolsPanel.add(jpColors);

		btnOutline = new JButton("");
		btnOutline.setToolTipText("Outline color");
		btnOutline.addActionListener(new ActionListener() {
			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent arg0) {
				Color previousOutline = outline;
				JColorChooser jcc = new JColorChooser();
				outline = jcc.showDialog(null, "Choose outline color", outline);

				if (outline == null) {
					outline = previousOutline;
				}

				btnOutline.setBackground(outline);
			}
		});
		btnOutline.setBackground(outline);
		btnOutline.setPreferredSize(new Dimension(34, 34));
		jpColors.add(btnOutline);

		btnInside = new JButton("");
		btnInside.setToolTipText("Inside color");
		btnInside.addActionListener(new ActionListener() {
			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent e) {
				Color previousInside = inside;
				JColorChooser jcc = new JColorChooser();
				inside = jcc.showDialog(null, "Choose inside color", inside);

				if (inside == null) {
					inside = previousInside;
				}

				btnInside.setBackground(inside);
			}
		});
		btnInside.setBackground(inside);
		btnInside.setPreferredSize(new Dimension(34, 34));
		jpColors.add(btnInside);

		JPanel jpSendTo = new JPanel();
		jpSendTo.setBorder(new TitledBorder(null, "SendTo", TitledBorder.CENTER, TitledBorder.BOTTOM, null, null));
		jpToolsPanel.add(jpSendTo);
		jpSendTo.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		btnToBack = new JButton("", new ImageIcon("src/icons/toBack.png"));
		btnToBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (btnToBack.isEnabled()) {
					controller.toBack();
					update();
				}
			}
		});
		btnToBack.setToolTipText("To back");
		btnToBack.setPreferredSize(new Dimension(34, 34));
		btnToBack.setEnabled(false);
		jpSendTo.add(btnToBack);

		btnBringToBack = new JButton("", new ImageIcon("src/icons/sendToBack.png"));
		btnBringToBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (btnBringToBack.isEnabled()) {
					controller.bringToBack();
					update();
				}
			}
		});
		btnBringToBack.setToolTipText("Bring to back");
		btnBringToBack.setPreferredSize(new Dimension(34, 34));
		btnBringToBack.setEnabled(false);
		jpSendTo.add(btnBringToBack);

		btnToFront = new JButton("", new ImageIcon("src/icons/toFront.png"));
		btnToFront.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (btnToFront.isEnabled()) {
					controller.toFront();
					update();
				}
			}
		});
		btnToFront.setToolTipText("To front");
		btnToFront.setPreferredSize(new Dimension(34, 34));
		btnToFront.setEnabled(false);
		jpSendTo.add(btnToFront);

		btnBringToFront = new JButton("", new ImageIcon("src/icons/sendToFront.png"));
		btnBringToFront.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (btnBringToFront.isEnabled()) {
					controller.bringToFront();
					update();
				}
			}
		});
		btnBringToFront.setToolTipText("Bring to front");
		btnBringToFront.setPreferredSize(new Dimension(34, 34));
		btnBringToFront.setEnabled(false);
		jpSendTo.add(btnBringToFront);

		view.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (btnSelect.isSelected()) {
					controller.select(e);
				} else if (btnLine.isSelected()) {
					if (firstClick == null)
						firstClick = e;
					else {
						controller.draw(e, firstClick, outline, inside);
						firstClick = null;
					}
				} else {
					controller.draw(e, firstClick, outline, inside);
				}
			}
		});

		view.setBackground(Color.WHITE);

		jpMainPanel.add(view, BorderLayout.CENTER);

		ButtonGroup btnGroup = new ButtonGroup();
		btnGroup.add(btnSelect);
		btnGroup.add(btnPoint);
		btnGroup.add(btnLine);
		btnGroup.add(btnSquare);
		btnGroup.add(btnRectangle);
		btnGroup.add(btnCircle);
		btnGroup.add(btnHexagon);

	}

	public void setController(AppController controller) {
		this.controller = controller;
	}

	public AppView getView() {
		return view;
	}

	public JToggleButton getBtnPoint() {
		return btnPoint;
	}

	public JToggleButton getBtnLine() {
		return btnLine;
	}

	public JToggleButton getBtnSquare() {
		return btnSquare;
	}

	public JToggleButton getBtnRectangle() {
		return btnRectangle;
	}

	public JToggleButton getBtnCircle() {
		return btnCircle;
	}

	public JToggleButton getBtnHexagon() {
		return btnHexagon;
	}

	public JButton getBtnUndo() {
		return btnUndo;
	}

	public JButton getBtnRedo() {
		return btnRedo;
	}

	public JButton getBtnToBack() {
		return btnToBack;
	}

	public JButton getBtnToFront() {
		return btnToFront;
	}

	public JButton getBtnBringToBack() {
		return btnBringToBack;
	}

	public JButton getBtnBringToFront() {
		return btnBringToFront;
	}

	public DefaultListModel<String> getDlm() {
		return this.dlm;
	}

	public void addToLog(String s) {
		this.dlm.addElement(s);
		JScrollBar sb = spLog.getVerticalScrollBar();
		sb.setValue(sb.getMaximum()); 
	}

	@Override
	public void update() {
		int countSelected = 0;
		for (Shape s : view.getModel().getAll()) { 
			if (s.isSelected()) {
				countSelected++;
			}
		}
		if (countSelected < 1) { // ako nije ni jedan onda ne moze nista da se klikne
			btnEdit.setEnabled(false);
			btnDelete.setEnabled(false);
			btnToBack.setEnabled(false);
			btnToFront.setEnabled(false);
			btnBringToBack.setEnabled(false);
			btnBringToFront.setEnabled(false);
		} else if (countSelected == 1) { // ako je tacno jedan moze nesto
			btnEdit.setEnabled(true);
			btnDelete.setEnabled(true);
			btnToBack.setEnabled(true);
			btnToFront.setEnabled(true);
			btnBringToBack.setEnabled(true);
			btnBringToFront.setEnabled(true);

			if (controller.isReversed())
				Collections.reverse(view.getModel().getAll());

			for (Shape s : view.getModel().getAll()) {
				if (s.isSelected()) {
					if (view.getModel().getAll().size() == 1) { // ako je selektovan oblik jedini u listi oblika
						btnToBack.setEnabled(false);
						btnToFront.setEnabled(false);
						btnBringToBack.setEnabled(false);
						btnBringToFront.setEnabled(false);
					} else { // ako selektovan oblik nije jedini u listi oblika
						if (view.getModel().getAll().indexOf(s) == 0) { // ako je selektovan oblik prvi u listi oblika
							btnToBack.setEnabled(false);
							btnBringToBack.setEnabled(false);
						} else if (view.getModel().getAll().indexOf(s) == view.getModel().getAll().size() - 1) {
							// ako je selektovan oblik prvi u listi oblika
							btnToFront.setEnabled(false);
							btnBringToFront.setEnabled(false);
						}
					}
					break;
				}
			}

			if (controller.isReversed())
				Collections.reverse(view.getModel().getAll());
		} else if (countSelected > 1) { //ako je vise od jednog oblika selektovano onda je moguce samo brisanje
			btnEdit.setEnabled(false);
			btnToBack.setEnabled(false);
			btnToFront.setEnabled(false);
			btnBringToBack.setEnabled(false);
			btnBringToFront.setEnabled(false);
			btnDelete.setEnabled(true);
		}
	}

	public void reset() {
		btnEdit.setEnabled(false);
		btnDelete.setEnabled(false);
		btnToBack.setEnabled(false);
		btnToFront.setEnabled(false);
		btnBringToBack.setEnabled(false);
		btnBringToFront.setEnabled(false);
		btnUndo.setEnabled(false);
		btnRedo.setEnabled(false);
		outline = Color.BLACK;
		btnOutline.setBackground(outline);
		inside = Color.WHITE;
		btnInside.setBackground(inside);
		btnSelect.setSelected(true);
		dlm.clear();
		firstClick = null;
	}
}
