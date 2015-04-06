package keymastergame.objects.enemies;

import java.awt.Color;
import java.awt.Graphics;

import keymastergame.StartingClass;
import keymastergame.framework.Box;
import keymastergame.framework.Vector;
import keymastergame.objects.GameObject;

public class EnemyObject extends GameObject {	

	public static final Vector size = new Vector(24, 28);
	public int waitTimer = 0;
	
	
	public EnemyObject() {
		collision = new Box(new Vector(0,0), size);
		velocity = new Vector(0,0);

		collisionUp = false;
		collisionRight = false;
		collisionDown = false;
		collisionLeft = false;
	}

	public EnemyObject(Vector pos) {
		
		collision = new Box(pos, size);
		velocity = new Vector(0,0);
		

		collisionUp = false;
		collisionRight = false;
		collisionDown = false;
		collisionLeft = false;
	}
	
	public void update() {
		velocity.y += gravAcc;
		
		act();
			
		collision.position.add(velocity);
	}
	
	public void paint(Graphics g) {
		collision.paint(g, Color.RED);
	}
	
	protected void act () {
		//do enemy logic
		
	}
	
	protected void facePlayer() {
		double plrPosX = StartingClass.gameState.plr.collision.position.x;
		
		if (plrPosX > collision.position.x) {
			faceLeft = false;
		} else if (plrPosX < collision.position.x){
			faceLeft = true;
		}
		
	}
	
}
