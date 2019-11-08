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

public class AddCircleDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private JTextField tfRadius;

	private int radius;
	
	public AddCircleDialog() {
		setTitle("Add Circle");
		setModal(true);
		setSize(190,113);
		setResizable(false);
		setLocationRelativeTo(null);
		Image img = new ImageIcon(AppFrame.class.getResource("/icons/circle.png")).getImage();
	    this.setIconImage(img);
	    
	    JPanel jpMainPanel = new JPanel();
	    getContentPane().add(jpMainPanel, BorderLayout.CENTER);
	    GridBagLayout gbl_jpMainPanel = new GridBagLayout();
	    gbl_jpMainPanel.columnWidths = new int[]{0, 0, 0};
	    gbl_jpMainPanel.rowHeights = new int[]{0, 0, 0};
	    gbl_jpMainPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
	    gbl_jpMainPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
	    jpMainPanel.setLayout(gbl_jpMainPanel);
	    
	    JLabel lblEnterRadius = new JLabel("Enter radius:");
	    GridBagConstraints gbc_lblEnterRadius = new GridBagConstraints();
	    gbc_lblEnterRadius.insets = new Insets(0, 0, 5, 5);
	    gbc_lblEnterRadius.anchor = GridBagConstraints.EAST;
	    gbc_lblEnterRadius.gridx = 0;
	    gbc_lblEnterRadius.gridy = 0;
	    jpMainPanel.add(lblEnterRadius, gbc_lblEnterRadius);
	    
	    tfRadius = new JTextField();
	    GridBagConstraints gbc_tfRadius = new GridBagConstraints();
	    gbc_tfRadius.insets = new Insets(0, 0, 5, 0);
	    gbc_tfRadius.fill = GridBagConstraints.HORIZONTAL;
	    gbc_tfRadius.gridx = 1;
	    gbc_tfRadius.gridy = 0;
	    jpMainPanel.add(tfRadius, gbc_tfRadius);
	    tfRadius.setColumns(10);
	    
	    JButton btnSubmit = new JButton("Submit");
	    btnSubmit.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseClicked(MouseEvent arg0) {
	    		try {
					radius = Integer.parseInt(tfRadius.getText());
					if(radius<0 || radius>400)
					{
						throw new NumberFormatException();
					}
					dispose();
				}
				catch(NumberFormatException e){
					JOptionPane.showMessageDialog(AddCircleDialog.this, "Invalid input!");
				}
	    	}
	    });
	    GridBagConstraints gbc_btnSubmit = new GridBagConstraints();
	    gbc_btnSubmit.gridx = 1;
	    gbc_btnSubmit.gridy = 1;
	    jpMainPanel.add(btnSubmit, gbc_btnSubmit);
	}

	public int getRadius() {
		return radius;
	}
}
