package keymastergame.objects.enemies;

import java.awt.Color;
import java.awt.Graphics;

import keymastergame.StartingClass;
import keymastergame.Tile;
import keymastergame.framework.Box;
import keymastergame.framework.Vector;
import keymastergame.objects.Player;

public class EnemyB extends EnemyObject{
	//behavior: move under player and jump to destroy blocks underneath palyer

	private double jumpHeight = 100; //how vertically close under player to jump
	private double jumpDist = 2; //how horizontally close to player to jump
	private double jumpVelY = 7;
	
	private double moveSpeed = 4;
	
	private double jumpDisable = 60;
	private double jumpTimer = 0;
	
	public EnemyB() {
		collision = new Box(new Vector(0,0), size);
		velocity = new Vector(0,0);

		collisionUp = false;
		collisionRight = false;
		collisionDown = false;
		collisionLeft = false;
	}
	public EnemyB(Vector pos) {
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
		g.drawString("B", (int)collision.position.x - 4, (int)collision.position.y + 4);
	}

	protected void act() {
		velocity.x = 0;
		
		if (jumpTimer > 0) jumpTimer--;
		
		if (collisionUp) {
			//check if we're under player
			Player plr = StartingClass.gameState.plr;
			if (plr.collision.position.y <= collision.position.y + 10 && plr.collision.position.y + jumpHeight >= collision.position.y ) {
				
				//approximate plr's horizontal position to nearest tile
				double plrPosX = Math.floor(plr.collision.position.x / StartingClass.TILESIZE) * StartingClass.TILESIZE + StartingClass.TILESIZE/2;
				
				//check if we are directly under player
				if (collision.position.x <= plrPosX + jumpDist && collision.position.x >= plrPosX - jumpDist && jumpTimer == 0) {
					//jump
					velocity.y = -jumpVelY;
					jumpTimer = jumpDisable;
					
				} else {
					//move towards player
					if (collision.position.x < plrPosX) {
						velocity.x = moveSpeed;
						faceLeft = false;
					} else if (collision.position.x > plrPosX) {
						velocity.x = -moveSpeed;
						faceLeft = true;
						
					}
				}
				
			}
			
		} else {
			if (collisionDown) {
				//destroy block(s) above us
				Tile t = StartingClass.gameState.lvl.getTileFromPoint(new Vector(collision.position.x, collision.position.y - StartingClass.TILESIZE));
				if (t != null) {
					t.setDisabled(120);
					//be stunned for 90 frames
					waitTimer = 90;
				}
			}
		}		
		
	}
}
