package pathVisualizer;

public class Point {
	private double x;
	private double y;
	
	public Point() {
		x = 0;
		y = 0;
	}
	
	public Point(double x,double y) {
		this.x = x;
		this.y = y;
	}
	
	public Point(Point obj) {
		x = obj.x;
		y = obj.y;
	}
	//setter
	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	public void set(double x, double y) {
		this.x = x;
		this.y = y;
	}
	//getter
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "(" +x+","+y +")";
	}
	
	//compare to another Point
	public boolean compare(Point obj) {
		return this.x == obj.getX() && this.y == obj.getY();
	}

	public double SqurDis(Point obj) {
		double xDiff = Math.abs(obj.getX() - x);
		double yDiff = Math.abs(obj.getY() - y);
		
		return Math.sqrt(xDiff*xDiff + yDiff*yDiff);
	}
}
