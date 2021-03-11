package pathVisualizer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		Pane pane = new PathVisualizer().getVisualizerPane();
		stage.setTitle("PathVisualizer");
		stage.setScene(new Scene(pane,1000,500));
		stage.show();
	}

}
