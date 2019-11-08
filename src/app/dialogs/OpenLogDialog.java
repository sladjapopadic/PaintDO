package app.dialogs;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JDialog;

import app.mvc.AppController;
import app.mvc.AppFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;

public class OpenLogDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	public OpenLogDialog(AppController controller, BufferedReader bf) {

		setSize(212, 74);
		setLocationRelativeTo(null);
		setModal(true);
		setResizable(false);
		setTitle("Load commands");
		Image img = new ImageIcon(AppFrame.class.getResource("/icons/redo.png")).getImage();
		this.setIconImage(img);

		JPanel jpMainPanel = new JPanel();
		getContentPane().add(jpMainPanel, BorderLayout.CENTER);
		jpMainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton btnNextCmd = new JButton("Next command");
		btnNextCmd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				String line;
				try {
					if ((line = bf.readLine()) != null) {  
						controller.loadNext(line);
					} else {
						dispose();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		jpMainPanel.add(btnNextCmd);
	}
}
