package app.dialogs;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JDialog;

import app.mvc.AppFrame;
import geometry.Square;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import javax.swing.SwingConstants;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JColorChooser;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EditSquareDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextField tfStartX;
	private JTextField tfStartY;
	private JTextField tfSide;
	private int startX;
	private int startY;
	private int side;
	private Color outline;
	private Color inside;

	public EditSquareDialog(Square s) {

		setSize(201, 187);
		setLocationRelativeTo(null);
		setModal(true);
		setResizable(false);
		setTitle("Edit Square");
		Image img = new ImageIcon(AppFrame.class.getResource("/icons/square.png")).getImage();
		this.setIconImage(img);

		outline = s.getOutlineColor();
		inside = s.getInsideColor();

		JPanel jpMainPanel = new JPanel();
		getContentPane().add(jpMainPanel, BorderLayout.CENTER);
		GridBagLayout gbl_jpMainPanel = new GridBagLayout();
		gbl_jpMainPanel.columnWidths = new int[] { 0, 0, 0 };
		gbl_jpMainPanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gbl_jpMainPanel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_jpMainPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		jpMainPanel.setLayout(gbl_jpMainPanel);

		JLabel lblStartX = new JLabel("X (Start point):");
		lblStartX.setHorizontalAlignment(SwingConstants.TRAILING);
		GridBagConstraints gbc_lblStartX = new GridBagConstraints();
		gbc_lblStartX.anchor = GridBagConstraints.EAST;
		gbc_lblStartX.insets = new Insets(0, 0, 5, 5);
		gbc_lblStartX.gridx = 0;
		gbc_lblStartX.gridy = 0;
		jpMainPanel.add(lblStartX, gbc_lblStartX);

		tfStartX = new JTextField();
		tfStartX.setText(String.valueOf(s.getUpperLeft().getX()));
		GridBagConstraints gbc_tfStartX = new GridBagConstraints();
		gbc_tfStartX.insets = new Insets(0, 0, 5, 0);
		gbc_tfStartX.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfStartX.gridx = 1;
		gbc_tfStartX.gridy = 0;
		jpMainPanel.add(tfStartX, gbc_tfStartX);
		tfStartX.setColumns(10);

		JLabel lblStartY = new JLabel("Y (Start point):");
		GridBagConstraints gbc_lblStartY = new GridBagConstraints();
		gbc_lblStartY.anchor = GridBagConstraints.EAST;
		gbc_lblStartY.insets = new Insets(0, 0, 5, 5);
		gbc_lblStartY.gridx = 0;
		gbc_lblStartY.gridy = 1;
		jpMainPanel.add(lblStartY, gbc_lblStartY);

		tfStartY = new JTextField();
		tfStartY.setText(String.valueOf(s.getUpperLeft().getY()));
		GridBagConstraints gbc_tfStartY = new GridBagConstraints();
		gbc_tfStartY.insets = new Insets(0, 0, 5, 0);
		gbc_tfStartY.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfStartY.gridx = 1;
		gbc_tfStartY.gridy = 1;
		jpMainPanel.add(tfStartY, gbc_tfStartY);
		tfStartY.setColumns(10);

		JLabel lblSide = new JLabel("Side:");
		GridBagConstraints gbc_lblSide = new GridBagConstraints();
		gbc_lblSide.anchor = GridBagConstraints.EAST;
		gbc_lblSide.insets = new Insets(0, 0, 5, 5);
		gbc_lblSide.gridx = 0;
		gbc_lblSide.gridy = 2;
		jpMainPanel.add(lblSide, gbc_lblSide);

		tfSide = new JTextField();
		tfSide.setText(String.valueOf(s.getSide()));
		GridBagConstraints gbc_tfSide = new GridBagConstraints();
		gbc_tfSide.insets = new Insets(0, 0, 5, 0);
		gbc_tfSide.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfSide.gridx = 1;
		gbc_tfSide.gridy = 2;
		jpMainPanel.add(tfSide, gbc_tfSide);
		tfSide.setColumns(10);

		JLabel lblOutlineColor = new JLabel("Outline color:");
		GridBagConstraints gbc_lblOutlineColor = new GridBagConstraints();
		gbc_lblOutlineColor.anchor = GridBagConstraints.EAST;
		gbc_lblOutlineColor.insets = new Insets(0, 0, 5, 5);
		gbc_lblOutlineColor.gridx = 0;
		gbc_lblOutlineColor.gridy = 3;
		jpMainPanel.add(lblOutlineColor, gbc_lblOutlineColor);

		JButton btnOutlineColor = new JButton("");
		btnOutlineColor.setBackground(outline);
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
		btnOutlineColor.setPreferredSize(new Dimension(40, 20));
		GridBagConstraints gbc_btnOutlineColor = new GridBagConstraints();
		gbc_btnOutlineColor.insets = new Insets(0, 0, 5, 0);
		gbc_btnOutlineColor.gridx = 1;
		gbc_btnOutlineColor.gridy = 3;
		jpMainPanel.add(btnOutlineColor, gbc_btnOutlineColor);

		JLabel lblInsideColor = new JLabel("Inside color:");
		GridBagConstraints gbc_lblInsideColor = new GridBagConstraints();
		gbc_lblInsideColor.anchor = GridBagConstraints.EAST;
		gbc_lblInsideColor.insets = new Insets(0, 0, 5, 5);
		gbc_lblInsideColor.gridx = 0;
		gbc_lblInsideColor.gridy = 4;
		jpMainPanel.add(lblInsideColor, gbc_lblInsideColor);

		JButton btnInsideColor = new JButton("");
		btnInsideColor.setBackground(inside);
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
					startX = Integer.parseInt(tfStartX.getText());
					startY = Integer.parseInt(tfStartY.getText());
					side = Integer.parseInt(tfSide.getText());

					if (startX < 1 || startX > 890 || startY < 1 || startY > 375 || side < 1 || side > 400) {
						throw new NumberFormatException();
					}

					dispose();
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(EditSquareDialog.this,
							"Enter numbers 1-890 for X, 1-375 for Y and 1-400 for side", "Warrning",
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

	public int getStartX() {
		return startX;
	}

	public int getStartY() {
		return startY;
	}

	public int getSide() {
		return side;
	}

	public Color getOutline() {
		return outline;
	}

	public Color getInside() {
		return inside;
	}

}
