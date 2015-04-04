package keymastergame.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import keymastergame.StartingClass;
import keymastergame.Tile;
import keymastergame.framework.Animation;
import keymastergame.framework.Box;
import keymastergame.framework.Resource;
import keymastergame.framework.Vector;

public class Player extends GameObject {

	/* variables inherited from GameObject, for reference
	 
	public Box collision;
	public Vector velocity;
	protected double gravAcc;
		
	public boolean collisionUp;
	public boolean collisionRight;
	public boolean collisionDown;
	public boolean collisionLeft;
	
	*/
	public static final Vector size = new Vector(18, 24);
	
	//animations
	private int moveState = 0;
	
	private Vector imageOffset;
	
	private Animation running;
	private Animation climbing;
	private Animation dying;
	
	
	private static Image currentImage;
	
	//gameplay variables
	public boolean hasWon = false;
	public boolean isDead = false;	//death animation complete
	public boolean isDying = false;	//in process of completing death animation
	
	public boolean hasKey = false;	
	private boolean onLadder = false;

	protected double gravAcc = 0.4;
	private static double horizontalSpeed = 2.5;
	private static double ladderSpeed = 2.5; 
	
	//tracks state of controls, true = pressed, false = not pressed
	private boolean keyLeft = false;
	private boolean keyRight = false;
	private boolean keyUp = false;
	private boolean keyDown = false;
	private boolean keyAbility = false;
	
	private boolean doAbility = false;
	
	//const of controls
	public final static int controlLeft = KeyEvent.VK_LEFT;
	public final static int controlRight = KeyEvent.VK_RIGHT;
	public final static int controlUp = KeyEvent.VK_UP;
	public final static int controlDown = KeyEvent.VK_DOWN;
	public final static int controlAbility = KeyEvent.VK_SPACE;
	
	//tile disappear lasts 120 frames
	private int abilityDuration = 120;
	
	//countdown variable until player can use ability again
	private int abilityCooldown = 0;
	
	
	public Player() {
		collision = new Box(new Vector(0,0), size);
		velocity = new Vector(0,0);

		configureAnimations();
		
		collisionUp = false;
		collisionRight = false;
		collisionDown = false;
		collisionLeft = false;
	}

	public Player(Vector position) {
		collision = new Box(position, size);
		velocity = new Vector(0,0);
		
		configureAnimations();
		
		collisionUp = false;
		collisionRight = false;
		collisionDown = false;
		collisionLeft = false;
		
	}

	public void update() {
		velocity.x = 0;
		
		if (!StartingClass.levelBoundary.intersects(collision)) {
			//fallen out of map, die
			
			//System.out.println("not in level!");
			
			isDead = true;
			return;
		}
		
		//decrement cooldown
		if (abilityCooldown > 0) {
			abilityCooldown--;
			if (abilityCooldown == 0) {
				System.out.println("Ability available!");
			}
		}
		
		if (!onLadder) {
			//not attached to a ladder
			
			
			if (collisionUp) {
				//on the ground
				
				if (doAbility && abilityCooldown == 0) {
					//do ability
					performAbility();
				}

				if ((keyUp || keyDown) && !(keyLeft || keyRight)) {
					//try to attach to ladder
					
					Ladder ladder = StartingClass.gameState.lvl.getLadderFromPoint(collision.position);
					if (ladder != null) {
						collision.position.x = ladder.collision.position.x;
						onLadder = true;
					}
				}
			}
			
			
			//movement
			if (!onLadder) {
				if (keyLeft && !keyRight) {
					velocity.x = -horizontalSpeed;
				} else if (keyRight && !keyLeft) {
					velocity.x = horizontalSpeed;
				}
				
				//apply gravity when off ladder
				velocity.y += gravAcc;
			}
			
		} else {
			
			
			if (canMountLadder(collision.position)) {
				//on ladder
				velocity.y = 0;
				//if (faceLeft) faceLeft = false;
	
				if (keyUp && !keyDown) {
					if (canMountLadder(new Vector(collision.position.x, collision.position.y - ladderSpeed))) {
						velocity.y = -ladderSpeed;
					} else {
						//reached top of ladder
						Ladder ladder = StartingClass.gameState.lvl.getLadderFromPoint(collision.position);
						collision.position.y = ladder.collision.position.y - ladder.collision.size.y/2;
						
						if (canDismountLadder()) onLadder = false;
						
					}
					
				} else if (keyDown && !keyUp) {
					
					if (canMountLadder(new Vector(collision.position.x, collision.position.y + ladderSpeed))) {
						velocity.y = ladderSpeed;
					} else {
						//reached bottom of ladder
						Ladder ladder = StartingClass.gameState.lvl.getLadderFromPoint(collision.position);
						collision.position.y = ladder.collision.position.y + ladder.collision.size.y/2;
						
						if (canDismountLadder()) onLadder = false;
					}
				}
	
				//left or right dismounts ladder
				if (!(keyUp || keyDown) && (keyLeft || keyRight) && canDismountLadder()) {
					onLadder = false;
				}
			}else {
				onLadder = false;
				velocity.y = 0;
			}
		}
		
				
		collision.position.add(velocity);
	}
	
