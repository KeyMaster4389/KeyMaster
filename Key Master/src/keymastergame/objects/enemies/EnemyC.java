package keymastergame.objects.enemies;

import java.awt.Color;
import java.awt.Graphics;

import keymastergame.StartingClass;
import keymastergame.framework.Box;
import keymastergame.framework.Vector;
import keymastergame.objects.Player;

public class EnemyC extends EnemyObject {

	private int shootSpeed = 90;
	private int shootTimer = 0;
	private double projectileSpeed = 3;

	public EnemyC() {
		collision = new Box(new Vector(0,0), size);
		velocity = new Vector(0,0);

		collisionUp = false;
		collisionRight = false;
		collisionDown = false;
		collisionLeft = false;
		
	}
	
	
	public EnemyC(Vector pos) {
		collision = new Box(pos, size);
		velocity = new Vector(0,0);
		
		collisionUp = false;
		collisionRight = false;
		collisionDown = false;
		collisionLeft = false;
	}
	
	
	public void paint(Graphics g) {
		collision.paint(g, Color.RED);
		
		g.setColor(Color.BLACK);
		g.drawString("C", (int)collision.position.x - 4, (int)collision.position.y + 4);
	}

	protected void act() {
		velocity.x = 0;
		
		if (shootTimer > 0) shootTimer--;
		
		if (collisionUp) {
			
			Player plr = StartingClass.gameState.plr;
			if (plr.collision.position.x > collision.position.x) {
				faceLeft = false;
			} else if (plr.collision.position.x < collision.position.x) {
				faceLeft = true;
			}
			
			if (shootTimer == 0) {
				//shoot
				
				Projectile p = new Projectile(new Vector(collision.position));
				
				if (!faceLeft) {
					p.collision.position.x += 8;
					p.velocity.x = projectileSpeed;
				} else {
					p.collision.position.x -= 8;
					p.velocity.x = -projectileSpeed;
				}
				
				p.faceLeft = faceLeft;
				
				StartingClass.gameState.addObj.add(p);
				
				shootTimer = shootSpeed;
				
			}
			
		}
		
	}

	
	
	
	
}
