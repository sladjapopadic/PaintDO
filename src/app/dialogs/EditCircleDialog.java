package app.dialogs;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JDialog;

import app.mvc.AppFrame;
import geometry.Circle;

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

public class EditCircleDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextField tfCenterX;
	private JTextField tfCenterY;
	private JTextField tfRadius;
	private int centerX;
	private int centerY;
	private int radius;
	private Color outline;
	private Color inside;

	public EditCircleDialog(Circle c) {

		setSize(187, 186);
		setLocationRelativeTo(null);
		setModal(true);
		setResizable(false);
		setTitle("Edit Circle");
		Image img = new ImageIcon(AppFrame.class.getResource("/icons/circle.png")).getImage();
		this.setIconImage(img);

		outline = c.getOutlineColor();
		inside = c.getInsideColor();

		JPanel jpMainPanel = new JPanel();
		getContentPane().add(jpMainPanel, BorderLayout.CENTER);
		GridBagLayout gbl_jpMainPanel = new GridBagLayout();
		gbl_jpMainPanel.columnWidths = new int[] { 0, 0, 0 };
		gbl_jpMainPanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gbl_jpMainPanel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_jpMainPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		jpMainPanel.setLayout(gbl_jpMainPanel);

		JLabel lblCenterX = new JLabel("X (Center):");
		GridBagConstraints gbc_lblCenterX = new GridBagConstraints();
		gbc_lblCenterX.anchor = GridBagConstraints.EAST;
		gbc_lblCenterX.insets = new Insets(0, 0, 5, 5);
		gbc_lblCenterX.gridx = 0;
		gbc_lblCenterX.gridy = 0;
		jpMainPanel.add(lblCenterX, gbc_lblCenterX);

		tfCenterX = new JTextField();
		tfCenterX.setText(String.valueOf(c.getCenter().getX()));
		GridBagConstraints gbc_tfCenterX = new GridBagConstraints();
		gbc_tfCenterX.insets = new Insets(0, 0, 5, 0);
		gbc_tfCenterX.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfCenterX.gridx = 1;
		gbc_tfCenterX.gridy = 0;
		jpMainPanel.add(tfCenterX, gbc_tfCenterX);
		tfCenterX.setColumns(10);

		JLabel lblCenterY = new JLabel("Y (Center):");
		GridBagConstraints gbc_lblCenterY = new GridBagConstraints();
		gbc_lblCenterY.anchor = GridBagConstraints.EAST;
		gbc_lblCenterY.insets = new Insets(0, 0, 5, 5);
		gbc_lblCenterY.gridx = 0;
		gbc_lblCenterY.gridy = 1;
		jpMainPanel.add(lblCenterY, gbc_lblCenterY);

		tfCenterY = new JTextField();
		tfCenterY.setText(String.valueOf(c.getCenter().getY()));
		GridBagConstraints gbc_tfCenterY = new GridBagConstraints();
		gbc_tfCenterY.insets = new Insets(0, 0, 5, 0);
		gbc_tfCenterY.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfCenterY.gridx = 1;
		gbc_tfCenterY.gridy = 1;
		jpMainPanel.add(tfCenterY, gbc_tfCenterY);
		tfCenterY.setColumns(10);

		JLabel lblRadius = new JLabel("Radius:");
		GridBagConstraints gbc_lblRadius = new GridBagConstraints();
		gbc_lblRadius.anchor = GridBagConstraints.EAST;
		gbc_lblRadius.insets = new Insets(0, 0, 5, 5);
		gbc_lblRadius.gridx = 0;
		gbc_lblRadius.gridy = 2;
		jpMainPanel.add(lblRadius, gbc_lblRadius);

		tfRadius = new JTextField();
		tfRadius.setText(String.valueOf(c.getRadius()));
		GridBagConstraints gbc_tfRadius = new GridBagConstraints();
		gbc_tfRadius.insets = new Insets(0, 0, 5, 0);
		gbc_tfRadius.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfRadius.gridx = 1;
		gbc_tfRadius.gridy = 2;
		jpMainPanel.add(tfRadius, gbc_tfRadius);
		tfRadius.setColumns(10);

		JLabel lblOutlineColor = new JLabel("Outline color:");
		GridBagConstraints gbc_lblOutlineColor = new GridBagConstraints();
		gbc_lblOutlineColor.insets = new Insets(0, 0, 5, 5);
		gbc_lblOutlineColor.gridx = 0;
		gbc_lblOutlineColor.gridy = 3;
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
		gbc_btnOutlineColor.gridy = 3;
		jpMainPanel.add(btnOutlineColor, gbc_btnOutlineColor);

		JLabel lblInsideColor = new JLabel("Inside color:");
		GridBagConstraints gbc_lblInsideColor = new GridBagConstraints();
		gbc_lblInsideColor.insets = new Insets(0, 0, 5, 5);
		gbc_lblInsideColor.gridx = 0;
		gbc_lblInsideColor.gridy = 4;
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
		gbc_btnInsideColor.gridy = 4;
		jpMainPanel.add(btnInsideColor, gbc_btnInsideColor);

		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {

					centerX = Integer.parseInt(tfCenterX.getText());
					centerY = Integer.parseInt(tfCenterY.getText());
					radius = Integer.parseInt(tfRadius.getText());

					if (centerX < 1 || centerX > 890 || centerY < 1 || centerY > 375 || radius < 1 || radius > 400) {
						throw new NumberFormatException();
					}

					dispose();

				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(EditCircleDialog.this,
							"Enter numbers 1-890 for X, 1-375 for Y and 1-400 for radius", "Warrning",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		GridBagConstraints gbc_btnSubmit = new GridBagConstraints();
		gbc_btnSubmit.gridwidth = 2;
		gbc_btnSubmit.gridx = 0;
		gbc_btnSubmit.gridy = 5;
		jpMainPanel.add(btnSubmit, gbc_btnSubmit);
	}

	public int getCenterX() {
		return centerX;
	}

	public int getCenterY() {
		return centerY;
	}

	public int getRadius() {
		return radius;
	}

	public Color getOutline() {
		return outline;
	}

	public Color getInside() {
		return inside;
	}

}