	public void updateAnimation() {
		if (collisionUp && !onLadder) {
			//on ground
			
			if (velocity.x == 0) {
				//idle
				if (currentImage != Resource.idle) {
					currentImage = Resource.idle;
				}
				
			} else if (velocity.x > 0) {
				//run right
				faceLeft = false;
				
				
				if (moveState != 1) {
					moveState = 1;
					running.resetAnimation();
				}
				currentImage = running.getImage();
				
				running.update(StartingClass.frameSpeed);
				
			} else if (velocity.x < 0) {
				//run left
				faceLeft = true;
				

				if (moveState != 1) {
					moveState = 1;
					running.resetAnimation();
				}
				currentImage = running.getImage();
				
				running.update(StartingClass.frameSpeed);
			}
		} else if (onLadder) {
			//on ladder


			if (moveState != 2) {
				moveState = 2;
				climbing.resetAnimation();
			}

			currentImage = climbing.getImage();
			if (velocity.y != 0) {
				climbing.update(StartingClass.frameSpeed);
			}
			
		} else {
			//falling
			currentImage = Resource.air;
			
			if (velocity.x < 0) {
				faceLeft = true;
			} else if (velocity.x > 0) {
				faceLeft = false;
			}
			
		}
		
	}
	
	public void paint(Graphics g) {
		
		if (StartingClass.debugGraphics) {
			collision.paint(g, Color.GREEN);
		}  
			
		double xPos = collision.position.x;
		double yPos = collision.position.y;
			
		if (!faceLeft) {
				
			g.drawImage(currentImage, (int)(xPos - imageOffset.x),  (int)(yPos - imageOffset.y), null);
			
		} else {
			g.drawImage(currentImage, (int)(xPos + imageOffset.x),  (int)(yPos - imageOffset.y),
					-currentImage.getWidth(null), currentImage.getHeight(null), null);
		}
			
		
		
	}
	
	public void input(int code, boolean pressed) {
		//do something here
		switch (code) {
		
		case controlLeft: keyLeft = pressed;
			break;
		case controlRight: keyRight = pressed;
			break;
		case controlUp: keyUp = pressed;
			break;
		case controlDown: keyDown = pressed;
			break;
			//only do ability when pressed, not held
		case controlAbility: doAbility = (keyAbility != pressed) && pressed; keyAbility = pressed;
			break;
		}
		
	}
	
	//performs ability
	public void performAbility() {
		System.out.println("Ability activated!");

		Vector checkPoint = new Vector(collision.position);
		
		Vector removePoint = new Vector(collision.position);
		if (!faceLeft) {
			removePoint.x += StartingClass.TILESIZE;
			checkPoint.x += StartingClass.TILESIZE;
		} else {
			removePoint.x -= StartingClass.TILESIZE;
			checkPoint.x -= StartingClass.TILESIZE;
		}
		removePoint.y += StartingClass.TILESIZE;
		

		Tile t1 = StartingClass.gameState.lvl.getTileFromPoint(checkPoint);
		Tile t2 = StartingClass.gameState.lvl.getTileFromPoint(removePoint);
		
		if (t1 == null && t2 != null) {
			t2.setDisabled(abilityDuration);
			
			abilityCooldown = abilityDuration;
		
		} else {
			System.out.println("Ability failed!");
		}
	}
	
	public boolean canMountLadder(Vector pos) {
		//check if the player is intersecting a ladder's collision box
		Ladder ladder = StartingClass.gameState.lvl.getLadderFromPoint(pos);
		
		return ladder != null;
	}
	public boolean canDismountLadder() {
		
		//check if player is currently colliding with any tiles
		for (Tile t : StartingClass.gameState.lvl.tiles) {
			if (!t.isDisabled() && t.collision.intersects(collision)) {
				return false;
			}
		}
		return true;
	}

	public boolean collisionActive() {
		//don't collide with tiles if on a ladder
		return !onLadder;
	}
	
	private void configureAnimations() {
		
		imageOffset = new Vector(16,16);
		
		running = new Animation();
		climbing = new Animation();
		dying = new Animation();
		
		currentImage = Resource.idle;
		
		int runtime = 100;
		int climbtime = 130;
		//sync with reload delay
		int dietime = StartingClass.gameState.reloadLevelDelay * StartingClass.frameSpeed / 9;
				
		
		running.addFrame(Resource.run1, runtime);
		running.addFrame(Resource.run2, runtime);
		running.addFrame(Resource.run3, runtime);
		running.addFrame(Resource.run4, runtime);
		running.addFrame(Resource.run5, runtime);
		running.addFrame(Resource.run6, runtime);
		
		climbing.addFrame(Resource.climb1, climbtime);
		climbing.addFrame(Resource.climb2, climbtime);
		
		dying.addFrame(Resource.die1, dietime);
		dying.addFrame(Resource.die2, dietime);
		dying.addFrame(Resource.die3, dietime);
		dying.addFrame(Resource.die4, dietime);
		dying.addFrame(Resource.die5, dietime);
		dying.addFrame(Resource.die6, dietime);
		dying.addFrame(Resource.die7, dietime);
		dying.addFrame(Resource.die8, dietime);
		dying.addFrame(Resource.die9, dietime);
	}
	
	public void win() {
		hasWon = true;
		currentImage = Resource.winning;
	}
	
	public void winUpdate() {		
		//do nothing
		velocity.x = 0;
		velocity.y = 0;
	}
	
	public void die() {
		isDead = true;
		dying.resetAnimation();
		currentImage = dying.getImage();
	}

	public void dieUpdate() {
		currentImage = dying.getImage();

		velocity.x = 0;
		velocity.y += gravAcc;
		
	}
}
