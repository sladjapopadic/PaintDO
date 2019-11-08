package app.io;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class SavingLog implements Saving {
	
	private DefaultListModel<String> dlm;  

	public SavingLog(DefaultListModel<String> dlm) {
		this.dlm = dlm;
	}

	@Override
	public void save() {
		JFileChooser saveDrawing = new JFileChooser("c:\\Users\\sladj\\Desktop");
		saveDrawing.setDialogTitle("Choose save destination");
		if (saveDrawing.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			File drawing = new File(saveDrawing.getSelectedFile().getAbsolutePath() + "_log.txt");

			if (drawing.exists()) {
				JOptionPane.showMessageDialog(null, "Filename already exists!", "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				try {
					PrintWriter pw = new PrintWriter(drawing);
					for (int i = 0; i < dlm.size(); i++) {
						pw.println(dlm.get(i));
					}
					pw.close();
					JOptionPane.showMessageDialog(null, "File successfully saved", "Saving complete",
							JOptionPane.INFORMATION_MESSAGE);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
