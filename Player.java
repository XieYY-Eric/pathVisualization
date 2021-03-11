package pathVisualizer;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import pathVisualizer.PathVisualizer.GameStates;

public class Player {
	private Map map;
	private PathVisualizer pathVisualizer;
	private Log log;
	private Point currentPos,targetPos;
	private int steps = 0;
	private String algorithm;
	private Animation animation;
	public enum playerStates{waiting,searching,stuck,foundExit,stop};
	public static playerStates m_states = playerStates.waiting;

	//data structure required for algorithm
	private Queue<Point> look_queue;
	private Stack<Point> look_stack;
	private LinkedList<Point> shorestPath;
	private HashMap<Point, Point> parent = new HashMap<>();
	
	public Player(PathVisualizer obj) {
		pathVisualizer = obj;
		look_queue = new LinkedList<>();
		look_stack = new Stack<>();
	}
	
	public void moveTo(Point p) {
		currentPos = p;
		map.room(p).draw();
		steps +=1;
	}

	public void moveTo(Room r) {
		currentPos = r.getPoint();
		r.draw();
		steps +=1;
	}
	
	public Point getCurrentPos() {
		return currentPos;
	}
	

	public void setCurrentPos(Point currentPos) {
		this.currentPos = currentPos;
	}

	public int getSteps() {
		return steps;
	}

	public playerStates getPlayerStates() {
		return m_states;
	}

	//terminal the game when player is not moving
	public void setM_states(playerStates m_states) {
		Player.m_states = m_states;
		switch(m_states) {
		case searching:
			log.displayLog("seaching");
			break;
		case waiting:
			log.displayLog("waiting...");
			break;
		case stuck:
			PathVisualizer.setGameStates(GameStates.stop);
			log.displayLog("A total deadEnd "+steps);
			break;
		case foundExit:
			log.displayLog("Found Exit, Total Steps: "+steps);
			PathVisualizer.setGameStates(GameStates.stop);
			break;
		case stop:
			log.displayLog("pauseing...");
			PathVisualizer.setGameStates(GameStates.stop);
		}
	}
	
	public void getReady() {
		map = pathVisualizer.getMap();
		log = map.getPathVisualizer().getLog();
		this.currentPos = null;
		look_queue = new LinkedList<>();
		look_stack = new Stack<>();
		look_queue.add(map.getStartRoom().getPoint());
		look_stack.add(map.getStartRoom().getPoint());
		algorithm = map.getPathVisualizer().getAlgorithmChoice().getAlgorithm();
		shorestPath = new LinkedList<>();
	

	}
	
	//move one step at a time
	public void updata() {
		
		switch(algorithm) {
		case "BFS":
			breathFirstSearch();
			break;
		case "DFS":
			depthFirstSeach();
			break;
		default:
			
		}
		
	}
	
	void showShortestPath() {
		shorestPath.addFirst(currentPos);
		Point prev = parent.get(currentPos);
		while (prev != null) {
			shorestPath.addFirst(prev);
			prev = parent.get(prev);
		}
		animation = new Timeline(new KeyFrame(javafx.util.Duration.millis(100), e->{
			if(shorestPath.isEmpty()) {
				return;
			}
			Point curr = shorestPath.getFirst();
			map.room(curr).setStyle("-fx-background-color: yellow");
			shorestPath.removeFirst();
		}));
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.play();
		
	}
		
	public void breathFirstSearch() {
		Point west = new Point(), east = new Point(), north = new Point(), south = new Point();
		Point []dir = { north,east,south,west };
		//no more place to find, get stuck
		if(look_queue.isEmpty()) {
			setM_states(playerStates.stuck);
			return;
		}
		targetPos = look_queue.peek();
		moveTo(targetPos);
		look_queue.remove();
		//found the exit after this move
		if(currentPos.compare( map.getEndRoom().getPoint())) {
			setM_states(playerStates.foundExit);
			showShortestPath();
			return;
		}
		dir[0].set(currentPos.getX(), currentPos.getY()-1);
		dir[1].set(currentPos.getX()+1, currentPos.getY());
		dir[2].set(currentPos.getX(), currentPos.getY()+1);
		dir[3].set(currentPos.getX()-1, currentPos.getY());
		for (int i = 0; i < 4; i++) {
			//check out of bound or not
			if(map.isRoom(dir[i])) {
				if (!map.room(dir[i]).isDiscovered() && map.room(dir[i]).isFree()) {
					look_queue.add(dir[i]);
					map.room(dir[i]).setDiscovered(true);
					parent.put(dir[i],currentPos);
				}
			}
			
		}
	}
	
	public void depthFirstSeach() {
		Point west = new Point(), east = new Point(), north = new Point(), south = new Point();
		Point []dir = { west,east,north,south };
		//no more place to find, get stuck
		if(look_stack.isEmpty()) {
			setM_states(playerStates.stuck);
			return;
		}
		targetPos = look_stack.peek();
		moveTo(targetPos);
		look_stack.pop();
		//found the exit after this move
		if(currentPos.compare( map.getEndRoom().getPoint())) {
			setM_states(playerStates.foundExit);
			showShortestPath();
			return;
		}
		dir[0].set(currentPos.getX()-1, currentPos.getY());
		dir[1].set(currentPos.getX()+1, currentPos.getY());
		dir[2].set(currentPos.getX(), currentPos.getY()-1);
		dir[3].set(currentPos.getX(), currentPos.getY()+1);
		for (int i = 0; i < 4; i++) {
			//check out of bound or not
			if(map.isRoom(dir[i])) {
				if (!map.room(dir[i]).isDiscovered() && map.room(dir[i]).isFree()) {
					look_stack.add(dir[i]);
					map.room(dir[i]).setDiscovered(true);
					parent.put(dir[i],currentPos);
				}
			}
			
		}
	}
}
