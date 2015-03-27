package keymastergame.framework;

public class Vector {

	public double x;
	public double y;

	public Vector() {
		x = 0;
		y = 0;
	}

	public Vector(double i, double j) {
		x = i;
		y = j;
	}

	public Vector(Vector v) {
		x = v.x;
		y = v.y;
	}

	public void add(Vector v) {
		x = x + v.x;
		y = y + v.y;
	}

	public void subtract(Vector v) {
		x = x - v.x;
		y = y - v.y;
	}

	public double getMagnitude() {
		return Math.sqrt((x * x) + (y * y));
	}
}
