package keymastergame;

public class Hole {

	private int x, y;
	private boolean visible;
	
	public Hole(int startX, int startY){
		x = startX;
		y = startY;
		//speedX = 7;
		visible = true;
	}
	
	public void update(){
		//x += speedX;
		/*if (x > 800){
			visible = false;
		}*/
		
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	/*public int getSpeedX() {
		return speedX;
	}*/

	public boolean isVisible() {
		return visible;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	/*public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}*/

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
}
