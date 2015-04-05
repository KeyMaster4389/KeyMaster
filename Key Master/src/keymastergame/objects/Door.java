package keymastergame.objects;

import java.awt.Color;
import java.awt.Graphics;

import keymastergame.StartingClass;
import keymastergame.framework.Box;
import keymastergame.framework.Resource;
import keymastergame.framework.Vector;

public class Door extends GameObject{

	public static final Vector size = new Vector(32, 32);
	
	public boolean isOpen = false;
	

	public Door() {
		collision = new Box(new Vector(0,0), size);
		velocity = new Vector(0,0);

		collisionUp = false;
		collisionRight = false;
		collisionDown = false;
		collisionLeft = false;
	}

	public Door(Vector pos) {
		
		collision = new Box(pos, size);
		velocity = new Vector(0,0);
		

		collisionUp = false;
		collisionRight = false;
		collisionDown = false;
		collisionLeft = false;
	}
	
	public void update() {
		//do nothing
	}
	
	public void paint(Graphics g) {
		int xPos = (int)(collision.position.x - StartingClass.TILESIZE/2);
		int yPos = (int)(collision.position.y - StartingClass.TILESIZE/2);
		
		
		if (!isOpen) {
			
			if (StartingClass.debugGraphics) {
				collision.paint(g, Color.WHITE);
			} else {
				g.drawImage(Resource.doorClosedSpr, xPos, yPos, null);
			}
			
		} else {
			if (StartingClass.debugGraphics) {
				collision.paint(g, Color.BLACK);
			} else {
				g.drawImage(Resource.doorOpenSpr, xPos, yPos, null);
			}
		}
		
	}
	
	public boolean collisionActive() {
		return false;
	}
	
}
