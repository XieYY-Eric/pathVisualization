package pathVisualizer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class AlgorithmChoice {
	private Label startRoom = new Label("startRoom"), lbstartRoom = new Label();
	private Label exitRoom = new Label("ExitRoom"), lbexitRoom = new Label();
	private Label blockRoom = new Label("BlockRoom"), lbblockRoom = new Label();
	private Label discoveredRoom = new Label("DiscoveredRoom"), lbdiscoveredRoom = new Label();
	private Label shortestPathRoom = new Label("Path"), lbShPathRoom = new Label();
	private String[] algorithmList = {"BFS","DFS"};
	private ObservableList<String> algorithms = (ObservableList<String>) FXCollections.observableArrayList(algorithmList);
	private String algorithm = algorithmList[0];
	private ComboBox<String> cb = new ComboBox<>(algorithms);
	private HBox pane = new HBox();
	
	public AlgorithmChoice() {
		lbstartRoom.setStyle("-fx-background-color: blue");
		lbstartRoom.setPrefWidth(20);
		lbexitRoom.setStyle("-fx-background-color: red");
		lbexitRoom.setPrefWidth(20);
		lbblockRoom.setStyle("-fx-background-color: black");
		lbblockRoom.setPrefWidth(20);
		lbdiscoveredRoom.setStyle("-fx-background-color: grey");
		lbdiscoveredRoom.setPrefWidth(20);
		lbShPathRoom.setStyle("-fx-background-color: yellow");
		lbShPathRoom.setPrefWidth(20);
		
		startRoom.setAlignment(Pos.CENTER_RIGHT);
		exitRoom.setAlignment(Pos.CENTER_RIGHT);
		blockRoom.setAlignment(Pos.CENTER_RIGHT);
		discoveredRoom.setAlignment(Pos.CENTER_RIGHT);
		
		cb.setOnAction(e-> comboxHandler());
	}
	public HBox getPane() {
		cb.setValue(algorithmList[0]);
		pane.setStyle("-fx-border-color: black;-fx-background-color:IVORY");
		pane.setPadding(new Insets(15, 12, 15, 12));
		pane.setSpacing(50);
		pane.getChildren().addAll(new Label("startRoom"), lbstartRoom, new Label("ExitRoom"), lbexitRoom,
				new Label("BlockRoom"), lbblockRoom, new Label("DiscoveredRoom"), lbdiscoveredRoom, shortestPathRoom,
				lbShPathRoom, cb);
		pane.setAlignment(Pos.CENTER);
		return pane;
	}
	public void comboxHandler() {
		algorithm = cb.getValue();
	}
	
	public String getAlgorithm() {
		return algorithm;
	}
}
