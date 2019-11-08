package app.dialogs;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JDialog;

import app.mvc.AppFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AddRectangleDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextField tfWidth;
	private JTextField tfHeight;
	private int heightR;
	private int widthR;

	public AddRectangleDialog() {
		setTitle("Add Rectangle");
		setModal(true);
		setSize(190, 113);
		setResizable(false);
		setLocationRelativeTo(null);
		Image img = new ImageIcon(AppFrame.class.getResource("/icons/rectangle.png")).getImage();
		this.setIconImage(img);

		JPanel jpMainPanel = new JPanel();
		getContentPane().add(jpMainPanel, BorderLayout.CENTER);
		GridBagLayout gbl_jpMainPanel = new GridBagLayout();
		gbl_jpMainPanel.columnWidths = new int[] { 0, 0, 0 };
		gbl_jpMainPanel.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_jpMainPanel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_jpMainPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		jpMainPanel.setLayout(gbl_jpMainPanel);

		JLabel lblEnterWidth = new JLabel("Enter width:");
		GridBagConstraints gbc_lblEnterWidth = new GridBagConstraints();
		gbc_lblEnterWidth.insets = new Insets(0, 0, 5, 5);
		gbc_lblEnterWidth.anchor = GridBagConstraints.EAST;
		gbc_lblEnterWidth.gridx = 0;
		gbc_lblEnterWidth.gridy = 0;
		jpMainPanel.add(lblEnterWidth, gbc_lblEnterWidth);

		tfWidth = new JTextField();
		GridBagConstraints gbc_tfWidth = new GridBagConstraints();
		gbc_tfWidth.insets = new Insets(0, 0, 5, 0);
		gbc_tfWidth.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfWidth.gridx = 1;
		gbc_tfWidth.gridy = 0;
		jpMainPanel.add(tfWidth, gbc_tfWidth);
		tfWidth.setColumns(10);

		JLabel lblEnterHeight = new JLabel("Enter height:");
		GridBagConstraints gbc_lblEnterHeight = new GridBagConstraints();
		gbc_lblEnterHeight.anchor = GridBagConstraints.EAST;
		gbc_lblEnterHeight.insets = new Insets(0, 0, 5, 5);
		gbc_lblEnterHeight.gridx = 0;
		gbc_lblEnterHeight.gridy = 1;
		jpMainPanel.add(lblEnterHeight, gbc_lblEnterHeight);

		tfHeight = new JTextField();
		GridBagConstraints gbc_tfHeight = new GridBagConstraints();
		gbc_tfHeight.insets = new Insets(0, 0, 5, 0);
		gbc_tfHeight.anchor = GridBagConstraints.NORTH;
		gbc_tfHeight.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfHeight.gridx = 1;
		gbc_tfHeight.gridy = 1;
		jpMainPanel.add(tfHeight, gbc_tfHeight);
		tfHeight.setColumns(10);

		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					heightR = Integer.parseInt(tfHeight.getText());
					widthR = Integer.parseInt(tfWidth.getText());
					if (heightR < 0 || heightR > 400 || widthR < 0 || widthR > 400) {
						throw new NumberFormatException();
					}
					dispose();
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(AddRectangleDialog.this, "Invalid input!");
				}
			}
		});
		GridBagConstraints gbc_btnSubmit = new GridBagConstraints();
		gbc_btnSubmit.gridx = 1;
		gbc_btnSubmit.gridy = 2;
		jpMainPanel.add(btnSubmit, gbc_btnSubmit);
	}

	public int getHeightR() {
		return heightR;
	}

	public int getWidthR() {
		return widthR;
	}

}
