package pathVisualizer;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import pathVisualizer.Control.PlacerType;

public class Map {
	private PathVisualizer pathVisualizer;
	private GridPane pane = new GridPane();
	private Room [][] rooms;
	private double roomheight,roomwidth;
	private double mapwidth = 1000, mapheight=mapwidth/2;
	private Room startRoom,endRoom;
	private Player player;
	private boolean readyToDraw = false;

	public Map(PathVisualizer obj) {
		pathVisualizer = obj;
		//creates rooms and define each room's height and width
		rooms = new Room[obj.getRow()][obj.getColumn()];
		roomheight = mapheight/obj.getRow();
		roomwidth = mapwidth/obj.getColumn();
		
		//initialize each room, their color and states,point coordinates, and enable action handler
		//finally add them to the grid pane to display them
		for(int y=0;y<rooms.length;y++) {
			for(int x =0;x<rooms[y].length;x++) {
				rooms[y][x] = new Room(new Point(x,y));
				rooms[y][x].setStyle("-fx-border-color: black;-fx-background-color:white");
				rooms[y][x].setPrefSize(roomheight, roomwidth);
				Room target = rooms[y][x];
				rooms[y][x].setOnMouseClicked(e -> clickHandler(target));
				rooms[y][x].setOnMouseEntered(e -> enterHandler(target));
				pane.add(rooms[y][x], x, y);
			}
		}
		//initialize player 
		player = new Player(obj);
		
	}
	
	public GridPane getPane() {
		pane.setStyle("-fx-border-color: blue");
		pane.setGridLinesVisible(true);
		pane.setAlignment(Pos.CENTER);
		pane.setStyle("-fx-background-color: DARKGRAY");
		return pane;
	}
	
	//room click handler, given the button flag in control pane, determine how to handler
	public void clickHandler(Room room) {
		//if the game is running do nothing, you can't set block while running
		if(pathVisualizer.isGameRunning()) {
			pathVisualizer.getLog().displayLog("The game is running, no further modification allowed");
			return;
		}
		Control control = this.pathVisualizer.getControl();
		//
		if(control.placer == PlacerType.nonPlacer) {
			return;
		}
		if(!room.isFree()&&readyToDraw) {
			readyToDraw = false;
		}
		//gotta make sure room is available 
		if(!room.isFree()) {
			pathVisualizer.getLog().displayLog("Room is not avaiable");
			return;
		}
		//it is available, choose setStart/End/or block
		switch (control.placer) {
		case StartPlacer:
			this.setStart(room);
			break;
		case ExitPlacer:
			this.setExit(room);
			break;
		case BlockPlacer:
			readyToDraw = true;
			this.setBlock(room);
			break;
		default:
			break;
		}
	}
	
	public void setBlock(Room room) {
		if(room == null || room == endRoom) {
			return;
		}
		room.setFree(false);
		room.setDiscovered(true);
		room.setStyle("-fx-background-color: black");
		pathVisualizer.getLog().displayLog("Block places at "+room.getPoint());
	}
	
	public void setExit(Room room) {
		if(room == null) {
			return;
		}
		if(endRoom != null) {
			clearRoom(endRoom);
		}
		endRoom = room;
		room.setFree(true);
		room.setDiscovered(false);
		room.setStyle("-fx-background-color: red");
		pathVisualizer.getLog().displayLog("Exit: " + room.getPoint());
	}
	
	public void setStart(Room room) {
		if(room == null) {
			return;
		}
		if(startRoom != null) {
			clearRoom(startRoom);
		}
		startRoom = room;
		room.setFree(false);
		room.setDiscovered(true);
		room.setStyle("-fx-background-color: blue");
		pathVisualizer.getLog().displayLog("Start places at "+room.getPoint());
	}
	
	public void clearRoom(Room room) {
		if(room == null) {
			return;
		}
		room.setStyle("-fx-border-color: black;-fx-background-color:white");
		room.setFree(true);
		room.setDiscovered(false);
	}
	
	
	public Room getStartRoom() {
		return startRoom;
	}

	public Room getEndRoom() {
		return endRoom;
	}
	
	
	public void setStartRoom(Room startRoom) {
		this.startRoom = startRoom;
	}

	public void setEndRoom(Room endRoom) {
		this.endRoom = endRoom;
	}

	public PathVisualizer getPathVisualizer() {
		return pathVisualizer;
	}
	
	public Room[][] getRooms() {
		return rooms;
	}

	public Room room(int x,int y) {
		if(x < 0 || x >= pathVisualizer.getColumn() || y < 0 || y >= pathVisualizer.getRow())
		{
			return null;
		}
		return rooms[y][x];
	}
	public Room room(Point p) {
		if(p == null) {
			return null;
		}
		return room((int)p.getX(),(int)p.getY());
	}
	
	
	public Player getPlayer() {
		return player;
	}

	public boolean isRoom(Point p) {
		if(p.getX()>=0 && p.getX() < pathVisualizer.getColumn()) {
			if(p.getY() >=0 && p.getY() < pathVisualizer.getRow()) {
				return true;
			}
		}
		return false;
	}
	
	public void enterHandler(Room r) {
		if(!readyToDraw) {
			return;
		}
		Control control = this.pathVisualizer.getControl();
		if(control.placer == PlacerType.BlockPlacer) {
			setBlock(r);
		}
	}
}
