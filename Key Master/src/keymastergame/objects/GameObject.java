package keymastergame.objects;

import java.awt.Color;
import java.awt.Graphics;

import keymastergame.framework.Box;
import keymastergame.framework.Vector;

public class GameObject {
	//track facing
	public boolean faceLeft = false;
	
	public static final Vector size = new Vector(32, 32);
	
	public Box collision;
	public Vector velocity;
	protected double gravAcc = 0.5;
		
	public boolean collisionUp;
	public boolean collisionRight;
	public boolean collisionDown;
	public boolean collisionLeft;
	
	public boolean toRemove = false;
	
	public GameObject() {
		collision = new Box(new Vector(0,0), size);
		velocity = new Vector(0,0);

		collisionUp = false;
		collisionRight = false;
		collisionDown = false;
		collisionLeft = false;
	}

	public GameObject(Vector pos) {
		
		collision = new Box(pos, size);
		velocity = new Vector(0,0);
		

		collisionUp = false;
		collisionRight = false;
		collisionDown = false;
		collisionLeft = false;
	}
	
	public void update() {		
		//do object logic here
		
		velocity.y += gravAcc;
		
		//random movement
		velocity.x += (Math.random()-0.5)*2;
		if(collisionUp && Math.random() < 0.02) {
			//jump
			velocity.y = -12;
		}
		
		collision.position.add(velocity);
	}
	
	public void paint(Graphics g) {
		collision.paint(g, Color.LIGHT_GRAY);
	}
	
	public boolean collisionActive() {
		return true;
	}

}
