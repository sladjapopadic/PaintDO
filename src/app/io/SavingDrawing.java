package app.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import geometry.Shape;

public class SavingDrawing implements Saving {

	private ArrayList<Shape> listOfShapes;

	public SavingDrawing(ArrayList<Shape> listOfShapes) {
		this.listOfShapes = listOfShapes;
	}

	@Override
	public void save() {
		JFileChooser saveDrawing = new JFileChooser("c:\\Users\\sladj\\Desktop"); 
		saveDrawing.setDialogTitle("Choose save destination");
		if (saveDrawing.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) { 
			File drawing = new File(saveDrawing.getSelectedFile().getAbsolutePath()); 

			if (drawing.exists()) { 
				JOptionPane.showMessageDialog(null, "Filename already exists!", "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				FileOutputStream fos;
				ObjectOutputStream oos;
				try { 
					fos = new FileOutputStream(drawing);
					oos = new ObjectOutputStream(fos);

					oos.writeObject(listOfShapes);
					oos.close();
					fos.close();

					JOptionPane.showMessageDialog(null, "File successfully saved", "Saving complete",
							JOptionPane.INFORMATION_MESSAGE);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
