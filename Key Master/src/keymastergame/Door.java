package keymastergame;

import java.awt.Color;
import java.awt.Graphics;

import keymastergame.framework.Box;
import keymastergame.framework.Vector;

public class Door extends GameObject{

	public boolean isOpen = false;
	

	public Door() {
		collision = new Box(new Vector(0,0), new Vector(32,32));
		velocity = new Vector(0,0);

		collisionUp = false;
		collisionRight = false;
		collisionDown = false;
		collisionLeft = false;
	}

	public Door(Vector pos) {
		
		collision = new Box(pos, new Vector(32,32));
		velocity = new Vector(0,0);
		

		collisionUp = false;
		collisionRight = false;
		collisionDown = false;
		collisionLeft = false;
	}
	
	public void update() {	
		//System.out.println(collision.position.x + " " + collision.position.y);
		
		//do object logic here
		
	}
	
	public void paint(Graphics g) {
		if (!isOpen) {
			collision.paint(g, Color.LIGHT_GRAY);
		} else {
			collision.paint(g, Color.DARK_GRAY);
		}
		
	}
	
	public boolean collisionActive() {
		return false;
	}
	
}
