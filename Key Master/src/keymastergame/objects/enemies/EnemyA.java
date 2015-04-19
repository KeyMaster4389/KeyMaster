package keymastergame.objects.enemies;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import keymastergame.StartingClass;
import keymastergame.framework.Animation;
import keymastergame.framework.Box;
import keymastergame.framework.Resource;
import keymastergame.framework.Vector;
import keymastergame.objects.Player;

public class EnemyA extends EnemyObject {
	//behavior: chaser, run at player if they're on our level and there are tiles to the player
	
	private boolean rushing = false;
	private double rushX;
	
	private double deAcc = 0.075;
	
	// animation - left 0, right 1
	private int faceState = 1;

	private Vector imageOffset;// initialize in configureAnimations method

	private Animation left;
	private Animation right;
	private static Image currentImage;
	
	public EnemyA() {
		collision = new Box(new Vector(0,0), size);
		velocity = new Vector(0,0);

		configureAnimations();
		
		collisionUp = false;
		collisionRight = false;
		collisionDown = false;
		collisionLeft = false;
	}

	public EnemyA(Vector pos) {
		
		collision = new Box(pos, size);
		velocity = new Vector(0,0);
		
		configureAnimations();

		collisionUp = false;
		collisionRight = false;
		collisionDown = false;
		collisionLeft = false;
	}
	
	public void updateAnimation() {
		
		//facePlayer();

		if(velocity.x == 0){
			
			if(faceLeft){
				
				if(currentImage != Resource.enemyALeft7){
					
					currentImage = Resource.enemyALeft7;
				}
				
			}else if (!faceLeft){
				
				if(currentImage != Resource.enemyARight4){
					
					currentImage =  Resource.enemyARight4;
					
				}
				
			}
			
		}else if (velocity.x > 0){
			
			faceLeft = false;
			//facePlayer();
			
			if(faceState != 1){
				
				faceState = 1;
				right.resetAnimation();
				
			}
			
			currentImage = right.getImage();
			right.update(StartingClass.frameSpeed);
			
		}else if (velocity.x < 0){
			
			faceLeft = true;
			//facePlayer();
			
			if(faceState != 0){
				
				faceState = 0;
				left.resetAnimation();
				
			}
			
			currentImage = left.getImage();
			left.update(StartingClass.frameSpeed);
			
		}
	}
	
	public void paint(Graphics g) {
		
		if (StartingClass.debugGraphics) {
			collision.paint(g, Color.RED);
		
			g.setColor(Color.BLACK);
			g.drawString("A", (int)collision.position.x - 4, (int)collision.position.y + 4);
		
		}else {

			double xPos = collision.position.x;
			double yPos = collision.position.y;

			g.drawImage(currentImage, (int) (xPos - imageOffset.x),
					(int) (yPos - imageOffset.y), null);

		}
	}
	
	public void act() {
		if(collisionUp) {
			Player plr = StartingClass.gameState.plr;
			facePlayer();
			if (Math.abs((plr.collision.position.y + plr.collision.size.y/2) - (collision.position.y + collision.size.y/2)) < 10) {
				//on same height
				//check if there are tiles between here and the player
				
				boolean chase = true;
				
				Vector apprGridSelf = new Vector();
				Vector apprGridPlr = new Vector();
				
				//approximate grid location of enemy and player
				apprGridSelf.x = Math.floor(collision.position.x / StartingClass.TILESIZE);
				apprGridSelf.y = Math.floor(collision.position.y / StartingClass.TILESIZE);
				apprGridPlr.x = Math.floor(plr.collision.position.x / StartingClass.TILESIZE);
				apprGridPlr.y = Math.floor(plr.collision.position.y / StartingClass.TILESIZE);
				
					
				double start;
				double end;
					
				if (apprGridSelf.x > apprGridPlr.x) {
					start = apprGridPlr.x;
					end = apprGridSelf.x;
				} else {
					start = apprGridSelf.x;
					end = apprGridPlr.x;
				}
				
				//check 1: make sure there's no tiles between us and player
				for (double i = start*StartingClass.TILESIZE; i <= end*StartingClass.TILESIZE; i += StartingClass.TILESIZE) {
						
					if (StartingClass.gameState.lvl.getTileFromPoint(
							new Vector(i + StartingClass.TILESIZE/2, (apprGridSelf.y*StartingClass.TILESIZE) + StartingClass.TILESIZE/2 )
							) != null) {
						chase = false;
						break;
					}
				}

				//check 2: make sure there ARE tiles under us leading from us to player
				for (double i = start*StartingClass.TILESIZE; i <= end*StartingClass.TILESIZE; i += StartingClass.TILESIZE) {
						
					if (StartingClass.gameState.lvl.getTileFromPoint(
							new Vector(i + StartingClass.TILESIZE/2, (apprGridSelf.y*StartingClass.TILESIZE) + StartingClass.TILESIZE + StartingClass.TILESIZE/2 )
							) == null) {
						chase = false;
						break;
					}
				}
				
				
				if (chase) {
					rushing = true;
					rushX = plr.collision.position.x;
				}
				
			}
			
			if (!rushing) {
				
				//deaccelerate
				if (velocity.x != 0) {
					if (Math.abs(velocity.x) <= deAcc) {
						velocity.x = 0;
					} else {
						if (velocity.x > 0) {
							velocity.x -= deAcc;
						} else {
							velocity.x += deAcc;
						}
					}
				}
			} else {
				double dist = rushX - collision.position.x;
				
				if (Math.abs(dist) < 5) {
					rushing = false;
				} else {
					//move in direction of player
					if (dist > 0) {
						velocity.x = 3.5;
					} else {
						velocity.x = -3.5;
					}
				}
				
			}
		} else {
			velocity.x = 0;
			rushing = false;
		}
	}
	
	private void configureAnimations() {
		
		imageOffset = new Vector(16, 28);

		left = new Animation();
		right = new Animation();
		
		currentImage = Resource.enemyALeft1;

		int runtime = 120;
		
		left.addFrame(Resource.enemyALeft1, runtime);
		left.addFrame(Resource.enemyALeft2, runtime);
		left.addFrame(Resource.enemyALeft3, runtime);
		left.addFrame(Resource.enemyALeft4, runtime);
		left.addFrame(Resource.enemyALeft5, runtime);
		left.addFrame(Resource.enemyALeft6, runtime);
		left.addFrame(Resource.enemyALeft7, runtime);
		
		right.addFrame(Resource.enemyARight1, runtime);
		right.addFrame(Resource.enemyARight2, runtime);
		right.addFrame(Resource.enemyARight3, runtime);
		right.addFrame(Resource.enemyARight4, runtime);
		right.addFrame(Resource.enemyARight5, runtime);
		right.addFrame(Resource.enemyARight6, runtime);
		right.addFrame(Resource.enemyARight7, runtime);
		
		
	}
	
}
