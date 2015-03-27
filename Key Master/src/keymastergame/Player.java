package keymastergame;

import java.util.ArrayList;

public class Player {

	// Constants are Here
	//final int JUMPSPEED = -15;
	final int MOVESPEED = 2;
	final int GROUND = 382;
	
	private int centerX = 100;
	private int centerY = GROUND;
	//private boolean jumped = false;
	private boolean movingLeft = false;
	private boolean movingRight = false;
	private boolean movingDown = false;
	private boolean movingUp = false;
	//private boolean ducked = false;

	private int speedX = 0;
	private int speedY = 0;
	
	//private Background bg1 = StartingClass.getBg1();
	//private Background bg2 = StartingClass.getBg2();
	
	private ArrayList<Hole> holes = new ArrayList<Hole>();

	public void update() {
		// Moves Character or Scrolls Background accordingly.

		if (speedX < 0) {
			centerX += speedX;
		}
		/*if (speedX == 0 || speedX < 0) {
			bg1.setSpeedX(0);
			bg2.setSpeedX(0);

		}*/
		if (/*centerX <= 200 &&*/ speedX > 0) {
			centerX += speedX;
		}
		if (/*centerX <= 200 &&*/ speedY < 0) {
			centerY += speedY;
		}
		if (/*centerX <= 200 &&*/ speedY > 0) {
			centerY += speedY;
		}
		/*if (speedX > 0 && centerX > 200){
			bg1.setSpeedX(-MOVESPEED);
			bg2.setSpeedX(-MOVESPEED);
		}*/

		// Updates Y Position
		/*centerY += speedY;
		if (centerY + speedY >= GROUND) {
			centerY = GROUND;
		}*/

		// Handles Jumping
		/*if (jumped == true) {
			speedY += 1;

			if (centerY + speedY >= GROUND) {
				centerY = GROUND;
				speedY = 0;
				jumped = false;
			}
		}*/
		// Prevents going beyond X coordinate of 0
		/*if (centerX + speedX <= 60) {
			centerX = 61;
		}*/
	}
	public void moveRight() {
		//if (ducked == false) {
			speedX = MOVESPEED;
		//}
	}

	public void moveLeft() {
		//if (ducked == false) {
			speedX = -MOVESPEED;
		//}
	}
	
	public void moveUp() {
		//if (ducked == false) {
			speedY = -MOVESPEED;
		//}
	}
	
	public void moveDown() {
		//if (ducked == false) {
		speedY = MOVESPEED;
		//}
	}

	public void stopRight() {
		setMovingRight(false);
		stop();
	}

	public void stopLeft() {
		setMovingLeft(false);
		stop();
	}
	
	public void stopUp() {
		setMovingUp(false);
		stop();
	}
	
	public void stopDown() {
		setMovingDown(false);
		stop();
	}

	public void stop() {
		if (isMovingRight() == false && isMovingLeft() == false && isMovingUp() == false && isMovingDown() == false) {
			speedX = 0;
			speedY = 0;
		}
		if (isMovingRight() == false && isMovingLeft() == true) {
			moveLeft();
		}
		if (isMovingRight() == true && isMovingLeft() == false) {
			moveRight();
		}
		if (isMovingUp() == false && isMovingDown() == true) {
			moveDown();
		}
		if (isMovingUp() == true && isMovingDown() == false) {
			isMovingUp();
		}

	}

	/*public void jump() {
		if (jumped == false) {
			speedY = JUMPSPEED;
			jumped = true;
		}

	}*/
	
	/*public void shoot() {
		Projectile p = new Projectile(centerX + 50, centerY - 25);
		projectiles.add(p);
	}*/

	public int getCenterX() {
		return centerX;
	}

	public int getCenterY() {
		return centerY;
	}

	/*public boolean isJumped() {
		return jumped;
	}*/

	public int getSpeedX() {
		return speedX;
	}

	public int getSpeedY() {
		return speedY;
	}

	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}

	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}

	/*public void setJumped(boolean jumped) {
		this.jumped = jumped;
	}*/

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public void setSpeedY(int speedY) {
		this.speedY = speedY;
	}

	/*public boolean isDucked() {
		return ducked;
	}*/

	/*public void setDucked(boolean ducked) {
		this.ducked = ducked;
	}*/
	
	public void createHole() {
		Hole p = new Hole(centerX + 20, centerY);
		holes.add(p);
	}

	public boolean isMovingRight() {
		return movingRight;
	}

	public void setMovingRight(boolean movingRight) {
		this.movingRight = movingRight;
	}

	public boolean isMovingLeft() {
		return movingLeft;
	}

	public void setMovingLeft(boolean movingLeft) {
		this.movingLeft = movingLeft;
	}
	
	public boolean isMovingUp() {
		return movingUp;
	}

	public void setMovingUp(boolean movingUp) {
		this.movingUp = movingUp;
	}
	
	public boolean isMovingDown() {
		return movingDown;
	}

	public void setMovingDown(boolean movingDown) {
		this.movingDown = movingDown;
	}

	public ArrayList getHoles() {
		return holes;
	}

}
