package keymastergame;

import java.awt.Color;
import java.awt.Graphics;

import keymastergame.framework.Box;
import keymastergame.framework.Vector;

public class GameObject {
	
	public Box collision;
	public Vector velocity;
	private double gravAcc = 0.8;
		
	public boolean collisionUp;
	public boolean collisionRight;
	public boolean collisionDown;
	public boolean collisionLeft;
	
	
	public GameObject() {
		collision = new Box(new Vector(0,0), new Vector(26,32));
		velocity = new Vector(0,0);

		collisionUp = false;
		collisionRight = false;
		collisionDown = false;
		collisionLeft = false;
	}

	public GameObject(Vector pos) {
		
		collision = new Box(pos, new Vector(26,32));
		velocity = new Vector(0,0);
		

		collisionUp = false;
		collisionRight = false;
		collisionDown = false;
		collisionLeft = false;
	}
	
	public void update() {	
		//System.out.println(collision.position.x + " " + collision.position.y);
		
		
		//do object logic here
		
		
		
		velocity.y += gravAcc;
		
		//random movement
		velocity.x += (Math.random()-0.5)*2;
		if(collisionUp && Math.random() < 0.02) {
			//jump
			velocity.y = -20;
		}
		
		collision.position.add(velocity);
	}
	
	public void paint(Graphics g) {
		collision.paint(g, Color.RED);
	}
	
	public boolean collisionActive() {
		return true;
	}

}
