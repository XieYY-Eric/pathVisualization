package pathVisualizer;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import pathVisualizer.PathVisualizer.GameStates;
import pathVisualizer.Player.playerStates;

public class Control{
	private PathVisualizer pathVisualizer;
	private HBox pane = new HBox();
	private Button startButton = new Button("start");
	private Button stopButton = new Button("stop");
	private Button startPoint = new Button("placeStart");
	private Button endPoint = new Button("placeExit");
	private Button setWall = new Button("placeWall");
	private Button clearbt = new Button("Clear");
	private Log log;
	private Timeline animation;
	public enum PlacerType{StartPlacer,ExitPlacer,BlockPlacer,nonPlacer};
	public PlacerType placer = PlacerType.nonPlacer;

	public Control(PathVisualizer obj) {
		pathVisualizer = obj;
		log = pathVisualizer.getLog();
		//add children
		pane.getChildren().addAll(startButton,stopButton,startPoint,endPoint,setWall,clearbt);
		startButton.setOnAction(e-> start());
		stopButton.setOnAction(e-> stop());
		startPoint.setOnAction(e-> setStartPosition());
		endPoint.setOnAction(e-> setExitRoom());
		setWall.setOnAction(e-> placeBlock());
		clearbt.setOnAction(e-> clearAll());
	}
	
	public HBox getPane() {
		pane.setStyle("-fx-border-color: red;-fx-background-color:CYAN");
		pane.setPadding(new Insets(15, 12, 15, 12));
		pane.setSpacing(100);
		pane.setAlignment(Pos.CENTER);
		return pane;
	}
	
	public void start() {
		if(Player.m_states == playerStates.stuck || Player.m_states == playerStates.foundExit) {
			return;
		}
		if(pathVisualizer.getMap().getEndRoom() == null || pathVisualizer.getMap().getStartRoom() == null) {
			log.displayLog("Please select endRoom and StartRoom first");
			return;
		}
		PathVisualizer.setGameStates(GameStates.running);
		log.displayLog("Game start running");
		placer = PlacerType.nonPlacer;
		//Initialize player data if player is waiting
		if(Player.m_states == playerStates.waiting) {
			pathVisualizer.getMap().getPlayer().getReady();
			pathVisualizer.getMap().getPlayer().setM_states(playerStates.searching);
			//run the game until game is finished
			animation = new Timeline(new KeyFrame(javafx.util.Duration.millis(50), e-> game()));
			animation.setCycleCount(Timeline.INDEFINITE);
			animation.play();
		}else if(Player.m_states == playerStates.stop){
			Player.m_states = playerStates.searching;
			animation.play();
		}
	}
	
	public void stop() {
		//pause the game
		if(Player.m_states == playerStates.searching) {
			Player.m_states = playerStates.stop;
			animation.stop();
			log.displayLog("Game pause...");
		}else {
			//end the game
			PathVisualizer.setGameStates(GameStates.stop);
			Player.m_states = playerStates.waiting;
			pathVisualizer.getMap().setStartRoom(null);
			pathVisualizer.getMap().setEndRoom(null);		
			clearAll();
			log.displayLog("Game end");
		}		
		
	}
	
	public void setStartPosition() {
		if(PathVisualizer.gameState == GameStates.running) {
			log.displayLog("Game is running, no modification allowed");
			return;
		}
		placer = PlacerType.StartPlacer;
		log.displayLog("Ready to set Start Postion...");
	}
	
	public void setExitRoom() {
		if(PathVisualizer.gameState == GameStates.running) {
			log.displayLog("Game is running, no modification allowed");
			return;
		}
		placer = PlacerType.ExitPlacer;
		log.displayLog("Ready to set Exit Postion...");
	}
	
	public void placeBlock() {
		if(PathVisualizer.gameState == GameStates.running) {
			log.displayLog("Game is running, no modification allowed");
			return;
		}
		placer = PlacerType.BlockPlacer;
		log.displayLog("Ready to set Block Postion...");
	}
	
	public void clearAll() {
		if(PathVisualizer.gameState == GameStates.running) {
			log.displayLog("Game is running, no modification allowed");
			return;
		}
		Map map = this.pathVisualizer.getMap();
		for(Room rs[]:map.getRooms()) {
			for(Room r:rs) {
				map.clearRoom(r);
			}
		}
		map.setStartRoom(null);
		map.setEndRoom(null);
		Player.m_states = playerStates.waiting;
		log.clearLog();
	}

	public void game() {
		if(pathVisualizer.isGameRunning()) {
			pathVisualizer.getMap().getPlayer().updata();
		}
	}
}
