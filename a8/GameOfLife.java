package a8;

public class GameOfLife {

	public static void main(String[] args) {
		// Starting point for your submission.
		ConwayModel model = new ConwayModel();
		ConwayView view = new ConwayView();
		view.setSize(500,500);
		ConwayController controller = new ConwayController(model, view);
		
		view.setVisible(true);
		
	}
}
