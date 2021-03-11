package pathVisualizer;

import javafx.scene.layout.BorderPane;

public class PathVisualizer {
	private Map map;
	private Control control;
	private Log log;
	private AlgorithmChoice algorithmChoice;
	private int row = 20;
	private int column = 2*row;
	public enum GameStates{running,stop} ;
	public static GameStates gameState = GameStates.stop;
	
	public PathVisualizer() {
		log = new Log(this);
		map = new Map(this);
		control = new Control(this);
		algorithmChoice = new AlgorithmChoice();
	}
	
	public BorderPane getVisualizerPane() {
		BorderPane pane = new BorderPane();
		pane.setTop(algorithmChoice.getPane());
		pane.setCenter(map.getPane());
		pane.setBottom(control.getPane());
		pane.setRight(log.getPane());
		
		return pane;
	}
	//fundamental function, the ability to access different section of the projects
	public Map getMap() {
		return map;
	}
	public Control getControl() {
		return control;
	}
	public Log getLog() {
		return log;
	}
	public AlgorithmChoice getAlgorithmChoice() {
		return algorithmChoice;
	}
	
	//reference only
	public int getRow() {
		return row;
	}
	public int getColumn() {
		return column;
	}

	
	public boolean isGameRunning() {
		return gameState == GameStates.running;
	}
	
	public boolean isGameFinished() {
		return gameState == GameStates.stop;
	}
	
	public static void setGameStates(GameStates states) {
		gameState = states;
	}

}
