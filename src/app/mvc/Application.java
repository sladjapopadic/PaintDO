package app.mvc;

public class Application {
	public static void main(String[] args) {
		AppModel model = new AppModel();
		AppFrame frame = new AppFrame();
		frame.getView().setModel(model);
		AppController controller = new AppController(model, frame);
		frame.setController(controller);

		frame.setSize(900, 650);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setTitle("Paint");
		frame.setResizable(false);
	}
}
