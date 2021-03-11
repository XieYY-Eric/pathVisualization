package pathVisualizer;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class Log {
	private VBox pane = new VBox();
	private String message ="Log initialize";
	private TextArea logArea = new TextArea();
	private Button btClear = new Button("reFresh");
	public Log(PathVisualizer obj) {
		logArea.setPrefColumnCount(15);
		logArea.setPrefRowCount(20);
		logArea.wrapTextProperty().set(true);
		logArea.editableProperty().set(false);
		btClear.setOnAction(e->clearLog());
		logArea.setText(">>>"+message);
		logArea.setStyle("-fx-text-fill: white;-fx-control-inner-background:black");
	}
	
	public VBox getPane() {
		pane.getChildren().addAll(logArea,btClear);
		pane.setStyle("-fx-border-color: red;-fx-background-color:DARKSEAGREEN");
		pane.setPadding(new Insets(15, 50, 15, 50));
		return pane;
	}
	
	public void displayLog(String log) {
		String newlog =  log +"\n>>>"+ message;
		message = newlog;
		logArea.setText(">>>"+message);
	}
	
	public void replaceLog(String log) {
		message = log;
		logArea.setText(">>>"+message);
	}
	
	public void clearLog() {
		message = "";
		logArea.setText(">>>");
	}
	
	
}
