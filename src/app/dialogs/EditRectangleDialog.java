package app.dialogs;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JDialog;

import app.mvc.AppFrame;
import geometry.Rectangle;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JColorChooser;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EditRectangleDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextField tfStartX;
	private JTextField tfStartY;
	private JTextField tfHeight;
	private JTextField tfWidth;
	private int startX;
	private int startY;
	private int heightR;
	private int widthR;
	private Color outline;
	private Color inside;

	public EditRectangleDialog(Rectangle r) {

		setSize(193, 219);
		setLocationRelativeTo(null);
		setModal(true);
		setResizable(false);
		setTitle("Edit Rectangle");
		Image img = new ImageIcon(AppFrame.class.getResource("/icons/rectangle.png")).getImage();
		this.setIconImage(img);

		outline = r.getOutlineColor();
		inside = r.getInsideColor();

		JPanel jpMainPanel = new JPanel();
		getContentPane().add(jpMainPanel, BorderLayout.CENTER);
		GridBagLayout gbl_jpMainPanel = new GridBagLayout();
		gbl_jpMainPanel.columnWidths = new int[] { 0, 0, 0 };
		gbl_jpMainPanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_jpMainPanel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_jpMainPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		jpMainPanel.setLayout(gbl_jpMainPanel);

		JLabel lblXstartPoint = new JLabel("X (Start point):");
		GridBagConstraints gbc_lblXstartPoint = new GridBagConstraints();
		gbc_lblXstartPoint.anchor = GridBagConstraints.EAST;
		gbc_lblXstartPoint.insets = new Insets(0, 0, 5, 5);
		gbc_lblXstartPoint.gridx = 0;
		gbc_lblXstartPoint.gridy = 0;
		jpMainPanel.add(lblXstartPoint, gbc_lblXstartPoint);

		tfStartX = new JTextField();
		tfStartX.setText(String.valueOf(r.getUpperLeft().getX()));
		GridBagConstraints gbc_tfStartX = new GridBagConstraints();
		gbc_tfStartX.insets = new Insets(0, 0, 5, 0);
		gbc_tfStartX.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfStartX.gridx = 1;
		gbc_tfStartX.gridy = 0;
		jpMainPanel.add(tfStartX, gbc_tfStartX);
		tfStartX.setColumns(10);

		JLabel lblYstartPoint = new JLabel("Y (Start point):");
		GridBagConstraints gbc_lblYstartPoint = new GridBagConstraints();
		gbc_lblYstartPoint.anchor = GridBagConstraints.EAST;
		gbc_lblYstartPoint.insets = new Insets(0, 0, 5, 5);
		gbc_lblYstartPoint.gridx = 0;
		gbc_lblYstartPoint.gridy = 1;
		jpMainPanel.add(lblYstartPoint, gbc_lblYstartPoint);

		tfStartY = new JTextField();
		tfStartY.setText(String.valueOf(r.getUpperLeft().getY()));
		GridBagConstraints gbc_tfStartY = new GridBagConstraints();
		gbc_tfStartY.insets = new Insets(0, 0, 5, 0);
		gbc_tfStartY.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfStartY.gridx = 1;
		gbc_tfStartY.gridy = 1;
		jpMainPanel.add(tfStartY, gbc_tfStartY);
		tfStartY.setColumns(10);

		JLabel lblHeight = new JLabel("Height:");
		GridBagConstraints gbc_lblHeight = new GridBagConstraints();
		gbc_lblHeight.anchor = GridBagConstraints.EAST;
		gbc_lblHeight.insets = new Insets(0, 0, 5, 5);
		gbc_lblHeight.gridx = 0;
		gbc_lblHeight.gridy = 2;
		jpMainPanel.add(lblHeight, gbc_lblHeight);

		tfHeight = new JTextField();
		tfHeight.setText(String.valueOf(r.getHeight()));
		GridBagConstraints gbc_tfHeight = new GridBagConstraints();
		gbc_tfHeight.insets = new Insets(0, 0, 5, 0);
		gbc_tfHeight.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfHeight.gridx = 1;
		gbc_tfHeight.gridy = 2;
		jpMainPanel.add(tfHeight, gbc_tfHeight);
		tfHeight.setColumns(10);

		JLabel lblWidth = new JLabel("Width:");
		GridBagConstraints gbc_lblWidth = new GridBagConstraints();
		gbc_lblWidth.anchor = GridBagConstraints.EAST;
		gbc_lblWidth.insets = new Insets(0, 0, 5, 5);
		gbc_lblWidth.gridx = 0;
		gbc_lblWidth.gridy = 3;
		jpMainPanel.add(lblWidth, gbc_lblWidth);

		tfWidth = new JTextField();
		tfWidth.setText(String.valueOf(r.getSide()));
		GridBagConstraints gbc_tfWidth = new GridBagConstraints();
		gbc_tfWidth.insets = new Insets(0, 0, 5, 0);
		gbc_tfWidth.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfWidth.gridx = 1;
		gbc_tfWidth.gridy = 3;
		jpMainPanel.add(tfWidth, gbc_tfWidth);
		tfWidth.setColumns(10);

		JLabel lblOutlineColor = new JLabel("Outline color:");
		GridBagConstraints gbc_lblOutlineColor = new GridBagConstraints();
		gbc_lblOutlineColor.insets = new Insets(0, 0, 5, 5);
		gbc_lblOutlineColor.gridx = 0;
		gbc_lblOutlineColor.gridy = 4;
		jpMainPanel.add(lblOutlineColor, gbc_lblOutlineColor);

		JButton btnOutlineColor = new JButton("");
		btnOutlineColor.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("static-access")
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Color previousOutline = outline;
				JColorChooser jcc = new JColorChooser();
				outline = jcc.showDialog(null, "Choose outline color", outline);

				if (outline == null) {
					outline = previousOutline;
				}

				btnOutlineColor.setBackground(outline);
			}
		});
		btnOutlineColor.setBackground(outline);
		btnOutlineColor.setPreferredSize(new Dimension(40, 20));
		GridBagConstraints gbc_btnOutlineColor = new GridBagConstraints();
		gbc_btnOutlineColor.insets = new Insets(0, 0, 5, 0);
		gbc_btnOutlineColor.gridx = 1;
		gbc_btnOutlineColor.gridy = 4;
		jpMainPanel.add(btnOutlineColor, gbc_btnOutlineColor);

		JLabel lblInsideColor = new JLabel("Inside color:");
		GridBagConstraints gbc_lblInsideColor = new GridBagConstraints();
		gbc_lblInsideColor.insets = new Insets(0, 0, 5, 5);
		gbc_lblInsideColor.gridx = 0;
		gbc_lblInsideColor.gridy = 5;
		jpMainPanel.add(lblInsideColor, gbc_lblInsideColor);

		JButton btnInsideColor = new JButton("");
		btnInsideColor.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("static-access")
			@Override
			public void mouseClicked(MouseEvent e) {
				Color previousInside = inside;
				JColorChooser jcc = new JColorChooser();
				inside = jcc.showDialog(null, "Choose inside color", inside);

				if (inside == null) {
					inside = previousInside;
				}

				btnInsideColor.setBackground(inside);
			}
		});
		btnInsideColor.setBackground(inside);
		btnInsideColor.setPreferredSize(new Dimension(40, 20));
		GridBagConstraints gbc_btnInsideColor = new GridBagConstraints();
		gbc_btnInsideColor.insets = new Insets(0, 0, 5, 0);
		gbc_btnInsideColor.gridx = 1;
		gbc_btnInsideColor.gridy = 5;
		jpMainPanel.add(btnInsideColor, gbc_btnInsideColor);

		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					startX = Integer.parseInt(tfStartX.getText());
					startY = Integer.parseInt(tfStartY.getText());
					heightR = Integer.parseInt(tfHeight.getText());
					widthR = Integer.parseInt(tfWidth.getText());

					if (startX < 1 || startX > 890 || startY < 1 || startY > 375 || heightR < 1 || heightR > 400
							|| widthR < 1 || widthR > 400) {
						throw new NumberFormatException();
					}

					dispose();

				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(EditRectangleDialog.this,
							"Enter numbers 1-890 for X, 1-375 for Y and 1-400 for height and width", "Warrning",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		GridBagConstraints gbc_btnSubmit = new GridBagConstraints();
		gbc_btnSubmit.gridwidth = 2;
		gbc_btnSubmit.gridx = 0;
		gbc_btnSubmit.gridy = 6;
		jpMainPanel.add(btnSubmit, gbc_btnSubmit);

	}

	public int getStartX() {
		return startX;
	}

	public int getStartY() {
		return startY;
	}

	public int getHeightR() {
		return heightR;
	}

	public int getWidthR() {
		return widthR;
	}

	public Color getOutline() {
		return outline;
	}

	public Color getInside() {
		return inside;
	}
	
}
