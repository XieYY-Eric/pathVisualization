package pathVisualizer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;

public class Room extends Label{

	private Point point;
	private boolean free = true;
	private boolean discovered = false;
	private Timeline animation = new Timeline(new KeyFrame(javafx.util.Duration.millis(50), e -> transitionHandler()));
	private int rgb = 255;
	public Room() {
		super();
		point = new Point();
		
	}
	
	private void transitionHandler() {
		// TODO Auto-generated method stub
		if(rgb<125) {
			pause();
			return;
		}
		rgb-=10;
		String rgbValue = "rgb("+rgb+","+rgb+","+rgb+")";
		System.out.println(rgb);
		this.setStyle("-fx-background-color: "+rgbValue);
		return;
	}

	public Room(Point po) {
		super();
		point = new Point(po);
	}
	
	public Room(String s) {
		super(s);
		point = new Point();
	}
	
	public Point getPoint() {
		return point;
	}
	
	public void setFree(boolean free) {
		this.free = free;
	}
	public boolean isFree() {
		return free;
	}
	
	public boolean isDiscovered() {
		return discovered;
	}
	
	public void setDiscovered(boolean discovered) {
		this.discovered = discovered;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Room: " + point + "\nFree: " + free + "\ndiscovered: " +discovered;
	}

	//draw doesn't change the attribute, only color
	public void draw() {
		rgb =255;
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.play();
	}
	
	public void pause() {
		animation.pause();
	}
}
