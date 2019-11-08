package app.dialogs;

import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import javax.swing.JTextField;

import app.mvc.AppFrame;

import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddSquareDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private int side;
	private JTextField tfSide;

	public AddSquareDialog() {

		setTitle("Add Square");
		setModal(true);
		setSize(190, 113);
		setResizable(false);
		setLocationRelativeTo(null);
		Image img = new ImageIcon(AppFrame.class.getResource("/icons/square.png")).getImage();
		this.setIconImage(img);

		JPanel jpMainPanel = new JPanel();
		getContentPane().add(jpMainPanel, BorderLayout.CENTER);
		GridBagLayout gbl_jpMainPanel = new GridBagLayout();
		gbl_jpMainPanel.columnWidths = new int[] { 0, 0, 0 };
		gbl_jpMainPanel.rowHeights = new int[] { 0, 0, 0 };
		gbl_jpMainPanel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_jpMainPanel.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		jpMainPanel.setLayout(gbl_jpMainPanel);

		JLabel lblEnterSide = new JLabel("Enter side:");
		GridBagConstraints gbc_lblEnterSide = new GridBagConstraints();
		gbc_lblEnterSide.insets = new Insets(0, 0, 5, 5);
		gbc_lblEnterSide.anchor = GridBagConstraints.EAST;
		gbc_lblEnterSide.gridx = 0;
		gbc_lblEnterSide.gridy = 0;
		jpMainPanel.add(lblEnterSide, gbc_lblEnterSide);

		tfSide = new JTextField();
		GridBagConstraints gbc_tfSide = new GridBagConstraints();
		gbc_tfSide.insets = new Insets(0, 0, 5, 0);
		gbc_tfSide.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfSide.gridx = 1;
		gbc_tfSide.gridy = 0;
		jpMainPanel.add(tfSide, gbc_tfSide);
		tfSide.setColumns(10);

		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					side = Integer.parseInt(tfSide.getText());
					if (side < 0 || side > 400) {
						throw new NumberFormatException();
					}
					dispose();
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(AddSquareDialog.this, "Invalid input!");
				}
			}
		});
		GridBagConstraints gbc_btnSubmit = new GridBagConstraints();
		gbc_btnSubmit.gridx = 1;
		gbc_btnSubmit.gridy = 1;
		jpMainPanel.add(btnSubmit, gbc_btnSubmit);
	}

	public int getSide() {
		return side;
	}
}
