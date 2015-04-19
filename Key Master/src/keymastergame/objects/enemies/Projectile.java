package keymastergame.objects.enemies;

import java.awt.Color;
import java.awt.Graphics;

import keymastergame.framework.Box;
import keymastergame.framework.Vector;

public class Projectile extends EnemyObject {

	Vector size = new Vector(10,10);
	
	public Projectile() {
		collision = new Box(new Vector(0,0), size);
		velocity = new Vector(0,0);

		collisionUp = false;
		collisionRight = false;
		collisionDown = false;
		collisionLeft = false;
	}
	public Projectile(Vector pos) {
		collision = new Box(pos, size);
		velocity = new Vector(0,0);

		collisionUp = false;
		collisionRight = false;
		collisionDown = false;
		collisionLeft = false;
	}
	

	public void update() {
		//no gravity
		//velocity.y += gravAcc;
		
		if (waitTimer == 0) act();
		else waitTimer--;
			
		collision.position.add(velocity);
	}
	
	public void paint(Graphics g) {
		collision.paint(g, Color.GREEN);
	}

	protected void act() {
		if (collisionUp || collisionDown || collisionLeft || collisionRight) {
			toRemove = true;
		}
	}

}
